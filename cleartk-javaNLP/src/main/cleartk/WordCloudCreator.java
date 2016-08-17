package main.cleartk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CASException;
import org.apache.uima.jcas.JCas;
import org.mcavallo.opencloud.Cloud;

public class WordCloudCreator implements Runnable {

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
	boolean finished;
	
	public WordCloudCreator() {
	}

	public WordCloudCreator(String f) throws IOException, UIMAException {
		file = new File(f);
		fisTargetFile = new FileInputStream(file);
		finished = false;
//		createCas();
	}

	private void createCas() throws IOException, UIMAException {
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
		ClearTKProcessor nlp = new ClearTKProcessor(targetFileStr);
		jcas = nlp.executeClearTK();
	}

	public Cloud updateCloud(boolean arr[], Cloud c) throws CASException {

		if (arr[COMMENT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.SingleLineComment", jcas, c);
			UimaRutaAnnotator b = new UimaRutaAnnotator(
					"uima.ruta.annotators.MultiLineComment", jcas, c);
			a.addToCloud();
			b.addToCloud();
		}

		if (arr[CLASSNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.ClassName", jcas, c);
			a.addToCloud();
		}

		if (arr[METHODNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.MethodName", jcas, c);
			a.addToCloud();
		}

		if (arr[VARNAME]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.VarName", jcas, c);
			a.addToCloud();
		}

		if (arr[PACKAGE]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.Package", jcas, c);
			a.addToCloud();
		}

		if (arr[IMPORT]) {
			UimaRutaAnnotator a = new UimaRutaAnnotator(
					"uima.ruta.annotators.Import", jcas, c);
			a.addToCloud();
		}

		return c;
	}

	@Override
	public void run() {
		String targetFileStr;
		try {
			System.out.println("entra a nlp");
			targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
			ClearTKProcessor nlp = new ClearTKProcessor(targetFileStr);
			jcas = nlp.executeClearTK();
			System.out.println("termina nlp");
			finished = true;
		} catch (UIMAException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean HasFinished(){
		return finished;
	}
}