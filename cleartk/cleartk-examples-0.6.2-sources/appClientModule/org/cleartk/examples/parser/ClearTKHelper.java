package org.cleartk.examples.parser;

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
		//System.setProperty("org.uimafit.type.import_pattern", "classpath*:desc/typesystems/**/*.xml");
		return TypeSystemDescriptionFactory.createTypeSystemDescription();
	}
	
	public static TypePriorities getTypePriorities() {
		return TypePrioritiesFactory.createTypePriorities(
			"uima.ruta.annotators.Main",
			"uima.ruta.annotators.Block",
			"uima.ruta.annotators.Class",
			"uima.ruta.annotators.ClassHeader",
			"uima.ruta.annotators.ClassName",
			"uima.ruta.annotators.ClassType",
			"uima.ruta.annotators.Constructor",
			"uima.ruta.annotators.Declaration",
			"uima.ruta.annotators.Implementation",
			"uima.ruta.annotators.Import",
			"uima.ruta.annotators.Inheritance",
			"uima.ruta.annotators.JavaReservedWords",
			"uima.ruta.annotators.Method",
			"uima.ruta.annotators.MethodHeader",
			"uima.ruta.annotators.MethodName",
			"uima.ruta.annotators.MultiLineComment",
			"uima.ruta.annotators.Package",
			"uima.ruta.annotators.Parameters",
			"uima.ruta.annotators.PrimitiveType",
			"uima.ruta.annotators.SinglelineComment",
			"uima.ruta.annotators.VarName"
			);
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
