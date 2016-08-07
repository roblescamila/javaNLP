package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.*;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.*;
import org.cleartk.syntax.opennlp.*;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Sentence;
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.*;
import org.uimafit.pipeline.SimplePipeline;

public class ClearTKProcessor {

	private static File filesDirectory;

	public ClearTKProcessor(String input) {
		filesDirectory = new File(input);// new File("file:///" + input);
	}

	public void executeClearTK() {
		try {
			CollectionReader colectionReader = UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory);
			AggregateBuilder builder = createBuilder();
			System.out.println("EMPIEZA");
			SimplePipeline.runPipeline(colectionReader, builder.createAggregateDescription());
			System.out.println("FIN");
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
		builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class, XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
				"output", XWriter.PARAM_FILE_NAMER_CLASS_NAME, ViewURIFileNamer.class.getName()));
		builder.add(PosTaggerAnnotator.getDescription());
		builder.add(LemmaAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		return builder;
	}
}
