package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.uima.UIMAException;
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

	private static File filesDirectory = new File("input");

	public static enum ClearTKPreference {
		Comments
	}

	public void executeClearTK(String outputFile, ClearTKPreference preference) {
		try {
			// ClearTK Annotators
			AggregateBuilder builder = createBuilder(preference);
			// Pipeline Execution
			SimplePipeline.runPipeline(UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory),
					builder.createAggregateDescription());
		} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		}
	}

	private AggregateBuilder createBuilder(ClearTKPreference preference) throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();
		builder.add(UriToDocumentTextAnnotator.getDescription());
		builder.add(SentenceAnnotator.getDescription());
		builder.add(TokenAnnotator.getDescription());
		builder.add(PosTaggerAnnotator.getDescription());
		builder.add(LemmaAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		return builder;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		ClearTKProcessor tester = new ClearTKProcessor();
		ClearTKPreference preference = ClearTKPreference.Comments;
		tester.executeClearTK("output", preference);
	}
}
