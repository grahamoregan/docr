package docr;

import static com.google.common.base.Strings.isNullOrEmpty;
import static docr.Classes.isController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

/**
 * Document Jersey Resources annotated with @Path
 */
public class Jersey {

	public static final Jersey create() {

		Jersey jersey = new Jersey();

		return jersey;

	}

	public void document(File baseSourceDirectory, String outDir) {

		List<JavaClass> controllers = getResources(baseSourceDirectory);

		if (!isNullOrEmpty(outDir)) {

			try {

				Outputter out = HtmlOutputter.create(new FileWriter(outDir));

				out.output(controllers);

			} catch (IOException e) {

				throw new RuntimeException(e);
			}

		} else {

			StringWriter writer = new StringWriter();

			Outputter out = SystemOutOutputter.create();

			out.output(controllers);

			System.out.println(writer.toString());

		}

	}

	public List<JavaClass> getResources(File directory) {

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSourceTree(directory);

		List<JavaClass> controllers = new ArrayList<JavaClass>();

		for (JavaSource src : builder.getSources())
			for (JavaClass cls : src.getClasses())
				if (isController(cls))
					controllers.add(cls);

		return controllers;

	}

	public List<JavaClass> getController(Reader reader) {

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSource(reader);

		List<JavaClass> controllers = new ArrayList<JavaClass>();

		for (JavaSource src : builder.getSources())
			for (JavaClass cls : src.getClasses())
				if (isController(cls))
					controllers.add(cls);

		return controllers;

	}

}