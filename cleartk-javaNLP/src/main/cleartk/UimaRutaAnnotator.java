package main.cleartk;

import java.io.IOException;
import java.util.Vector;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.uimafit.util.CasUtil;

public class UimaRutaAnnotator {

	private boolean selected;
	private String classPath;
	private Vector<String> wordVector;
	private CAS cas;

	// path : "uima.ruta.annotators.MethodName"
	public UimaRutaAnnotator(String path, CAS inputCas) {
		selected = false;
		classPath = path;
		cas = inputCas;
	}

	public Vector<String> createVector() {
		Type type = cas.getTypeSystem().getType(classPath);
		for (AnnotationFS annotation : CasUtil.select(cas, type)) {
			wordVector.add(annotation.getCoveredText());
		}
		return wordVector;
	}

	public Vector<String> getVector() {
		return wordVector;
	}
}
