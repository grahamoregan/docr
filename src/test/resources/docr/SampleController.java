package docr;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import docr.annotation.UserlessLogin;

/**
 * Sample message
 */
@Controller
@RequestMapping("/test")
public class SampleController {

	/**
	 * default resource for controller
	 * 
	 * @param id
	 * @param nothing
	 * @param ip
	 *            the user's ip
	 * @param test
	 * @return
	 */
	@UserlessLogin("false")
	@RequestMapping(value = "/something", method = RequestMethod.GET)
	public String index(final @RequestParam(required = true, defaultValue = "12") String id, @CookieValue(required = true) String cookies, @RequestHeader String ip, @RequestParam String test) {
		return null;
	}

	/**
	 * Second resource - testing only
	 * 
	 * @return
	 */
	@RequestMapping("/second")
	public Double second() {
		return null;
	}

}
