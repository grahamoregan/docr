package docr.spring;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaParameter;

import docr.Annotations;

public class Parameters {

	public static final boolean isPathVarible(JavaParameter param) {

		Annotation ann = Annotations.getAnnotation(param.getAnnotations(), org.springframework.web.bind.annotation.PathVariable.class.getName());

		return (ann != null) ? true : false;
	}

	public static final boolean isReqestParam(JavaParameter param) {

		Annotation ann = Annotations.getAnnotation(param.getAnnotations(), org.springframework.web.bind.annotation.RequestParam.class.getName());

		return (ann != null) ? true : false;

	}

	public static final boolean isRequired(JavaParameter param) {

		for (Annotation a : param.getAnnotations())
			if (Annotations.isAnnotationTargetRequired(a))
				return true;

		return false;

	}

	public static String getDefaulValue(JavaParameter p) {

		for (Annotation a : p.getAnnotations()) {

			String result = Annotations.getAnnotationParameterValue(a, "defaultValue");

			if (!isNullOrEmpty(result) && !"null".equals(result))
				return result;
		}

		return "";

	}
}
