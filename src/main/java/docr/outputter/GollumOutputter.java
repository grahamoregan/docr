package docr.outputter;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

import com.google.common.base.Strings;

import docr.Method;
import docr.Parameter;
import docr.Resource;

public class GollumOutputter implements Outputter {

	private Writer out;

	private String outDir;

	private String projectName;

	public static final GollumOutputter create(Writer out) {

		GollumOutputter co = new GollumOutputter();

		co.out = out;

		return co;

	}

	public static final GollumOutputter create(String projectName, String outDir) {

		GollumOutputter co = new GollumOutputter();
		co.projectName = projectName;
		co.outDir = outDir;
		return co;
	}

	@Override
	public void output(List<Resource> resources) {

		try {

			if (!isNullOrEmpty(outDir)) {

				File f = new File(outDir, projectName + ".md");

				FileWriter writer = new FileWriter(f);

				System.out.println("Writing " + projectName + " to " + f.getAbsolutePath());

				for (Resource r : resources) {
					writer.write(format("* [[%s|%s#%s]]%n", r.getTitle(), projectName, r.getTitle().toLowerCase()));
					for (Method method : r.getMethods()) {
						writer.write(format("    * [[%s|%s#%s]]%n", method.getTitle(), projectName, method.getTitle().toLowerCase()));
					}
				}

				writer.write(String.format("%n"));

				for (Resource r : resources) {

					writer.write(format("# %s%n", r.getTitle()));
					writer.write(String.format("%n"));
					writer.write(format("%s%n", Strings.nullToEmpty(r.getComment())));
					writer.write(String.format("%n"));

					for (Method method : r.getMethods()) {

						writer.write(format("## %s%n", method.getTitle()));
						writer.write(format("%s%n", Strings.nullToEmpty(method.getComment())));
						writer.write(String.format("%n"));
						writer.write(format("### %s%n", "Definition"));
						writer.write(format("> %s %s%s %n", method.getMethod(), r.getPath(), method.getPath()));
						writer.write(String.format("%n"));

						if (method.getParameters().size() > 0) {

							writer.write(String.format("### Parameters %n"));
							writer.write(String.format("Name | Required | Type | Default | Description%n"));
							writer.write(String.format("---|---|---|---|---%n"));

							for (Parameter parameter : method.getParameters()) {

								String name = parameter.getName();

								String required = parameter.getRequired().toString();
								String type = parameter.getType();
								String defaultValue = Strings.nullToEmpty(parameter.getDefaultValue());
								String description = Strings.nullToEmpty(parameter.getComment());

								writer.write(String.format("%s | %s | %s | %s | %s%n", name, required, type, defaultValue, description));

							}

							writer.write(String.format("%n"));

						}

						if (!isNullOrEmpty(method.getExampleRequest())) {
							writer.write(String.format("### Example Request%n"));
							writer.write(String.format("~~~%n"));
							writer.write(String.format("%s%n", Strings.nullToEmpty(method.getExampleRequest())));
							writer.write(String.format("~~~%n"));
							writer.write(String.format("%n"));
						}

						if (!isNullOrEmpty(method.getExampleResponse())) {
							writer.write(String.format("### Example Response%n"));
							writer.write(String.format("~~~%n"));
							writer.write(String.format("%s%n", Strings.nullToEmpty(method.getExampleResponse())));
							writer.write(String.format("~~~%n"));
							writer.write(String.format("%n"));
						}

					}

				}
				writer.flush();
				writer.close();
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
