package docr;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.FileNotFoundException;
import java.util.logging.Logger;

import docr.jersey.JerseySourceProcessor;
import docr.outputter.GollumOutputter;

public class Main {

	private static final Logger logger = Logger.getLogger("Docr");

	public static void main(String[] args) throws FileNotFoundException {

		String dir = System.getProperty("dir", "/Users/graham/Development/source/JeemTV/registration/registration-service/");
		String out = System.getProperty("out", "/tmp/");

		logger.info("Reading Spring controllers from " + dir);

		if (!isNullOrEmpty(out))
			System.out.println("Outputting the content to " + out);

		new JerseySourceProcessor().run("/Users/graham/Development/source/JeemTV/registration/registration-service/", GollumOutputter.create("Registration-API", out));
		new JerseySourceProcessor().run("/Users/graham/Development/source/JeemTV/moderation/moderation-service/", GollumOutputter.create("Moderation-API", out));

	}

}
