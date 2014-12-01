package docr.jersey;

import static docr.spring.Methods.getComment;
import static docr.spring.Methods.getParamComments;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Path;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.JavaSource;

import docr.AbstractSourceProcessor;
import docr.Annotations;
import docr.Comments;
import docr.Method;
import docr.Parameter;
import docr.Resource;
import docr.outputter.Outputter;

/**
 * Document Jersey Resources annotated with @Path
 */
public class JerseySourceProcessor extends AbstractSourceProcessor {

	public List<JavaClass> getResource(Reader reader) {

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(reader);

		List<JavaClass> resources = new ArrayList<JavaClass>();

		for (JavaSource src : builder.getSources())
			for (JavaClass cls : src.getClasses())
				if (Resources.isResource(cls))
					resources.add(cls);

		return resources;

	}

	@Override
	public List<JavaClass> load(JavaSource[] sources) {

		List<JavaClass> resources = new ArrayList<JavaClass>();

		for (JavaSource src : sources)
			for (JavaClass cls : src.getClasses())
				if (Resources.isResource(cls))
					resources.add(cls);

		return resources;
	}

	@Override
	public List<Resource> process(List<JavaClass> classes, Outputter outputter) {

		List<Resource> resources = Lists.newArrayList();

		for (JavaClass cls : classes) {

			Resource resource = new Resource();

			resource.setTitle(Resources.toTitle(cls));
			resource.setComment(cls.getComment());
			resource.setPath(Annotations.getAnnotationValue(Annotations.getAnnotation(cls.getAnnotations(), Path.class.getName())));

			for (JavaMethod javaMethod : cls.getMethods()) {

				if (!javaMethod.isConstructor()) {

					if (Resources.isWebMethod(javaMethod)) {

						final Map<String, String> paramComments = getParamComments(javaMethod);

						Method method = new Method();
						method.setComment(getComment(javaMethod));

						method.setMethod(Resources.getHttpMethod(javaMethod).getType().getJavaClass().getName());
						method.setPath(Resources.getPath(javaMethod));
						method.setName(javaMethod.getName());

						if (javaMethod.getComment() != null) {
							method.setComment(Comments.extract(javaMethod.getComment(), "Description"));
							method.setExampleRequest(Comments.extract(javaMethod.getComment(), "Example Request"));
							method.setExampleResponse(Comments.extractJson(javaMethod.getComment(), "Example Response"));
						}

						Function<JavaParameter, Parameter> f = new Function<JavaParameter, Parameter>() {

							@Override
							public Parameter apply(JavaParameter input) {

								Annotation def = Annotations.getAnnotation(input.getAnnotations(), DefaultValue.class.getName());

								Parameter parameter = new Parameter();
								parameter.setName(Annotations.stripQuotes(Resources.getName(input)));
								parameter.setComment(paramComments.get(input.getName()));
								parameter.setDefaultValue(Annotations.getAnnotationParameterValue(def, "value"));

								parameter.setRequired(!Resources.hasAnyAnnotation(input, Lists.newArrayList(Nullable.class.getName())));
								parameter.setType(input.getType().getJavaClass().getName());

								return parameter;
							}

						};

						for (JavaParameter p : Resources.getRequestParams(javaMethod))
							method.addParameter(f.apply(p));

						for (JavaParameter p : Resources.getCookieParams(javaMethod))
							method.addCookie(f.apply(p));

						for (JavaParameter p : Resources.getHeaderParams(javaMethod))
							method.addHeader(f.apply(p));

						for (JavaParameter p : Resources.getPathParams(javaMethod))
							method.addPathParam(f.apply(p));

						resource.addMethod(method);

					}

				}

			}

			resources.add(resource);

			// System.out.println(resources);

		}

		return resources;

	}
}
