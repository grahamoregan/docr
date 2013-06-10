package docr.spring;

import static com.google.common.base.Strings.isNullOrEmpty;
import static docr.Annotations.getAnnotationValue;
import static java.lang.String.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.DocletTag;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;

import docr.Annotations;
import docr.Classes;
import docr.Parameters;

public class Methods {

	/**
	 * Does the method have a request mapping?
	 * 
	 * @return
	 */
	public static boolean isWebMethod(JavaMethod method) {

		Annotation ant = getRequestMapping(method);

		return (ant != null) ? true : false;

	}

	public static String getHttpMethod(JavaMethod method) {

		Annotation ant = Annotations.getAnnotation(method.getAnnotations(), org.springframework.web.bind.annotation.RequestMapping.class.getName());

		if (ant == null)
			return "";

		Object i = ant.getNamedParameter("method");

		String result = (i != null) ? i.toString() : "";

		if (result != null) {

			// TODO this could be an array so could be book-ended by '[' & ']'
			result = result.substring((result.indexOf('.') + 1), result.length());

			return result;
		}

		return "GET";

	}

	public static String getComment(JavaMethod method) {

		return method.getComment();

	}

	public static Annotation getRequestMapping(JavaMethod method) {

		Annotation[] annotations = method.getAnnotations();

		Annotation ant = Annotations.getAnnotation(annotations, RequestMapping.class.getName());

		return ant;

	}

	public static String getRequestMappingAsString(JavaMethod method) {

		String classMapping = Classes.getRequestMappingAsString(method.getParentClass());

		Annotation[] annotations = method.getAnnotations();

		Annotation ant = Annotations.getAnnotation(annotations, RequestMapping.class.getName());

		return (ant != null) ? format("%s%s", classMapping, getAnnotationValue(ant)) : "";

	}

	public static List<JavaParameter> getRequestParams(JavaMethod m) {

		return getMatchingAnnotations(m, RequestParam.class);

	}

	public static List<JavaParameter> getRequestHeaders(JavaMethod m) {

		return getMatchingAnnotations(m, RequestHeader.class);

	}

	public static List<JavaParameter> getCookieValues(JavaMethod m) {

		return getMatchingAnnotations(m, CookieValue.class);

	}

	public static List<JavaParameter> getPathVariables(JavaMethod m) {

		return getMatchingAnnotations(m, PathVariable.class);

	}

	public static List<JavaParameter> getMatchingAnnotations(JavaMethod m, Class<? extends Object> c) {

		List<JavaParameter> params = new ArrayList<JavaParameter>();

		for (JavaParameter p : m.getParameters()) {

			for (Annotation a : p.getAnnotations())
				if (a.getType().isA(new Type(c.getName())))
					params.add(p);
		}

		return params;

	}

	public static Boolean hasAnnotation(JavaMethod method, String fullClassName) {

		Annotation[] annotations = method.getAnnotations();

		Annotation ant = Annotations.getAnnotation(annotations, fullClassName);

		return ant != null;

	}

	/**
	 * 
	 * TODO should this be in {@link Parameters} instead?
	 * 
	 * Given the {@link DocletTag}s for a comment, this returns name and value
	 * as a map. For example, if it were run on this method then it would return
	 * a map with one entry {"tags" : "the comment's tags"}
	 * 
	 * @param tags
	 *            the comment's tags
	 * @return
	 */
	public static Map<String, String> getParamComments(JavaMethod method) {

		DocletTag[] tags = method.getTags();

		JavaParameter[] params = method.getParameters();

		Map<String, String> results = new HashMap<String, String>();

		for (DocletTag tag : tags) {

			if ("param".equals(tag.getName())) {

				String target = tag.getValue().trim();

				if (!isNullOrEmpty(target)) {

					for (JavaParameter p : params) {

						if (target.startsWith(p.getName())) {

							String comment = target.substring(p.getName().length(), target.length()).trim();

							results.put(p.getName(), comment);

						}
					}
				}
			}
		}
		return results;

	}

}
