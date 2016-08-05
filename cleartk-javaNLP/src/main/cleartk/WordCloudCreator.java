package main.cleartk;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Token;
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.mcavallo.opencloud.Cloud;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.pipeline.SimplePipeline;
import org.uimafit.util.CasUtil;

public class WordCloudCreator {

	static final int COMMENT = 0;
	static final int CLASSNAME = 1;
	static final int METHODNAME = 2;
	static final int VARNAME = 3;
	static final int PACKAGE = 4;
	static final int IMPORT = 5;

	FileInputStream fisTargetFile;// = new FileInputStream(new
									// File("c:\\Users\\Cami\\Documents\\Faca\\Materias\\4to\\Dise�o\\javanlp\\example-projects\\ExampleProject\\output\\test.txt.xmi"));
	String targetFileStr;// = IOUtils.toString(fisTargetFile, "UTF-8");

	public WordCloudCreator() throws IOException {
		FileInputStream fisTargetFile = new FileInputStream(new File(
				"c:\\Users\\Cami\\Documents\\Faca\\Materias\\4to\\Dise�o\\javanlp\\example-projects\\ExampleProject\\output\\test.txt.xmi"));
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
	}

	public Cloud CreateCloud(boolean arr[]) throws InvalidXMLException, ResourceInitializationException, IOException {
		Cloud c = new Cloud();
		String inputEngine = "main.descriptors.MainEngine";
		AnalysisEngine engine = AnalysisEngineFactory.createEngine(inputEngine);
		CAS cas = engine.newCAS();
		cas.setDocumentText(targetFileStr);

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

	public static void main(String[] args) throws Exception {
		System.out.println("FIN");
	}
}