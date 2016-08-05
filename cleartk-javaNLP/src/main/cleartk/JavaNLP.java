//// PrintWriter xmlOut = new PrintWriter("xmlOutput.xml");
//		// Properties props = new Properties();
//		// props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,
//		// parse");
//		// StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//		// Annotation annotation = new Annotation("This is a short sentence. And
//		// this is another.");
//		// pipeline.annotate(annotation);
//		// pipeline.xmlPrint(annotation, xmlOut);
//		// // An Annotation is a Map and you can get and use the
//		// // various analyses individually. For instance, this
//		// // gets the parse tree of the 1st sentence in the text.
//		// List<CoreMap> sentences =
//		// annotation.get(CoreAnnotations.SentencesAnnotation.class);
//		// if (sentences != null && sentences.size() > 0) {
//		// CoreMap sentence = sentences.get(0);
//		// Tree tree = sentence.get(TreeAnnotation.class);
//		// PrintWriter out = new PrintWriter(System.out);
//		// out.println("The first sentence parsed is:");
//		// tree.pennPrint(out);
//		// }
package main.cleartk;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import org.apache.commons.io.IOUtils;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.cleartk.clearnlp.MPAnalyzer;
import org.cleartk.clearnlp.PosTagger;
import org.cleartk.clearnlp.Tokenizer;
import org.cleartk.examples.documentclassification.basic.BasicDocumentClassificationAnnotator;
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
import org.uimafit.pipeline.SimplePipeline;
import org.uimafit.util.CasUtil;

public class JavaNLP {

	public static void main(String[] args) throws Exception {

//		File filesDirectory = new File("input");
		FileInputStream fisTargetFile = new FileInputStream(new File("c:\\Users\\Cami\\Documents\\Faca\\Materias\\4to\\Diseño\\javanlp\\example-projects\\ExampleProject\\output\\test.txt.xmi"));
		String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
//		String outputDirectory = "output";

		boolean comment, classname, methodname, realvarname, formalvarname, packag, impor;

		comment = true;
		classname = true;
		methodname = true;
		realvarname = true;
		formalvarname = true;
		packag = true;
		impor = true;

		
		String inputEngine = "main.descriptors.MainEngine";
		AnalysisEngine engine = AnalysisEngineFactory.createEngine(inputEngine);
		CAS cas = engine.newCAS();		
		cas.setDocumentText(targetFileStr);
		
//		Vector<String> comments = new Vector<String>();
//		Vector<String> classnames = new Vector<String>();
//		Vector<String> methodnames = new Vector<String>();
//		Vector<String> formalvarnames = new Vector<String>();
//		Vector<String> realvarnames = new Vector<String>();
//		Vector<String> packages = new Vector<String>();
//		Vector<String> imports = new Vector<String>();

		// create the annotation engine to extract dates
//		final AnalysisEngine engine = AnalysisEngineFactory.createEngine("main.descriptors.MainEngine");
//		final CAS cas = engine.newCAS();
//		cas.setDocumentText(targetFileStr);

		// annotate the document
//		engine.process(cas);

		// extract dates from the annotated document
		if (comment) {
//			UimaRutaANnotator
//			final Type SingleLineCommentType = cas.getTypeSystem().getType("uima.ruta.annotators.SingleLineComment");
//			for (AnnotationFS type : CasUtil.select(cas, SingleLineCommentType)) {
//				comments.add(type.getCoveredText());
//			}
//
//			final Type MultiLineCommentType = cas.getTypeSystem().getType("uima.ruta.annotators.MultiLineComment");
//			for (AnnotationFS type : CasUtil.select(cas, MultiLineCommentType)) {
//				comments.add(type.getCoveredText());
//			}
		}

		if (classname) {
//			final Type ClassNameType = cas.getTypeSystem().getType("uima.ruta.annotators.ClassName");
//			for (AnnotationFS type : CasUtil.select(cas, ClassNameType)) {
//				classnames.add(type.getCoveredText());
//			}
		}

		if (methodname) {
//			final Type MethodNameType = cas.getTypeSystem().getType("uima.ruta.annotators.MethodName");
//			for (AnnotationFS type : CasUtil.select(cas, MethodNameType)) {
//				comments.add(type.getCoveredText());
//			}
		}

		// if (realvarname) {
		// final Type SingleLineCommentType =
		// cas.getTypeSystem().getType("uima.ruta.annotators.SingleLineComment");
		// for (AnnotationFS type : CasUtil.select(cas, SingleLineCommentType))
		// {
		// comments.add(type.getCoveredText());
		// }
		// }
		//
		// if (formalvarname) {
		// final Type SingleLineCommentType =
		// cas.getTypeSystem().getType("uima.ruta.annotators.SingleLineComment");
		// for (AnnotationFS type : CasUtil.select(cas, SingleLineCommentType))
		// {
		// comments.add(type.getCoveredText());
		// }
		// }

		if (packag) {
//			final Type SingleLineCommentType = cas.getTypeSystem().getType("uima.ruta.annotators.Package");
//			for (AnnotationFS type : CasUtil.select(cas, SingleLineCommentType)) {
//				packages.add(type.getCoveredText());
//			}
		}

		if (impor) {
//			final Type SingleLineCommentType = cas.getTypeSystem().getType("uima.ruta.annotators.Import");
//			for (AnnotationFS type : CasUtil.select(cas, SingleLineCommentType)) {
//				imports.add(type.getCoveredText());
//			}
		}

//		System.out.println("Comments:");
//		for (int i = 0; i < comments.size(); i++) {
//			System.out.println(comments.elementAt(i));
//		}
//
//		System.out.println("Imports:");
//		for (int i = 0; i < imports.size(); i++) {
//			System.out.println(imports.elementAt(i));
//		}
//
//		System.out.println("Packages:");
//		for (int i = 0; i < packages.size(); i++) {
//			System.out.println(packages.elementAt(i));
//		}

//		AggregateBuilder builder = new AggregateBuilder();
//		builder.add(UriToDocumentTextAnnotator.getDescription());
//		builder.add(SentenceAnnotator.getDescription());
//		builder.add(TokenAnnotator.getDescription());
//		builder.add(AnalysisEngineFactory.createPrimitiveDescription(XWriter.class, XWriter.PARAM_OUTPUT_DIRECTORY_NAME,
//				outputDirectory, XWriter.PARAM_FILE_NAMER_CLASS_NAME, ViewURIFileNamer.class.getName()));
//		builder.add(PosTaggerAnnotator.getDescription());
//		builder.add(LemmaAnnotator.getDescription());
//		builder.add(DefaultSnowballStemmer.getDescription("English"));
//		// builder.add(AnalysisEngineFactory.createPrimitiveDescription(BasicDocumentClassificationAnnotator.class));
//		SimplePipeline.runPipeline(UriCollectionReader.getCollectionReaderFromDirectory(filesDirectory),
//				builder.createAggregateDescription());
		System.out.println("FIN");
	}
}