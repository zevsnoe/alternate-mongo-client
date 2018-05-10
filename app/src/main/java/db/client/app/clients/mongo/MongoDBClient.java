package db.client.app.clients.mongo;

import db.client.app.contract.Client;
import org.springframework.stereotype.Component;

@Component
public class MongoDBClient implements Client {

	private String address;
	private String port;

}
