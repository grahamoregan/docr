package docr.outputter;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.Writer;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import docr.Resource;

public class ConfluenceOutputter implements Outputter {

	private Writer out;

	public static final ConfluenceOutputter create(Writer out) {

		ConfluenceOutputter co = new ConfluenceOutputter();

		co.out = out;

		return co;

	}

	public void output(List<Resource> resources) {
		/*
		try {
			out.write("{toc}\n\n");

			for (JavaClass cls : classes) {

				out.write(format("h3. %s \n", toTitle(cls)));

				out.write(format("\n %s \n\n", escape(cls.getComment())));

				out.write("|| Resource || URI Structure || Method || Path Variables || Request Parameters || Cookie Values || Request Headers || \n");

				for (JavaMethod m : cls.getMethods()) {

					if (isWebMethod(m)) {

						final Map<String, String> paramComments = getParamComments(m);

						Function<JavaParameter, String> f = new Function<JavaParameter, String>() {

							@Override
							public String apply(JavaParameter input) {
								return format("*%s* %s %s %s \\\\ ", input.getName(), ((isRequired(input)) ? "* " : ""), ((!isNullOrEmpty(getDefaulValue(input))) ? format("[%s]", getDefaulValue(input)) : ""), (paramComments.get(input.getName()) != null) ? paramComments.get(input.getName()) : "");
							}

						};

						// TODO can these be done with Joiners?
						StringBuilder requestParams = new StringBuilder();

						for (JavaParameter p : getRequestParams(m)) {
							requestParams.append(f.apply(p));
						}

						StringBuilder cookieValues = new StringBuilder();

						for (JavaParameter p : getCookieValues(m)) {
							cookieValues.append(f.apply(p));
						}

						StringBuilder requestHeaders = new StringBuilder();

						for (JavaParameter p : getRequestHeaders(m)) {
							requestHeaders.append(f.apply(p));
						}

						StringBuilder pathVariables = new StringBuilder();

						for (JavaParameter p : getPathVariables(m)) {
							pathVariables.append(f.apply(p));
						}

						String comment = escape(getComment(m));

						out.write(format("| %s | %s | %s | %s | %s | %s | %s | \n", comment, escape(getRequestMappingAsString(m)), getHttpMethod(m), escape(pathVariables.toString()), escape(requestParams.toString()), escape(cookieValues.toString()), escape(requestHeaders.toString())));

					}

				}

				out.write("\n\n");

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
		String nbsp = " \\\\ ";

		if (isNullOrEmpty(input))
			return nbsp;

		else {

			input = input.replace("{", "\\{");

			input = input.replace("}", "\\}");

			input = input.replace("\n", nbsp);

			return input;

		}

	}

}
