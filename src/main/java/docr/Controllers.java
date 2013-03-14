package docr;

import com.thoughtworks.qdox.model.JavaClass;

public class Controllers {

	/**
	 * Takes a {@link JavaClass} with a name like TestController and returns
	 * "Test"
	 * 
	 * @param cls
	 * @return
	 */
	public static String toTitle(JavaClass cls) {

		if (cls != null) {

			String name = cls.getName();

			if (name.endsWith("Controller"))
				return name.substring(0, name.length() - "Controller".length());

			return cls.getName();
		}

		return null;

	}

}
