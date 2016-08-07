package main.cleartk;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.mcavallo.opencloud.Cloud;
import org.uimafit.util.CasUtil;

public class UimaRutaAnnotator {

	// boolean selected;
	String classPath;
	CAS cas;
	Cloud cloud;

	// path : "uima.ruta.annotators.MethodName"
	public UimaRutaAnnotator(String path, CAS inputCas, Cloud c) {
		// selected = false;
		classPath = path;
		cas = inputCas;
		cloud = c;
	}

	public void addToCloud() throws CASException {
		Type type = cas.getTypeSystem().getType(classPath);
		System.out.println("empieza jcas");

		JCas a = cas.getJCas();
		System.out.println("termina jcas");
		for (AnnotationFS annotation : CasUtil.select(cas, type)) {
			// for (Token token : JCasUtil.select(a, Token.class)) {
			// System.out.println("ETNREERTE: " + token.getLemma());
			// String aux2 = token.getLemma();
			// String[] r2 = aux2.split("[.]");
			// for (String s : r2) {
			// String[] r3 = s.split("(?=\\p{Upper})");
			// if (r3.length != 1) {
			// for (int i = 1; i < r3.length; i++) {
			// cloud.addTag(r3[i]);
			// }
			// } else
			// cloud.addTag(r3[0]);
			// }
			// }
			cloud.addTag(annotation.getCoveredText());
		} // cloud.addTag("b");
	}
}