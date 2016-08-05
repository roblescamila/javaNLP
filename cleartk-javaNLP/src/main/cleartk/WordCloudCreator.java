package main.cleartk;

import java.io.IOException;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.mcavallo.opencloud.Cloud;

public class WordCloudCreator {
	
	private AnalysisEngine engine;
	private CAS cas; 
	
	public WordCloudCreator(String targetFileStr) throws InvalidXMLException, ResourceInitializationException, IOException {
		String inputEngine = "main.descriptors.MainEngine";
		engine = AnalysisEngineFactory.createEngine(inputEngine);
		cas = engine.newCAS();
		cas.setDocumentText(targetFileStr);
	}

	public void add(Cloud cloud) {
		
	}

}
