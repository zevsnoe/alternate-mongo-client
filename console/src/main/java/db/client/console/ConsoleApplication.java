package db.client.console;

import db.client.contract.client.Client;
import db.client.contract.client.ClientFactory;
import db.client.contract.client.Interactor;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoGatewayException;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

import static org.springframework.boot.WebApplicationType.*;

@SpringBootApplication
@ComponentScan(basePackages = {"db.client.app"})
public class ConsoleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ConsoleApplication.class)
				.web(NONE)
				.bannerMode(Banner.Mode.OFF)
				.run(args);
		Interactor interactor = ctx.getBean(Interactor.class);
		ClientFactory clientFactory = ctx.getBean(ClientFactory.class);
		Client client = clientFactory.getClient();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Input query bellow: ");
		while(scanner.hasNext()) {
			String string = scanner.nextLine();
			try {
				QueryExecutionResult queryExecutionResult = interactor.interactWith(client, string);
				System.out.println("");
				System.out.println(queryExecutionResult.getResult());
			} catch (MongoGatewayException e) {}

		}

		System.exit(0);
	}
}