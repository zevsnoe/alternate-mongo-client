package db.client.app.factory;

import db.client.contract.client.Client;
import db.client.contract.client.ClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoClientFactory implements ClientFactory {

	private final Client mongoDBClient;

	@Autowired
	public MongoClientFactory(Client mongoDBClient) {
		this.mongoDBClient = mongoDBClient;
	}

	@Override
	public Client getClient() {
		return mongoDBClient;
	}
}
