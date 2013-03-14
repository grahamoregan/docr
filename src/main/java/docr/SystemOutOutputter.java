package docr;

import static docr.DocletTags.getTitle;
import static docr.Methods.getHttpMethod;
import static docr.Parameters.isPathVarible;
import static java.lang.String.format;

import java.util.List;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

public class SystemOutOutputter implements Outputter {

	@Override
	public void output(List<JavaClass> classes) {

		for (JavaClass cls : classes) {

			Annotation ant = Classes.getRequestMapping(cls);

			String base = Annotations.getAnnotationValue(ant);

			System.out.println("base : " + base);

			String title = getTitle(cls.getTags(), cls.getName());
			System.out.println("title : " + title);

			String description = cls.getComment();
			System.out.println("description : " + description);

			for (JavaMethod m : cls.getMethods()) {

				if (Methods.isWebMethod(m)) {

					System.out.println("\ttitle : " + getTitle(m.getTags(), m.getName()));
					System.out.println("\tmethod : " + getHttpMethod(m));
					System.out.println("\tmapping : " + Annotations.getAnnotationValue(Methods.getRequestMapping(m)));

					System.out.println(m.getCodeBlock());

					for (JavaParameter param : m.getParameters()) {

						System.out.println(format("\t\t %s", param.getName()));

						System.out.println(format("\t\t\t path variable : %s", isPathVarible(param)));

					}

				}

			}

		}

	}

}
