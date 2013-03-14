package docr;

import com.thoughtworks.qdox.model.DocletTag;

public class DocletTags {

	public static String getTitle(DocletTag[] tags, String alternative) {

		String t = getDocletTagValue(tags, "title");

		return (t != null) ? t : alternative;

	}

	/**
	 * 
	 * TODO Remove : not sure how useful this actually is?
	 * 
	 * Gets the specified tag from the Javadoc, e.g. getMethodTitle(thisMethod,
	 * "return") would return "the value"
	 * 
	 * @param m
	 * @param name
	 * @return the value
	 */
	public static String getDocletTagValue(DocletTag[] tags, String name) {

		for (DocletTag tag : tags)
			if (name.equals(tag.getName()))
				return tag.getValue();

		return null;

	}

}
