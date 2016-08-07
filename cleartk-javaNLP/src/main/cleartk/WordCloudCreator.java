package main.cleartk;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.FilenameUtils;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.admin.CASFactory;
import org.apache.uima.cas.impl.XmiCasDeserializer;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.CasIOUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;
import org.mcavallo.opencloud.Cloud;
import org.xml.sax.SAXException;

public class WordCloudCreator {

	static final int COMMENT = 0;
	static final int CLASSNAME = 1;
	static final int METHODNAME = 2;
	static final int VARNAME = 3;
	static final int PACKAGE = 4;
	static final int IMPORT = 5;

	// FileInputStream fisTargetFile;
	JCas jcas;
	File file;
	FileInputStream fisTargetFile;

	public WordCloudCreator() {
	}

	public WordCloudCreator(String f) throws IOException, UIMAException {
		file = new File(f);
		fisTargetFile = new FileInputStream(file);
		createCas();
	}

	private void createCas() throws IOException,
			UIMAException {
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
//		AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
//		cas = engine.newCAS();
//		engine.process(cas);
		//jcas = JCasFactory.createJCas();

		//jcas.setDocumentText(targetFileStr);
		ClearTKProcessor nlp = new ClearTKProcessor(jcas);
		jcas = nlp.executeClearTK();
	}

	public Cloud updateCloud(boolean arr[], Cloud c) throws CASException {

		if (arr[COMMENT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.SingleLineComment", jcas.getCas(), c);
			UimaRutaAnnotator b = new UimaRutaAnnotator("uima.ruta.annotators.MultiLineComment", jcas.getCas(), c);
			a.addToCloud();
			b.addToCloud();
		}

		if (arr[CLASSNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.ClassName", jcas.getCas(), c);
			a.addToCloud();
		}

		if (arr[METHODNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.MethodName", jcas.getCas(), c);
			a.addToCloud();
		}

		if (arr[VARNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.VarName", jcas.getCas(), c);
			a.addToCloud();
		}

		if (arr[PACKAGE]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.Package", jcas.getCas(), c);
			a.addToCloud();
		}

		if (arr[IMPORT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.Import", jcas.getCas(), c);
			a.addToCloud();
		}

		return c;
	}
}