/** 
 * Copyright (c) 2009, Regents of the University of Colorado 
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * Neither the name of the University of Colorado at Boulder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE. 
 */

package org.cleartk.examples.parser;

import java.io.File;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.cleartk.examples.documentclassification.basic.BasicDocumentClassificationAnnotator;

import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.tokenizer.TokenAnnotator;

import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;

public class ParserExample {

	
	public static void main(String[] args) throws Exception {
		File filesDirectory = new File("entrada");
		String outputDirectory = "salida";
		System.out.println("Inicio");
		AggregateBuilder builder = new AggregateBuilder();
		System.out.println("1000");
		builder.add(UriToDocumentTextAnnotator.getDescription());
		builder.add(SentenceAnnotator.getDescription());
		builder.add(TokenAnnotator.getDescription());
		builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class, XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
				outputDirectory, XWriter.PARAM_FILE_NAMER_CLASS_NAME, ViewURIFileNamer.class.getName()));
		builder.add(AnalysisEngineFactory.createPrimitiveDescription(BasicDocumentClassificationAnnotator.class));
		
		SimplePipeline.runPipeline(UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory),
				builder.createAggregateDescription());
		CollectionReader r =UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory);
		
		JCas a= JCasFactory.createJCas();
		if (r.hasNext())
		{
			System.out.println("entre");
			r.getNext(a.getCas());
		}
		System.out.println(a.getDocumentText());
		System.out.println("FIN");
	}
}
