package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

	public static final String BACKEND = "BACKEND";

	private static final String template = "Backend configured is %s";

	@RequestMapping("/config")
	public String config() {

		String backend = System.getenv(BACKEND);

		return String.format(template, backend);
	}
}