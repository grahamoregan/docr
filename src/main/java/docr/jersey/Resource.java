package docr.jersey;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class Resource {

	private List<Method> methods = Lists.newArrayList();

	private String path;

	private String comment;

	private String title;

	public void addMethod(Method method) {
		this.methods.add(method);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("path", path).add("title", title).add("methods", methods).add("comment", comment).toString();
	}

	public List<Method> getMethods() {
		return methods;
	}

	public void setMethods(List<Method> methods) {
		this.methods = methods;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
