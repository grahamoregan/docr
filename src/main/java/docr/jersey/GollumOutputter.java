package docr.jersey;

import static com.google.common.base.Strings.isNullOrEmpty;
import static docr.spring.Methods.getComment;
import static docr.spring.Methods.getCookieValues;
import static docr.spring.Methods.getHttpMethod;
import static docr.spring.Methods.getParamComments;
import static docr.spring.Methods.getPathVariables;
import static docr.spring.Methods.getRequestHeaders;
import static docr.spring.Methods.getRequestParams;
import static java.lang.String.format;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Path;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import docr.Annotations;
import docr.Outputter;

public class GollumOutputter implements Outputter {

	private Writer out;

	private String outDir;

	public static final GollumOutputter create(Writer out) {

		GollumOutputter co = new GollumOutputter();

		co.out = out;

		return co;

	}

	public static final GollumOutputter create(String outDir) {

		GollumOutputter co = new GollumOutputter();

		co.outDir = outDir;

		return co;

	}

	@Override
	public void output(List<JavaClass> classes) {

		try {

			List<Resource> resources = Lists.newArrayList();

			for (JavaClass cls : classes) {

				Resource resource = new Resource();

				resource.setTitle(Resources.toTitle(cls));
				resource.setComment(cls.getComment());
				resource.setPath(Annotations.getAnnotationValue(Annotations.getAnnotation(cls.getAnnotations(), Path.class.getName())));

				for (JavaMethod m : cls.getMethods()) {

					if (!m.isConstructor()) {

						if (Resources.isWebMethod(m)) {

							final Map<String, String> paramComments = getParamComments(m);

							Map<String, Object> method = Maps.newHashMap();

							Method meth = new Method();
							meth.setComment(escape(getComment(m)));

							meth.setMethod(getHttpMethod(m));
							meth.setPath(Resources.getPath(m));

							Function<JavaParameter, Map<String, String>> f = new Function<JavaParameter, Map<String, String>>() {

								@Override
								public Map<String, String> apply(JavaParameter input) {

									Map<String, String> result = Maps.newHashMap();

									result.put("name", input.getName());

									// TODO hardcoded
									result.put("required", "true");

									// TODO fix
									result.put("defaultValue", "none");
									result.put("comments", paramComments.get(input.getName()));

									return result;
								}

							};

							for (JavaParameter p : getRequestParams(m)) {

								Map<String, String> map = f.apply(p);

								Parameter parameter = new Parameter();
								parameter.setDefaultValue(map.get("name"));
								parameter.setComment(map.get("comments"));
								parameter.setDefaultValue(map.get("defaultValue"));
								parameter.setRequired(Boolean.valueOf(map.get("required")));

								meth.addParameter(parameter);

							}

							for (JavaParameter p : getCookieValues(m))
								method.put("cookieValues", f.apply(p));

							for (JavaParameter p : getRequestHeaders(m))
								method.put("requestHeaders", f.apply(p));

							for (JavaParameter p : getPathVariables(m))
								method.put("pathVariables", f.apply(p));

							resource.addMethod(meth);

						}

					}

					
				}
				
				resources.add(resource);

				System.out.println(resources);

			}

			if (!isNullOrEmpty(outDir)) {

				for (Resource r : resources) {

					System.err.println("writing " + r.getTitle() + ".md");

					FileWriter writer = new FileWriter(new File(outDir, r.getTitle() + ".md"));

					writer.write(format("# %s%n", r.getTitle()));
					writer.write(String.format("%n"));
					writer.write(format("# %s%n", "Overview"));
					writer.write(format("%s%n", Strings.nullToEmpty(r.getComment())));
					writer.write(String.format("%n"));
					writer.write(format("# %s%n", "Methods"));
					writer.write(String.format("%n"));
					
					for(Method method : r.getMethods()){
						writer.write(format("## %s%n", method.getPath()));
						writer.write(String.format("%n"));
						writer.write(format("### %s%n", "Definition"));
						writer.write(format("> %s %s %n", method.getMethod(), Joiner.on("/").join(method.getParameters())));
						
						
						
					}

					writer.flush();
					writer.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String escape(String input) {

		// a crlf rather than &nbsp;
		String nbsp = " ";

		if (isNullOrEmpty(input))
			return nbsp;

		else {

			input = input.replace("\n", nbsp);

			return input;

		}

	}

}
