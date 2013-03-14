package docr;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;

public class Classes {

	public static boolean isController(JavaClass cls) {

		for (Annotation ant : cls.getAnnotations())
			// TODO move to function
			if (ant.getType().getJavaClass().getFullyQualifiedName().equals(org.springframework.stereotype.Controller.class.getName()))
				return true;

		return false;

	}

	public static Annotation getRequestMapping(JavaClass cls) {

		Annotation[] annotations = cls.getAnnotations();

		Annotation ant = Annotations.getAnnotation(annotations, org.springframework.web.bind.annotation.RequestMapping.class.getName());

		return ant;

	}

	public static String getRequestMappingAsString(JavaClass cls) {

		Annotation ant = getRequestMapping(cls);

		return (ant != null) ? Annotations.getAnnotationValue(ant) : "";

	}

}
