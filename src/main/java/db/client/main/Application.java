package db.client.main;

import db.client.api.RestApplication;
import db.client.console.ConsoleApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		if (args.length>0 && args[0].equals("console")) {
			ConsoleApplication.main(args);
		} else {
			SpringApplication.run(RestApplication.class, args);
		}
	}
}
