package docr.jersey;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import docr.Annotations;
import docr.spring.Methods;

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

			if (name.endsWith("Resource"))
				return name.substring(0, name.length() - "Resource".length());

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

	public static boolean hasAnyAnnotation(JavaParameter parameter, List<String> annotations) {

		for (String c : annotations)
			if (Annotations.getAnnotation(parameter.getAnnotations(), c) != null)
				return true;

		return false;

	}

	public static Annotation getHttpMethod(JavaMethod method) {

		List<String> list = Lists.newArrayList();
		list.add(GET.class.getName());
		list.add(POST.class.getName());
		list.add(PUT.class.getName());
		list.add(DELETE.class.getName());
		list.add(HEAD.class.getName());
		list.add(OPTIONS.class.getName());

		for (String c : list)
			for (Annotation a : method.getAnnotations()) {
				String first = a.getType().getFullyQualifiedName();

				if (Objects.equal(first, c))
					return a;

			}

		return null;

	}

	public static String getPath(JavaMethod method) {

		Annotation annotation = Annotations.getAnnotation(method.getAnnotations(), Path.class.getName());
		return Annotations.getAnnotationValue(annotation);

	}

	public static List<JavaParameter> getRequestParams(JavaMethod m) {

		List<JavaParameter> params = Lists.newArrayList();
		params.addAll(Methods.getMatchingAnnotations(m, FormParam.class));
		params.addAll(Methods.getMatchingAnnotations(m, QueryParam.class));

		return params;

	}

	public static List<JavaParameter> getCookieParams(JavaMethod m) {
		return Methods.getMatchingAnnotations(m, CookieParam.class);
	}

	public static List<JavaParameter> getHeaderParams(JavaMethod m) {
		return Methods.getMatchingAnnotations(m, HeaderParam.class);
	}

	public static List<JavaParameter> getPathParams(JavaMethod m) {
		return Methods.getMatchingAnnotations(m, PathParam.class);
	}

	public static String getName(JavaParameter parameter) {

		ArrayList<Class<? extends java.lang.annotation.Annotation>> params = Lists.newArrayList(FormParam.class, QueryParam.class, HeaderParam.class, CookieParam.class, PathParam.class);

		for (Class<? extends java.lang.annotation.Annotation> target : params)
			for (Annotation ann : parameter.getAnnotations()) {

				String first = ann.getType().getFullyQualifiedName();
				String second = target.getCanonicalName();
				if (Objects.equal(first, second)) {
					return ann.getNamedParameter("value") + "";
				}

			}

		return null;
	}
}
