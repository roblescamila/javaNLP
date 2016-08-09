package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.*;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.*;
import org.cleartk.syntax.opennlp.*;
import org.cleartk.token.lemma.choi.LemmaAnnotator;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.cleartk.token.tokenizer.TokenAnnotator;
import org.cleartk.token.type.Sentence;
import org.cleartk.token.type.Token;
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.JCasFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;
import org.uimafit.util.JCasUtil;

public class ClearTKProcessor {

	private static File filesDirectory;

	public ClearTKProcessor() {
		// filesDirectory = new File(input);// new File("file:///" + input);
		// filesDirectory = input;
	}

	public static void main(String[] args) throws UIMAException, IOException {
		ClearTKProcessor a = new ClearTKProcessor();
		a.executeClearTK();
	}
public String[] getTypeSystemDescriptor()

{
	String[] a = { "uima.ruta.annotators.MainTypeSystem", 
			"org.cleartk.token.type.Token"
			, "org.cleartk.examples.type.UsenetDocument", "org.cleartk.ne.type.Ace2005Document",
			"org.cleartk.ne.type.Chunk", "org.cleartk.ne.type.NamedEntity",
			"org.cleartk.ne.type.NamedEntityMention", "org.cleartk.srl.type.Argument",
			"org.cleartk.srl.type.Chunk", "org.cleartk.srl.type.Predicate",
			"org.cleartk.srl.type.SemanticArgument", "org.cleartk.summarization.type.SummarySentence",
			"org.cleartk.syntax.constituent.type.TreebankNode",
			"org.cleartk.syntax.dependency.type.DependencyNode",
			"org.cleartk.syntax.dependency.type.DependencyRelation",
			"org.cleartk.syntax.dependency.type.TopDependencyNode", "org.cleartk.timeml.type.Anchor",
			"org.cleartk.timeml.type.Event", "org.cleartk.timeml.type.TemporalLink",
			"org.cleartk.timeml.type.Text", "org.cleartk.timeml.type.Time", "org.cleartk.token.type.Sentence",
			"org.cleartk.token.type.Subtoken", "org.cleartk.token.type.Token"
			, "org.cleartk.util.type.Parenthetical"
			, "org.cleartk.ne.type.GazetteerNamedEntityMention",
			"org.cleartk.syntax.constituent.type.TerminalTreebankNode",
			"org.cleartk.syntax.constituent.type.TopTreebankNode",
			"org.cleartk.timeml.type.DocumentCreationTime"

	};
return a;
}

public Vector<AnalysisEngine> getPipeline() throws InvalidXMLException, ResourceInitializationException, IOException{
	Vector<AnalysisEngine> aux = new Vector<AnalysisEngine>();
	aux.add(AnalysisEngineFactory.createEngine("main.descriptors.MainEngine"));
	 aux.add(AnalysisEngineFactory.createEngine(SentenceAnnotator.getDescription()));
	 aux.add(AnalysisEngineFactory.createEngine(TokenAnnotator.getDescription()));
	 aux.add( AnalysisEngineFactory.createEngine(PosTaggerAnnotator.getDescription()));
	 aux.add(AnalysisEngineFactory.createEngine(LemmaAnnotator.getDescription()));
	 aux.add(AnalysisEngineFactory.createEngine(DefaultSnowballStemmer.getDescription("English")));
	 
	return aux;
	
}

public JCas executeClearTK() throws UIMAException, IOException {

	String[] typeSystemDescriptor = this.getTypeSystemDescriptor();
	JCas jCas = JCasFactory.createJCas(typeSystemDescriptor);
	jCas.setDocumentText("//MatiPrado \n //mati ");
	 Vector<AnalysisEngine> engines =  this.getPipeline();
	 for (AnalysisEngine engine : engines)
	 {
		engine.process(jCas); 
	 }

			System.out.println("FIN");
			return jCas;
				}	

	
	
	
	

	private AggregateBuilder createBuilder() throws UIMAException, IOException {
		AggregateBuilder builder = new AggregateBuilder();
		builder.add(UriToDocumentTextAnnotator.getDescription());
		builder.add(AnalysisEngineFactory.createAnalysisEngineDescription("main.descriptors.MainEngine"));
		builder.add(SentenceAnnotator.getDescription());
		builder.add(TokenAnnotator.getDescription());
		builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class, XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
				"output", XWriter.PARAM_FILE_NAMER_CLASS_NAME, ViewURIFileNamer.class.getName()));
		builder.add(PosTaggerAnnotator.getDescription());
		builder.add(LemmaAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		builder.add(Tester.getDescription());
		return builder;
	}
}