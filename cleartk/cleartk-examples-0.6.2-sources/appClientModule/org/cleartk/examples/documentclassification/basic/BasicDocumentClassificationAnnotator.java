/** 
 * Copyright (c) 2012, Regents of the University of Colorado 
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
package org.cleartk.examples.documentclassification.basic;

import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.Sofa;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.tcas.DocumentAnnotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.cleartk.classifier.CleartkAnnotator;
import org.cleartk.classifier.Feature;
import org.cleartk.classifier.Instance;
import org.cleartk.classifier.feature.extractor.CleartkExtractor;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Count;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Covered;
import org.cleartk.classifier.feature.extractor.simple.CoveredTextExtractor;
import org.cleartk.examples.type.UsenetDocument;
import org.cleartk.token.type.Token;
import org.uimafit.util.JCasUtil;

import com.google.common.hash.HashCode;

import bsh.org.objectweb.asm.Type;

/**
 * <br>
 * Copyright (c) 2012, Regents of the University of Colorado <br>
 * All rights reserved.
 * 
 * 
 * Simple class to demonstrate how to write a new CleartkAnnotator. This class implements a basic
 * document classification annotator whose only features are the counts of the words in the
 * document.
 * 
 * @author Lee Becker
 * 
 */
public class BasicDocumentClassificationAnnotator extends CleartkAnnotator<String> {

  private CleartkExtractor extractor;

  public void initialize(UimaContext context) throws ResourceInitializationException {
	  System.out.println("ini");  

  this.extractor = new CleartkExtractor(Token.class, new CoveredTextExtractor(), new Count(
        new Covered()));
  }

  public void process(JCas jCas) throws AnalysisEngineProcessException {
	  Annotation aux; 
	  AnnotationIndex aux2;
	  FeatureStructure aux3;
	  org.apache.uima.cas.Feature f;
	  List<org.apache.uima.cas.Feature> lista ;
	  TOP aux4;
	  CAS a= jCas.getCas();
	  System.out.println("procces");
	  //System.out.println(jCas.getDocumentText());
	  //System.out.println(jCas.getDocumentText().length());
      aux2 = jCas.getAnnotationIndex();
	  //System.out.println(aux2.size());
	//  System.out.println(jCas.getTypeSystem());
	  //System.out.println(a.getTypeSystem());
	  //System.out.println(   a.getViewName());
	//  System.out.println( a.getSofaDataString());
	 aux3= a.getSofaDataArray();
	org.apache.uima.cas.Type tp;
	//tp=aux2.getType();
	tp= jCas.getCasType(28);
	CAS asd;
	aux2= jCas.getAnnotationIndex();
	asd =jCas.getCas();
	//JCasUtil.select(jCas, aux4.getType());
	
     System.out.println(aux2.size());
    aux4=jCas.getDocumentAnnotationFs();
      aux3=  aux2.iterator().get();
      aux4.getType();
aux3=aux4;
Sofa pepe;
TOP auxiliar;
pepe=jCas.getSofa();
pepe.getLocalStringData();
System.out.println(pepe.getLocalStringData());
      System.out.println(aux4.getType());
Collection<TOP> mati;
  mati=	JCasUtil.selectAll(jCas);
  
  for (Token token : JCasUtil.select(jCas, Token.class)) {
		System.out.println(token.getCoveredText());
	}
  
  
  
//	while(mati.iterator().hasNext())
//	{	
//	auxiliar =mati.iterator().next();
//	String a1 =auxiliar.toString();
//	System.out.println(a1);
//	}
	
	System.out.println(tp.getName());
	lista =tp.getFeatures();
	f=lista.get(2);
	System.out.println(f.getName());
	//System.out.println(aux4.getStringValue(f));
	   
//	for (int i=0; i<65; i++)
//	{tp= jCas.getCasType(i);
//	 System.out.println("TYPE =   "+tp.getName() + "  " + i);
//	
//	 lista =tp.getFeatures();
//		for (int j=0;j<lista.size();j++)
//		{f=lista.get(j);
//		 System.out.println("FEATURE  = " +f.getName());
//		 
////	}
//		 System.out.println("++++++++++++++++++++++++++++++++++++++");	
//		 }
//
//	// System.out.println(a.getDocumentText());
	}

}
