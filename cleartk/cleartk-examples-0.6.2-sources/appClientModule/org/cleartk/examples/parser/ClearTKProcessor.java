package org.cleartk.examples.parser;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.*;
import org.apache.uima.collection.*;
import org.apache.uima.resource.*;
import org.apache.uima.resource.metadata.*;
import org.apache.uima.util.*;
//import org.cleartk.clearnlp.*;
//import org.cleartk.stanford.StanfordCoreNLPAnnotator;
import org.cleartk.syntax.opennlp.*;
import org.cleartk.timeml.event.*;
import org.cleartk.timeml.time.*;
import org.cleartk.timeml.tlink.*;
import org.cleartk.token.stem.snowball.DefaultSnowballStemmer;
import org.uimafit.factory.*;
import org.uimafit.pipeline.SimplePipeline;

import opennlp.uima.tokenize.Tokenizer;

public class ClearTKProcessor {
	private static String[] filenames = new String[] {"CRS", "HWS", "MSLite" };
	private static String inputPath = "entrada";
	
	public static enum ClearTKPreference {
	    SemanticRoleLabeling, 
	    TimeML,
	    Coreference
	}

	public void appendClearTK(ClearTKPreference preference) {
		for(String filename : filenames)
			appendClearTK(filename, preference);
	}
	
	public void appendClearTK(String filename, ClearTKPreference preference) {
		String inputExtension = ".xmi";
		String outputExtension = ".xmi";
		String outputPath = inputPath;

		String input = inputPath + filename + inputExtension;
		String output = outputPath + filename + "-cleartk" + outputExtension;
		this.executeClearTK(input, output, preference);
	}
	
	public void executeClearTK(String inputFile, String outputFile, ClearTKPreference preference) {
		try {
			// UIMA TypeSystems & Priorities
			TypeSystemDescription typeSystemDescription = ClearTKHelper.getTypeSystemDescription();
			TypePriorities typePriorities = ClearTKHelper.getTypePriorities();
			// Collection Reader
			CollectionReader collectionReader = ClearTKHelper.getXMIReaderCR(typeSystemDescription, typePriorities, inputFile);
			// ClearTK Annotators
		//	AggregateBuilder builder = createBuilder(preference);
			// CAS Writer Consumer
			AnalysisEngine writerCC = ClearTKHelper.getXMIWriterCC(typeSystemDescription, typePriorities, outputFile);
			// Pipeline Execution
			SimplePipeline.runPipeline(
					collectionReader,
				//	builder.createAggregate(),
					writerCC);
		} catch (ResourceInitializationException e) {
			e.printStackTrace();
		} catch (InvalidXMLException e) {
			e.printStackTrace();
		} catch (UIMAException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	private AggregateBuilder createBuilder(ClearTKPreference preference) throws ResourceInitializationException {
//		switch (preference) {
//		case SemanticRoleLabeling:
//			return createSemanticRoleLabelingBuilder();
//		case TimeML:
//			return createTimeMLBuilder();
//		case Coreference:
//			return createCoreferenceBuilder();
//		default:
//			return null;
//		}
//	}
	
//	private AggregateBuilder createSemanticRoleLabelingBuilder() throws ResourceInitializationException {
//		AggregateBuilder builder = new AggregateBuilder();
//		builder.add(SentenceAnnotator.getDescription());
//		builder.add(Tokenizer.getDescription());
//		builder.add(PosTagger.getDescription());
//		builder.add(MPAnalyzer.getDescription());
//		builder.add(DependencyParser.getDescription());
//		builder.add(SemanticRoleLabeler.getDescription());
//		return builder;
//	}

	private AggregateBuilder createTimeMLBuilder() throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();
		builder.add(org.cleartk.syntax.opennlp.SentenceAnnotator.getDescription());
		builder.add(org.cleartk.token.tokenizer.TokenAnnotator.getDescription());
		builder.add(org.cleartk.syntax.opennlp.PosTaggerAnnotator.getDescription());
		builder.add(DefaultSnowballStemmer.getDescription("English"));
		builder.add(org.cleartk.syntax.opennlp.ParserAnnotator.getDescription());
		builder.add(TimeAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(TimeTypeAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventTenseAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventAspectAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventClassAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventPolarityAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(EventModalityAnnotator.FACTORY.getAnnotatorDescription());
		//builder.add(TemporalLinkEventToDocumentCreationTimeAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(TemporalLinkEventToSameSentenceTimeAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(TemporalLinkMainEventToNextSentenceMainEventAnnotator.FACTORY.getAnnotatorDescription());
		//builder.add(TemporalLinkEventToSubordinatedEventAnnotator.FACTORY.getAnnotatorDescription());
		builder.add(VerbClauseTemporalAnnotator.FACTORY.getAnnotatorDescription());
		return builder;
	}

	private AggregateBuilder createCoreferenceBuilder() throws ResourceInitializationException {
		AggregateBuilder builder = new AggregateBuilder();
		builder.add(org.cleartk.syntax.opennlp.SentenceAnnotator.getDescription());
	//	builder.add(StanfordCoreNLPAnnotator.getDescription());
		return builder;
	}
	
	public static void main(String[] args) {
		ClearTKProcessor tester = new ClearTKProcessor();
		ClearTKPreference preference = ClearTKPreference.Coreference;
		// Global actions
		tester.appendClearTK(preference);
		// Single actions
		//tester.appendClearTK(filenames[1], preference);
	}
}