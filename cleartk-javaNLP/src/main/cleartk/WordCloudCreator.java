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
	JCas cas;
	File file;
	FileInputStream fisTargetFile;

	public WordCloudCreator() {
	}

	public WordCloudCreator(String f) throws IOException, UIMAException {
		file = new File(f);
		fisTargetFile = new FileInputStream(file);
		createCas();
	}

	private void createCas() throws InvalidXMLException, ResourceInitializationException, IOException,
			AnalysisEngineProcessException, CASException {
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
		AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
		cas = engine.newJCas();
		engine.process(cas);
		cas.setDocumentText(targetFileStr);
		ClearTKProcessor nlp = new ClearTKProcessor(cas);
		cas = nlp.executeClearTK();
	}

	public Cloud updateCloud(boolean arr[], Cloud c) throws CASException {

		if (arr[COMMENT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.SingleLineComment", cas, c);
			UimaRutaAnnotator b = new UimaRutaAnnotator("uima.ruta.annotators.MultiLineComment", cas, c);
			a.addToCloud();
			b.addToCloud();
		}

		if (arr[CLASSNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.ClassName", cas, c);
			a.addToCloud();
		}

		if (arr[METHODNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.MethodName", cas, c);
			a.addToCloud();
		}

		if (arr[VARNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.VarName", cas, c);
			a.addToCloud();
		}

		if (arr[PACKAGE]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.Package", cas, c);
			a.addToCloud();
		}

		if (arr[IMPORT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator("uima.ruta.annotators.Import", cas, c);
			a.addToCloud();
		}

		return c;
	}
}