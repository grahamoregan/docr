package docr;

import java.util.List;

import com.thoughtworks.qdox.model.JavaClass;

public interface Outputter {

	/**
	 * @param classes
	 *            the list of classes to include in output
	 */
	public void output(List<JavaClass> classes);

}
