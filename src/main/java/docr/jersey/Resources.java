package docr.jersey;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import com.google.common.collect.Lists;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import docr.Annotations;

public class Resources {

	/**
	 * Takes a {@link JavaClass} with a name like TestController and returns
	 * "Test"
	 * 
	 * @param cls
	 * @return
	 */
	public static String toTitle(JavaClass cls) {

		if (cls != null) {

			String name = cls.getName();

			if (name.endsWith("Controller"))
				return name.substring(0, name.length() - "Controller".length());

			return cls.getName();
		}

		return null;

	}

	public static boolean isResource(JavaClass cls) {

		for (Annotation ant : cls.getAnnotations())
			if (ant.getType().getJavaClass().getFullyQualifiedName().equals(Path.class.getName()))
				return true;

		return false;

	}

	public static boolean isWebMethod(JavaMethod method) {

		List<String> list = Lists.newArrayList();
		list.add(GET.class.getName());
		list.add(POST.class.getName());
		list.add(PUT.class.getName());
		list.add(DELETE.class.getName());
		list.add(HEAD.class.getName());
		list.add(OPTIONS.class.getName());

		return hasAnyAnnotation(method, list);

	}

	public static boolean hasAnyAnnotation(JavaMethod method, List<String> annotations) {

		for (String c : annotations)
			if (Annotations.getAnnotation(method.getAnnotations(), c) != null)
				return true;

		return false;

	}

	public static String getPath(JavaMethod method) {

		Annotation annotation = Annotations.getAnnotation(method.getAnnotations(), Path.class.getName());
		return Annotations.getAnnotationValue(annotation);

	}
}
