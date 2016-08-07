package main.cleartk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasIOUtil;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.mcavallo.opencloud.Cloud;

public class WordCloudCreator {

	static final int COMMENT = 0;
	static final int CLASSNAME = 1;
	static final int METHODNAME = 2;
	static final int VARNAME = 3;
	static final int PACKAGE = 4;
	static final int IMPORT = 5;

	FileInputStream fisTargetFile;
	CAS cas;
	File file; 
	String outputPath;

	public WordCloudCreator(String f, String output) throws IOException, InvalidXMLException,
			ResourceInitializationException, AnalysisEngineProcessException, CASException {
		outputPath = output;
		file = new File(f);
		fisTargetFile = new FileInputStream(file);
		createCas();
	}
	
	private void createCas() throws InvalidXMLException, ResourceInitializationException, IOException, AnalysisEngineProcessException, CASException {
		AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
		cas = engine.newCAS();
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
		cas.setDocumentText(targetFileStr);
		engine.process(cas);
		String out = outputPath.concat("/" + file.getName());
		CasIOUtil.writeXmi(cas, new File(out + ".xmi"));
		ClearTKProcessor nlp = new ClearTKProcessor(outputPath);
		nlp.executeClearTK();
	}
	
	public void setCas() throws IOException
	{
		CasIOUtil.readCas(cas, new File(outputPath.concat("/" + file.getName() + ".xmi")));
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