

import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
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
//   super.initialize(context);
//
//    // Create an extractor that gives word counts for a document
  this.extractor = new CleartkExtractor(Token.class, new CoveredTextExtractor(), new Count(
        new Covered()));
  }

  public void process(JCas jCas) throws AnalysisEngineProcessException {
	  System.out.println("procc2es");
	  System.out.println(jCas.getDocumentText());
    // use the extractor to create features for the document
    DocumentAnnotation doc = (DocumentAnnotation) jCas.getDocumentAnnotationFs();
    List<Feature> features = this.extractor.extract(jCas, doc);
 
    // during training, get the label for this document from the CAS
    if (isTraining()) {
      UsenetDocument document = JCasUtil.selectSingle(jCas, UsenetDocument.class);
      this.dataWriter.write(new Instance<String>(document.getCategory(), features));
      System.out.println("IF");
    }
    else {
    	  System.out.println("ELSE");
      UsenetDocument document = new UsenetDocument(jCas, 0, jCas.getDocumentText().length());
   document.setCategory("cantidad");
     
      document.addToIndexes();
     
    }

  }

}
