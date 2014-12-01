package docr;

import static docr.Classes.isController;

import java.util.List;

import com.google.common.collect.Lists;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;

import docr.outputter.Outputter;

public class SpringSourceProcessor extends AbstractSourceProcessor {

	@Override
	public List<JavaClass> load(JavaSource[] sources) {

		List<JavaClass> controllers = Lists.newArrayList();

		for (JavaSource src : sources)
			for (JavaClass cls : src.getClasses())
				if (isController(cls))
					controllers.add(cls);

		return controllers;

	}

	@Override
	public List<Resource> process(List<JavaClass> classes, Outputter outputter) {
		throw new UnsupportedOperationException("Not implemented");
	}

}
