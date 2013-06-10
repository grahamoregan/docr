package docr.jersey;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import docr.Outputter;

/**
 * Document Jersey Resources annotated with @Path
 */
public class Jersey {

	public static void main(String[] args) throws FileNotFoundException {

		String dir = System.getProperty("dir", "/Users/graham/Development/source/JeemTV/registration/");
		String out = System.getProperty("out", "/tmp/");

		System.out.println("Reading from " + dir);

		if (!isNullOrEmpty(out))
			System.out.println("Outputting the content to " + out);

		Jersey.create().document(new File(dir), out);
		// Jersey.create().document(new File(dir), null);

	}

	public static final Jersey create() {

		Jersey jersey = new Jersey();

		return jersey;

	}

	public void document(File baseSourceDirectory, String outDir) {

		List<JavaClass> resources = getResources(baseSourceDirectory);

		StringWriter writer = new StringWriter();

		Outputter out = GollumOutputter.create(outDir);

		out.output(resources);

		System.out.println(writer.toString());

	}

	public List<JavaClass> getResources(File directory) {

		JavaDocBuilder builder = new JavaDocBuilder();

		builder.addSourceTree(directory);

		List<JavaClass> resources = new ArrayList<JavaClass>();

		for (JavaSource src : builder.getSources())
			for (JavaClass cls : src.getClasses())
				if (Resources.isResource(cls))
					resources.add(cls);

		return resources;

	}

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

}
