package hello;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackendController {

	private static final String template = "Frontend called backend with the following result: '%s'";

	@RequestMapping("/backend")
	public String backend() {

		try {

			String backend = System.getenv(ConfigController.BACKEND);

			URL url = new URL(backend);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int status = con.getResponseCode();

			if (status != 200) {
				throw new Exception("Received unexpected status " + status);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			in.close();

			con.disconnect();

			return String.format(template, content.toString());

		} catch (Exception ex) {

			return "Error while calling backend" + ex.getLocalizedMessage();

		}
	}

}
