package docr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

public class FieldValueTest extends TestCase {

	private Method name = new Method();

	public void testFieldValue() throws Exception {

		JavaDocBuilder builder = new JavaDocBuilder();
		builder.addSourceTree(new File("/Users/graham/Development/source/EllisonBrookes/docr/src/test/java/"));

		JavaSource[] sources = builder.getSources();

		List<JavaClass> classes = new ArrayList<JavaClass>();

		for (JavaSource src : sources)
			for (JavaClass cls : src.getClasses())
				classes.add(cls);

		for (JavaClass cls : classes) {

			System.out.println(cls.getName());

			if (cls.getName().equals("FieldValueTest")) {

				System.out.println("expr: "+ cls.getFieldByName("name").getInitializationExpression());
			}
		}

	}

}
