package hello;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private static final String template = "Greetings from Spring Boot! Frontend v%s Running on Host %s";

	@RequestMapping("/")
	public String index() {

		String name;

		try {

			name = InetAddress.getLocalHost().getHostName();

		} catch (UnknownHostException ex) {

			Random rand = new Random();
			name = "unknown-" + rand.nextLong();

		}

		String appVersion = Application.class.getPackage().getImplementationVersion();

		return String.format(template, appVersion, name);
	}

}
