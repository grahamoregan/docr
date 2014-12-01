package docr;

import static docr.Annotations.getAnnotationValue;
import static docr.spring.Methods.getCookieValues;
import static docr.spring.Methods.getRequestHeaders;
import static docr.spring.Methods.getRequestMapping;
import static docr.spring.Methods.getRequestParams;
import static docr.spring.Parameters.isRequired;

import java.io.InputStreamReader;
import java.util.List;

import junit.framework.TestCase;

import com.thoughtworks.qdox.model.Annotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;

import docr.annotation.UserlessLogin;
import docr.outputter.SystemOutOutputter;
import docr.spring.Methods;
import docr.spring.Parameters;

public class SampleControllerTest extends TestCase {

	private List<JavaClass> controllers;

	public void setUp() {

		SpringSourceProcessor docr = new SpringSourceProcessor();

		// used .code as extension to work around Eclipse wanting to ignore the file
		docr.run(new InputStreamReader(this.getClass().getResourceAsStream("SampleController.code")), new SystemOutOutputter());

	}

	public void testRequestMapping() throws Exception {

		JavaClass sample = controllers.get(0);

		JavaMethod method = sample.getMethods()[0];

		Annotation a = getRequestMapping(method);

		// they come out with the direct encoding
		assertEquals("/something", getAnnotationValue(a));

		// they come out with the direct encoding
		assertEquals("/second", getAnnotationValue(getRequestMapping(sample.getMethods()[1])));
	}

	public void testFullRequestMapping() throws Exception {

		JavaClass sample = controllers.get(0);

		JavaMethod method = sample.getMethods()[0];

		// they come out with the direct encoding
		assertEquals("/test/something", Methods.getRequestMappingAsString(method));

	}

	public void testCookieValues() throws Exception {

		JavaClass sample = controllers.get(0);

		List<JavaParameter> cookieValues = getCookieValues(sample.getMethods()[0]);

		assertEquals(1, cookieValues.size());

		assertTrue(isRequired(cookieValues.get(0)));

	}

	public void testRequestParams() throws Exception {

		JavaClass sample = controllers.get(0);

		List<JavaParameter> values = getRequestParams(sample.getMethods()[0]);

		assertEquals(2, values.size());

		assertTrue(isRequired(values.get(0)));

		// if the required doesn't exist then it is also true
		assertTrue(isRequired(values.get(1)));

	}

	public void testRequestHeaders() throws Exception {

		JavaClass sample = controllers.get(0);

		List<JavaParameter> values = getRequestHeaders(sample.getMethods()[0]);

		assertEquals(1, values.size());

		assertTrue(isRequired(values.get(0)));

	}

	public void testDefaultValues() throws Exception {

		JavaClass sample = controllers.get(0);

		JavaParameter p = sample.getMethods()[0].getParameters()[0];

		assertEquals("12", Parameters.getDefaulValue(p));

	}

	public void testUserLessLogin() throws Exception {

		JavaClass sample = controllers.get(0);

		Annotation[] anns = sample.getMethods()[0].getAnnotations();

		assertNotNull(anns);

		Annotation vale = Annotations.getAnnotation(anns, UserlessLogin.class.getName());

		assertEquals("false", Annotations.getAnnotationValue(vale));

	}
}
