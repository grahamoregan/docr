package docr.outputter;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Writer;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import docr.Resource;

public class HtmlOutputter implements Outputter {

	private Writer out;

	public static final HtmlOutputter create(Writer out) {

		HtmlOutputter co = new HtmlOutputter();

		co.out = out;

		return co;

	}
	

	/**
	 * Convert the classes to maps so they can be used in Velocity
	 */
	public void output(List<Resource> resources) {
/*
		try {

			for (JavaClass cls : classes) {

				Map<String, Object> map = Maps.newHashMap();

				map.put("title", toTitle(cls));
				map.put("comment", escape(cls.getComment()));

				List<Map<String, Object>> methods = Lists.newArrayList();

				for (JavaMethod m : cls.getMethods()) {

					if (isWebMethod(m)) {

						final Map<String, String> paramComments = getParamComments(m);

						Map<String, Object> method = Maps.newHashMap();

						method.put("comment", escape(getComment(m)));
						method.put("method", getHttpMethod(m));
						method.put("requestMapping", escape(getRequestMappingAsString(m)));

						Function<JavaParameter, Map<String, String>> f = new Function<JavaParameter, Map<String, String>>() {

							@Override
							public Map<String, String> apply(JavaParameter input) {

								Map<String, String> result = Maps.newHashMap();

								result.put("name", input.getName());
								result.put("required", isRequired(input) + "");
								result.put("defaultValue", getDefaulValue(input));
								result.put("comments", paramComments.get(input.getName()));

								return result;
							}

						};

						for (JavaParameter p : getRequestParams(m))
							method.put("requestParams", f.apply(p));

						for (JavaParameter p : getCookieValues(m))
							method.put("cookieValues", f.apply(p));

						for (JavaParameter p : getRequestHeaders(m))
							method.put("requestHeaders", f.apply(p));

						for (JavaParameter p : getPathVariables(m))
							method.put("pathVariables", f.apply(p));

						methods.add(method);

					}

				}

				map.put("methods", methods);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}

	/**
	 * Replaces null and empty strings with a non-breaking space that Confluence
	 * understands and replaces line breaks with Confluence line breaks too.
	 * 
	 * Confluence uses curly braces to demarcate macros so they need to be
	 * escaped if they are to be included in {@link PathVariable} elements in
	 * the final documentation.
	 */
	public static String escape(String input) {

		// a crlf rather than &nbsp;
		String nbsp = " <br /> ";

		if (isNullOrEmpty(input))
			return nbsp;

		else {

			input = input.replace("\n", nbsp);

			return input;

		}

	}

}
