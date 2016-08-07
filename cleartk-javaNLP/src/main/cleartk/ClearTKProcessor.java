package main.cleartk;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.ruta.engine.XMIWriter;
import org.apache.uima.util.InvalidXMLException;
import org.cleartk.syntax.opennlp.PosTaggerAnnotator;
import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.uimafit.pipeline.SimplePipeline;

public class ClearTKProcessor {

	private JCas jCas;
	
	public ClearTKProcessor(JCas jc) {
		jCas = jc;
	}

	public JCas executeClearTK() {
		try {
			// CollectionReader collectionReader =
			// UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory.getParentFile());
			// AggregateBuilder builder = createBuilder();
			// System.out.println("EMPIEZA");
			// SimplePipeline.runPipeline(collectionReader,
			// builder.createAggregateDescription());
			// System.out.println("FIN");

//			JCas jCas = JCasFactory.createJCas();
//			jCas.setDocumentText("car cars carss ");
//			AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
//			engine.process(jCas);
			AnalysisEngine engine = AnalysisEngineFactory.createEngine(SentenceAnnotator.getDescription());
			engine.process(jCas);
			engine = AnalysisEngineFactory.createEngine(TokenAnnotator.getDescription());
			engine.process(jCas);
			engine = AnalysisEngineFactory.createEngine(PosTaggerAnnotator.getDescription());
			engine.process(jCas);
			engine = AnalysisEngineFactory.createEngine(LemmaAnnotator.getDescription());
			engine.process(jCas);
			engine = AnalysisEngineFactory.createEngine(DefaultSnowballStemmer.getDescription("English"));
			engine.process(jCas);
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		}
		return jCas;
	}

//	private AggregateBuilder createBuilder() throws ResourceInitializationException, InvalidXMLException, IOException {
//		AggregateBuilder builder = new AggregateBuilder();
//		builder.add(UriToDocumentTextAnnotator.getDescription());
//		builder.add(AnalysisEngineFactory.createAnalysisEngineDescription("main.descriptors.MainEngine"));
//		builder.add(SentenceAnnotator.getDescription());
//		builder.add(TokenAnnotator.getDescription());
//		builder.add(PosTaggerAnnotator.getDescription());
//		builder.add(LemmaAnnotator.getDescription());
//		builder.add(DefaultSnowballStemmer.getDescription("English"));
//		builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class, XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
//				"input", XWriter.PARAM_FILE_NAMER_CLASS_NAME, ViewURIFileNamer.class.getName()));
//		builder.add(AnalysisEngineFactory.createPrimitiveDescription(Tester.class));
//		return builder;
//	}
}
