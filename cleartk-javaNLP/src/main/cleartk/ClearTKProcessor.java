package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.*;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.*;
import org.cleartk.syntax.opennlp.*;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Sentence;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.factory.*;
import org.uimafit.pipeline.SimplePipeline;

public class ClearTKProcessor {
	
//	private static File filesDirectory;
	private static String filesDirectory;
	private static String outputDir;
	private static CAS cas;
	private static AnalysisEngine engine;
	
	public ClearTKProcessor (String input, CAS c, AnalysisEngine e){
		filesDirectory = "file:///" + input;//new File("file:///" + input);
		cas = c;
		engine = e;
	}
	
	public void executeClearTK(){
		try {
			
			TypeSystemDescription typeSystemDescription = TypeSystemDescriptionFactory.createTypeSystemDescription();
			CollectionReaderDescription crDescription = CollectionReaderFactory.createDescription(XMICollectionReader.class, typeSystemDescription, "input", filesDirectory);

		
			AggregateBuilder builder = createBuilder();
			System.out.println("EMPIEZA");
			SimplePipeline.runPipeline(crDescription, builder.createAggregateDescription());
			System.out.println("FIN");
		} catch (InvalidXMLException | ResourceInitializationException | IOException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		}
	}

	private AggregateBuilder createBuilder() throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();
//		builder.add(UriToDocumentTextAnnotator.getDescription());
		TypeSystemDescriptionFactory.createTypeSystemDescription();
		builder.add(SentenceAnnotator.getDescription());
		builder.add(TokenAnnotator.getDescription());
		builder.add(PosTaggerAnnotator.getDescription());
		builder.add(LemmaAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		return builder;
	}
}
