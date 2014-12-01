package docr.outputter;

import java.util.List;

import docr.Resource;

public class SystemOutOutputter implements Outputter {

	public static final SystemOutOutputter create() {

		SystemOutOutputter outputter = new SystemOutOutputter();

		return outputter;

	}

	@Override
	public void output(List<Resource> resources) {

		/*
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
		*/

	}

}
