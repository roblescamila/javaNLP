package main.cleartk;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
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
	public UimaRutaAnnotator(String path, CAS inputCas, Cloud c) {
		classPath = path;
		cas = inputCas;
		cloud = c;
	}

	public void addToCloud() throws CASException {
		Type type = cas.getTypeSystem().getType(classPath);
		JCas a = cas.getJCas();
		System.out.println("entro al add cloud");
		for (AnnotationFS annotation : CasUtil.select(cas, type)) {
			System.out.println("entro al forr de las anotation");
			System.out.println(annotation.getCoveredText());
			for (Token token : JCasUtil.select(annotation.getCAS().getJCas(), Token.class)) {
				System.out.println("entro al forr de los token");
				System.out.println("ETNREERTE: " + token.getFeatureValue((Feature) new CoveredTextExtractor()));
				String aux2 = token.getFeatureValue((Feature) new CoveredTextExtractor()).toString();
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
//			cloud.addTag(annotation.getCoveredText());
		}  cloud.addTag("b");
	}
}