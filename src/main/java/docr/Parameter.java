package docr;

import com.google.common.base.Objects;

public class Parameter {

	private String name;

	private String defaultValue;

	private Boolean required;

	private String comment;

	private String type;

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("name", name).add("defaultValue", defaultValue).add("required", required).add("comment", comment).toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
