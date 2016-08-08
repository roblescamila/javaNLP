package main.cleartk;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
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
import org.cleartk.util.ViewURIFileNamer;
import org.cleartk.util.ae.UriToDocumentTextAnnotator;
import org.cleartk.util.cr.UriCollectionReader;
import org.uimafit.component.xwriter.XWriter;
import org.uimafit.factory.AggregateBuilder;
import org.uimafit.factory.JCasFactory;
import org.uimafit.factory.JCasFactory;
import org.uimafit.pipeline.SimplePipeline;

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

	public void executeClearTK() throws UIMAException, IOException {
			
			String[] a = { "uima.ruta.annotators.MainTypeSystem", 
					"org.cleartk.token.type.Token"
					// ,"org.apache.uima.ruta.type.AnyLine"
					// ,"org.apache.uima.ruta.type.Line"
					// ,"org.apache.uima.ruta.type.Paragraph"
					// ,"org.apache.uima.ruta.type.WSLine"
					// ,"org.apache.uima.ruta.type.html.TAG"
					// ,"org.apache.uima.ruta.type.html.TBODY"
					// ,"org.apache.uima.ruta.type.html.TD"
					// ,"org.apache.uima.ruta.type.html.TH"
					// ,"org.apache.uima.ruta.type.html.TITLE"
					// ,"org.apache.uima.ruta.type.html.TR"
					// ,"org.apache.uima.ruta.type.html.U"
					// ,"org.apache.uima.ruta.type.html.UL"
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
					// ,"org.cleartk.type.ne.NamedEntityClass"
					// ,"org.cleartk.type.test.AuthorInfo"
					// ,"org.cleartk.type.test.Chunk"
					// ,"org.cleartk.type.test.DependencyRelation"
					// ,"org.cleartk.type.test.Header"
					// ,"org.cleartk.type.test.Language"
					// ,"org.cleartk.type.test.Lemma"
					// ,"org.cleartk.type.test.NamedEntityMention"
					// ,"org.cleartk.type.test.Orthography"
					// ,"org.cleartk.type.test.POSTag"
					// ,"org.cleartk.type.test.Sentence"
					// ,"org.cleartk.type.test.Token"
					, "org.cleartk.util.type.Parenthetical"

					// ,"uima.ruta.annotators.Constructor.ConstructorName"

					// ,"uima.ruta.annotators.MethodHeader.MethodType"

					// ,"uima.ruta.annotators.MultiLineComment.EndComment"
					// ,"uima.ruta.annotators.MultiLineComment.IniComment"

					// ,"uima.ruta.annotators.Parameters.EndParameters"
					// ,"uima.ruta.annotators.Parameters.InitParameters"

					// ,"uima.ruta.annotators.SingleLineComment.InitComment"

					// ,"org.apache.uima.ruta.type.html.A"
					// ,"org.apache.uima.ruta.type.html.B"
					// ,"org.apache.uima.ruta.type.html.BIG"
					// ,"org.apache.uima.ruta.type.html.BODY"
					// ,"org.apache.uima.ruta.type.html.BR"
					// ,"org.apache.uima.ruta.type.html.COMMENT"
					// ,"org.apache.uima.ruta.type.html.DIV"
					// ,"org.apache.uima.ruta.type.html.EM"
					// ,"org.apache.uima.ruta.type.html.FONT"
					// ,"org.apache.uima.ruta.type.html.FORM"
					// ,"org.apache.uima.ruta.type.html.H1"
					// ,"org.apache.uima.ruta.type.html.H2"
					// ,"org.apache.uima.ruta.type.html.H3"
					// ,"org.apache.uima.ruta.type.html.H4"
					// ,"org.apache.uima.ruta.type.html.H5"
					// ,"org.apache.uima.ruta.type.html.H6"
					// ,"org.apache.uima.ruta.type.html.HEAD"
					// ,"org.apache.uima.ruta.type.html.HTML"
					// ,"org.apache.uima.ruta.type.html.I"
					// ,"org.apache.uima.ruta.type.html.IMG"
					// ,"org.apache.uima.ruta.type.html.INPUT"
					// ,"org.apache.uima.ruta.type.html.LI"
					// ,"org.apache.uima.ruta.type.html.LINK"
					// ,"org.apache.uima.ruta.type.html.META"
					// ,"org.apache.uima.ruta.type.html.OL"
					// ,"org.apache.uima.ruta.type.html.P"
					// ,"org.apache.uima.ruta.type.html.REMARK"
					// ,"org.apache.uima.ruta.type.html.SCRIPT"
					// ,"org.apache.uima.ruta.type.html.SMALL"
					// ,"org.apache.uima.ruta.type.html.SPAN"
					// ,"org.apache.uima.ruta.type.html.STYLE"
					// ,"org.apache.uima.ruta.type.html.TABLE"
					, "org.cleartk.ne.type.GazetteerNamedEntityMention",
					"org.cleartk.syntax.constituent.type.TerminalTreebankNode",
					"org.cleartk.syntax.constituent.type.TopTreebankNode",
					"org.cleartk.timeml.type.DocumentCreationTime"

					// , "org.cleartk.ne.type.NamedEntityMention[]"
					// , "org.cleartk.srl.type.Argument[]"

					// ,"uima.tcas.Annotation[]"
					// ,"org.cleartk.syntax.constituent.type.TreebankNode[]"
					// ,"org.cleartk.syntax.dependency.type.DependencyRelation[]"
					// ,"org.cleartk.type.test.AuthorInfo[]"
					// ,"org.cleartk.type.test.POSTag[]"
					// ,"org.cleartk.type.test.DependencyRelation[]"
					// ,"org.cleartk.syntax.constituent.type.TerminalTreebankNode[]"
			};

			// a[0] = "uima.ruta.annotators.MainTypeSystem";
			// a[1] = "org.cleartk.token.type.Token";
System.out.println("..............");
			JCas jCas = JCasFactory.createJCas(a);
	
	  
			AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
			//JCas	jCas = JCasFactory.createJCas();
			  jCas.setDocumentText("//car cars ");
			engine.process(jCas);System.out.println("..............");
			   engine = AnalysisEngineFactory.createEngine(SentenceAnnotator.getDescription());
			  
			engine.process(jCas);
			System.out.println("..............");
			engine = AnalysisEngineFactory.createEngine(TokenAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("..............");
			engine = AnalysisEngineFactory.createEngine(PosTaggerAnnotator.getDescription());
			engine.process(jCas)
			;System.out.println("..............");
			engine = AnalysisEngineFactory.createEngine(LemmaAnnotator.getDescription());
			engine.process(jCas);
			System.out.println("..............");
			engine = AnalysisEngineFactory.createEngine(DefaultSnowballStemmer.getDescription("English"));
			engine.process(jCas);
			System.out.println("..............");
		
			
//			SimplePipeline.runPipeline(colectionReader, builder.createAggregateDescription());
			System.out.println("FIN");
		
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