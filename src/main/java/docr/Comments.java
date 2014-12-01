package docr;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Comments {

	/**
	 * Extracts the node with the matching id and pretty prints the JSON. This will
	 * Throw an RTE if the content isn't valid.
	 */
	public static String extractJson(String input, String id) {

		String content = extract(input, id);

		try {

			if (!isNullOrEmpty(content)) {

				ObjectMapper mapper = new ObjectMapper();
				JsonNode myObject = mapper.readTree(content);
				ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
				String result = writer.writeValueAsString(myObject);

				return result;

			}

			return "";

		} catch (JsonParseException e) {
			return content;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static String extract(String input, String id) {

		Document doc = Jsoup.parseBodyFragment(input);

		if (doc.getElementById(id) != null) {

			try {

				return doc.getElementById(id).text();

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		return null;

	}

	public static void main(String[] args) throws IOException {

		FileReader reader = new FileReader("/tmp/test.html");

		BufferedReader buffered = new BufferedReader(reader);

		StringBuilder builder = new StringBuilder();

		for (String temp = null; ((temp = buffered.readLine()) != null);)
			builder.append(temp).append("\n");

		String result = builder.toString();

		buffered.close();

		Document doc = Jsoup.parseBodyFragment(result);

		System.out.println(doc);

		System.out.println(doc.getElementById("Example Request").text());
	}

}
