package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.ruta.engine.XMIWriter;
import org.apache.uima.util.CasCreationUtils;
import org.apache.uima.util.InvalidXMLException;
import org.cleartk.syntax.opennlp.PosTaggerAnnotator;
import org.cleartk.syntax.opennlp.SentenceAnnotator;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Token;
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.factory.TypeSystemDescriptionFactory;
import org.uimafit.factory.JCasFactory;
import org.apache.uima.jcas.JCas;
import org.uimafit.pipeline.SimplePipeline;
import org.uimafit.util.CasUtil;
import org.uimafit.util.JCasUtil;

public class ClearTKProcessor {

	private JCas jCas;

	public ClearTKProcessor(JCas jc) {
		jCas = jc;
	}

	public JCas executeClearTK() throws IOException {
		try {
			// CollectionReader collectionReader =
			// UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory.getParentFile());
			// AggregateBuilder builder = createBuilder();
			// System.out.println("EMPIEZA");
			// SimplePipeline.runPipeline(collectionReader,
			// builder.createAggregateDescription());
			// System.out.println("FIN");
			System.out.println("1");

			AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
			jCas = engine.newJCas();
			// TypeSystem t = jCas.getTypeSystem();

			TypeSystemDescription t = TypeSystemDescriptionFactory.createTypeSystemDescription();
			TypeSystem as = jCas.getTypeSystem();
			Iterator<org.apache.uima.cas.Type> types = as.getTypeIterator();
			Vector<org.apache.uima.cas.Type> tipos = new Vector<org.apache.uima.cas.Type>();
			while (types.hasNext()) {
				Type aux=types.next();
				
				// tipos.add(types.next());
			}
			
			
			
			System.out.println("2");
			jCas = CasCreationUtils.createCas(t, null, null).getJCas();
			TypeSystem as2 = jCas.getTypeSystem();
			Iterator<org.apache.uima.cas.Type> types2 = as2.getTypeIterator();
			Vector<org.apache.uima.cas.Type> tipos2 = new Vector<org.apache.uima.cas.Type>();
			while (types.hasNext()) {
				System.out.println(types2.next().getName());
				// tipos.add(types.next());
			}
			System.out.println("1");
			jCas.setDocumentText("//car cars CAR");

			engine.process(jCas);

			// engine.process(jCas);
			System.out.println("2");

			engine = AnalysisEngineFactory.createEngine(SentenceAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("3");

			engine = AnalysisEngineFactory.createEngine(TokenAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("4");

			engine = AnalysisEngineFactory.createEngine(PosTaggerAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("5");

			engine = AnalysisEngineFactory.createEngine(LemmaAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("5");

			engine = AnalysisEngineFactory.createEngine(DefaultSnowballStemmer.getDescription("English"));
			engine.process(jCas);
			System.out.println("6");

			System.out.println("boooooooooooooooooooooooom");
			CAS cas = jCas.getCas();
			org.apache.uima.cas.Type type = cas.getTypeSystem().getType("uima.ruta.annotators.SingleLineComment");
			// System.out.println("boooooooooooooooooooooooom1");
			Collection<AnnotationFS> a = CasUtil.select(cas, type);
			// System.out.println("boooooooooooooooooooooooom111");
			for (AnnotationFS annotation : CasUtil.select(cas, type)) {

				// System.out.println("boooooooooooooooooooooooom2");
				// System.out.println("entro al forr de las anotation");
				// System.out.println(annotation.getCoveredText());
				for (Token token : JCasUtil.selectCovered(jCas, Token.class, annotation)) {

					// System.out.println("entro al forr de los token");

					String aux2 = token.getLemma();
					String[] r2 = aux2.split("[.]");
					for (String s : r2) {
						String[] r3 = s.split("(?=\\p{Upper})");
						if (r3.length != 1) {
							for (int i = 1; i < r3.length; i++) {
								System.out.println(r3[i]);
							}
						} else
							System.out.println(r3[0]);
					}
				}

			}

		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		}
		return jCas;
	}

	// private AggregateBuilder createBuilder() throws
	// ResourceInitializationException, InvalidXMLException, IOException {
	// AggregateBuilder builder = new AggregateBuilder();
	// builder.add(UriToDocumentTextAnnotator.getDescription());
	// builder.add(AnalysisEngineFactory.createAnalysisEngineDescription("main.descriptors.MainEngine"));
	// builder.add(SentenceAnnotator.getDescription());
	// builder.add(TokenAnnotator.getDescription());
	// builder.add(PosTaggerAnnotator.getDescription());
	// builder.add(LemmaAnnotator.getDescription());
	// builder.add(DefaultSnowballStemmer.getDescription("English"));
	// builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class,
	// XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
	// "input", XWriter.PARAM_FILE_NAMER_CLASS_NAME,
	// ViewURIFileNamer.class.getName()));
	// builder.add(AnalysisEngineFactory.createPrimitiveDescription(Tester.class));
	// return builder;
	// }
}
