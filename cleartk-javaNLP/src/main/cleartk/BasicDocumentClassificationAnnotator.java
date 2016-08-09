package main.cleartk;

import java.io.IOException;
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
import org.apache.uima.util.InvalidXMLException;
import org.cleartk.classifier.CleartkAnnotator;
import org.cleartk.classifier.Feature;
import org.cleartk.classifier.Instance;
import org.cleartk.classifier.feature.extractor.CleartkExtractor;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Count;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Covered;
import org.cleartk.classifier.feature.extractor.simple.CoveredTextExtractor;
import org.cleartk.examples.type.UsenetDocument;
import org.cleartk.token.type.Token;
import org.cleartk.type.test.Lemma;
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
	private boolean arr[];
	

  private CleartkExtractor extractor;
public void  setBoleans(boolean a[])
{
	arr=a;
}
  
  public void initialize(UimaContext context) throws ResourceInitializationException {
	  System.out.println("ini");  

  this.extractor = new CleartkExtractor(Token.class, new CoveredTextExtractor(), new Count(
        new Covered()));
  }

  public void process(JCas jCas) throws AnalysisEngineProcessException {
	
	  System.out.println(jCas.getDocumentText());
  System.out.println("IMPRIMO TODOS LOS TOKEN");
    for (Token token : JCasUtil.select(jCas, Token.class)) {
		System.out.print(token.getCoveredText()+" ");
	}
    System.out.println(" ");
    System.out.println("IMPRIMO TODOS LOS STEM");
    for (Token token : JCasUtil.select(jCas, Token.class)) {
		System.out.print(token.getStem()+ " ");
	}
    System.out.println(" ");
    System.out.println("IMPRIMO TODOS LOS LEMMA");
    for (Token token : JCasUtil.select(jCas, Token.class)) {
		System.out.print(token.getLemma()+ " ");
	}

	}

}
