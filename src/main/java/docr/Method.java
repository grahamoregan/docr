package docr;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

public class Method {

	private String path;

	// HTTP method
	private String method;

	private String comment;

	private Boolean auth;

	private String name;

	private String exampleRequest;

	private String exampleResponse;

	private List<Parameter> pathParameters = Lists.newArrayList();

	private List<Parameter> headers = Lists.newArrayList();

	private List<Parameter> cookies = Lists.newArrayList();

	private List<Parameter> parameters = Lists.newArrayList();

	public void addParameter(Parameter variable) {
		this.parameters.add(variable);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", path).add("method", method).add("comment", comment).add("auth", auth).toString();
	}

	public void addCookie(Parameter variable) {
		this.cookies.add(variable);
	}

	public void addHeader(Parameter variable) {
		this.headers.add(variable);
	}

	public void addPathParam(Parameter variable) {
		this.pathParameters.add(variable);
	}

	public String getTitle() {
		// TODO filter
		return name;
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

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getHeaders() {
		return headers;
	}

	public void setHeaders(List<Parameter> headers) {
		this.headers = headers;
	}

	public List<Parameter> getCookies() {
		return cookies;
	}

	public void setCookies(List<Parameter> cookies) {
		this.cookies = cookies;
	}

	public String getExampleRequest() {
		return exampleRequest;
	}

	public void setExampleRequest(String exampleRequest) {
		this.exampleRequest = exampleRequest;
	}

	public List<Parameter> getPathParameters() {
		return pathParameters;
	}

	public void setPathParameters(List<Parameter> pathParameters) {
		this.pathParameters = pathParameters;
	}

	public String getExampleResponse() {
		return exampleResponse;
	}

	public void setExampleResponse(String exampleResponse) {
		this.exampleResponse = exampleResponse;
	}

}
