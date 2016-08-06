package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.uima.UIMAException;
import org.apache.uima.cas.CAS;
import org.apache.uima.resource.*;
import org.apache.uima.util.*;
import org.cleartk.syntax.opennlp.*;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.factory.*;
import org.uimafit.pipeline.SimplePipeline;

public class ClearTKProcessor {
	
	private static File filesDirectory;
	private static String outputDir;
	private static CAS cas;
	
	public ClearTKProcessor (String input, CAS c){
		filesDirectory = new File(input);
		cas = c;
	}
	
	public void executeClearTK(){
		try {
			AggregateBuilder builder = createBuilder();
			SimplePipeline.runPipeline(cas, builder.createAggregateDescription());
		} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		}
	}

	private AggregateBuilder createBuilder() throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();
		builder.add(UriToDocumentTextAnnotator.getDescription());
		builder.add(SentenceAnnotator.getDescription());
		builder.add(TokenAnnotator.getDescription());
		builder.add(PosTaggerAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		builder.add(LemmaAnnotator.getDescription());
		return builder;
	}

//	public static void main(String[] args) throws UnsupportedEncodingException {
//		ClearTKProcessor tester = new ClearTKProcessor("input","output");
//		tester.executeClearTK("output");
//	}
}
