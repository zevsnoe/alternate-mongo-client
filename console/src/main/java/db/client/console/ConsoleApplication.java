package db.client.console;

import db.client.contract.client.Client;
import db.client.contract.client.ClientFactoryInterface;
import db.client.contract.client.Interactor;
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
		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(ConsoleApplication.class).web(NONE).run(args);
		Interactor interactor = ctx.getBean(Interactor.class);
		ClientFactoryInterface clientFactory = ctx.getBean(ClientFactoryInterface.class);
		Client client = clientFactory.getClient();

		Scanner scanner = new Scanner(System.in);

		System.out.println("Input query bellow: ");
		while(scanner.hasNext()) {
			String string = scanner.nextLine();
			Object o = interactor.interactWith(client, string);
			System.out.println("");
			System.out.println(o);
		}

		System.exit(0);
	}

}
