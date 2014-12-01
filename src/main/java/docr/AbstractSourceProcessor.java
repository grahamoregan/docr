package docr;

import java.io.File;
import java.io.Reader;
import java.util.List;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import docr.outputter.Outputter;

public abstract class AbstractSourceProcessor {

	public abstract List<JavaClass> load(JavaSource[] sources);

	public abstract List<Resource> process(List<JavaClass> classes, Outputter outputter);

	public List<Resource> run(String path, Outputter outputter) {

		File directory = new File(path);

		JavaDocBuilder builder = new JavaDocBuilder();
		builder.addSourceTree(directory);

		return run(builder, outputter);

	}

	/**
	 * For testing one file at a time.
	 * @param reader
	 * @param outputter
	 */
	public List<Resource> run(Reader reader, Outputter outputter) {
		JavaDocBuilder builder = new JavaDocBuilder();
		builder.addSource(reader);
		return run(builder, outputter);
	}

	public List<Resource> run(JavaDocBuilder builder, Outputter outputter) {
		List<JavaClass> classes = load(builder.getSources());
		List<Resource> resources = process(classes, outputter);

		outputter.output(resources);

		return resources;
	}

}
