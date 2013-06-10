package docr;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.thoughtworks.qdox.model.Annotation;

public class Annotations {

	/**
	 * QDox takes the values straight from the source so they will have quotes
	 * if they are strings
	 * 
	 * @param annotation
	 * @return
	 */
	public static String getAnnotationValue(Annotation annotation) {

		String temp = getAnnotationParameterValue(annotation, "value");

		return (!isEmpty(temp)) ? stripQuotes(temp) : "";

	}

	public static String stripQuotes(String temp) {
		return (temp.startsWith("\"") && temp.endsWith("\"")) ? temp.substring(
				1, temp.length() - 1) : temp;
	}

	public static boolean isAnnotationTargetRequired(Annotation annotation) {

		String result = getAnnotationParameterValue(annotation, "required");

		if (isEmpty(result))
			return true;

		return ("true".equals(result.toString())) ? true : false;

	}

	/**
	 * Queries the Annotation's parameters to see if it has a matching parameter
	 * for 'name'. If it does then it returns the value.
	 * 
	 * @param annotation
	 * @param name
	 * @return
	 */
	public static String getAnnotationParameterValue(Annotation annotation,
			String name) {

		if (annotation != null) {

			// little hack avoids multiple null checks below
			String value = annotation.getNamedParameter(name) + "";

			if (!isEmpty(value)) {

				value = value.trim();

				value = value.substring(0, value.length());

			}

			return stripQuotes(value);

		}

		return null;

	}

	/**
	 * QDox seems to return "null" as a string when values are empty so this
	 * checks that the value is not null, is not an empty string and is not
	 * equal to "null"
	 */
	public static boolean isEmpty(String input) {
		return isNullOrEmpty(input) || "null".equals(input);
	}

	public static Annotation getAnnotation(Annotation[] annotations, String name) {

		for (Annotation ant : annotations) {

			if (ant.getType().getJavaClass().getFullyQualifiedName()
					.equalsIgnoreCase(name))
				return ant;

		}
		return null;

	}

}
