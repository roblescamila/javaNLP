package main.cleartk;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypePriorities;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.util.InvalidXMLException;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.CollectionReaderFactory;
import org.uimafit.factory.TypePrioritiesFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;

public class ClearTKHelper {

	public static TypeSystemDescription getTypeSystemDescription() throws ResourceInitializationException {
//		System.setProperty("org.uimafit.type.import_pattern", "classpath*:desc/typesystems/**/*.xml");
		return TypeSystemDescriptionFactory.createTypeSystemDescription();
	}
	
	public static TypePriorities getTypePriorities() {
		return TypePrioritiesFactory.createTypePriorities(
			"edu.isistan.uima.unified.typesystems.srs.Project",
			"edu.isistan.uima.unified.typesystems.srs.Document",
			"edu.isistan.uima.unified.typesystems.srs.Section",
			"edu.isistan.uima.unified.typesystems.nlp.Sentence",
			"edu.isistan.uima.unified.typesystems.domain.DomainAction",
			"edu.isistan.uima.unified.typesystems.srl.Predicate",
			"edu.isistan.uima.unified.typesystems.srl.Structure",
			"edu.isistan.uima.unified.typesystems.srl.Argument",
			"edu.isistan.uima.unified.typesystems.nlp.SDDependency",
			"edu.isistan.uima.unified.typesystems.nlp.Chunk",
			"edu.isistan.uima.unified.typesystems.nlp.Entity",
			"edu.isistan.uima.unified.typesystems.srl.Role",
			"edu.isistan.uima.unified.typesystems.domain.DomainNumber",
			"edu.isistan.uima.unified.typesystems.nlp.Token",
			"edu.isistan.uima.unified.typesystems.wordnet.Sense");
	}
	
	public static CollectionReader getXMIReaderCR(TypeSystemDescription typeSystemDescription, TypePriorities typePriorities, String inputFile) throws ResourceInitializationException, InvalidXMLException {
		CollectionReaderDescription crDescription = 
			CollectionReaderFactory.createDescription(XMIReaderCollectionReader.class, typeSystemDescription, typePriorities, 
			"input", inputFile);
		return CollectionReaderFactory.createCollectionReader(crDescription);
	}
	
	public static AnalysisEngine getXMIWriterCC(TypeSystemDescription typeSystemDescription, TypePriorities typePriorities, String outputFile) throws ResourceInitializationException, InvalidXMLException {
		AnalysisEngineDescription aeDescription = 
			AnalysisEngineFactory.createPrimitiveDescription(XMIWriterCasConsumer.class, typeSystemDescription, typePriorities, 
			"output", outputFile);
		return AnalysisEngineFactory.createPrimitive(aeDescription);
	}
}
