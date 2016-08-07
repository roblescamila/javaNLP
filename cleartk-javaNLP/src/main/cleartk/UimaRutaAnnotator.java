package main.cleartk;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.cleartk.classifier.feature.extractor.CleartkExtractor;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Count;
import org.cleartk.classifier.feature.extractor.CleartkExtractor.Covered;
import org.cleartk.classifier.feature.extractor.simple.CoveredTextExtractor;
import org.cleartk.token.type.Token;
import org.mcavallo.opencloud.Cloud;
import org.uimafit.util.CasUtil;
import org.uimafit.util.JCasUtil;

public class UimaRutaAnnotator {

	// boolean selected;
	String classPath;
	CAS cas;
	Cloud cloud;

	// path : "uima.ruta.annotators.MethodName"
	public UimaRutaAnnotator(String path, CAS cas, Cloud c) {
		classPath = path;
		cas = cas;
		cloud = c;
	}

	public void addToCloud() throws CASException {
		Type type = cas.getTypeSystem().getType(classPath);
		System.out.println("empieza jcas");
		for (AnnotationFS annotation : CasUtil.select(cas, type)) {
			// System.out.println("entro al forr de las anotation");
			// System.out.println(annotation.getCoveredText());
			for (Token token : JCasUtil.selectCovered(cas.getJCas(), Token.class, annotation)) {
				System.out.println("entro al forr de los token");
				System.out.println("Entre: " + token.getLemma());
				String aux2 = token.getLemma();
				String[] r2 = aux2.split("[.]");
				for (String s : r2) {
					String[] r3 = s.split("(?=\\p{Upper})");
					if (r3.length != 1) {
						for (int i = 1; i < r3.length; i++) {
							cloud.addTag(r3[i]);
						}
					} else
						cloud.addTag(r3[0]);
				}
			}
			System.out.println("termina jcas");
			cloud.addTag(annotation.getCoveredText());
		} // cloud.addTag("b");
	}
}