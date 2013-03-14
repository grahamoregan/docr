package docr;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Logger;

public class Main {

	private static final Logger logger = Logger.getLogger("Docr");

	public static void main(String[] args) throws FileNotFoundException {

		String dir = System.getProperty("dir");
		String out = System.getProperty("out");

		logger.info("Reading Spring controllers from " + dir);

		if (!isNullOrEmpty(out))
			System.out.println("Outputting the content to " + out);

		Docr.create().document(new File(dir), out);

	}

}
