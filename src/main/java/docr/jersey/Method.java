package docr.jersey;

import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Method {

	private String path;

	// HTTP method
	private String method;

	private String comment;

	private Boolean auth;

	private Map<String, String> headers = Maps.newHashMap();

	private List<Parameter> parameters = Lists.newArrayList();

	public void addParameter(Parameter variable) {
		this.parameters.add(variable);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", path).add("method", method).add("comment", comment).add("auth", auth).toString();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getAuth() {
		return auth;
	}

	public void setAuth(Boolean auth) {
		this.auth = auth;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
