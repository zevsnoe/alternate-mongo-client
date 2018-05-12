package db.client.app.clients.factory;

import db.client.app.contract.Client;
import db.client.app.contract.ClientFactoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientFactory implements ClientFactoryInterface {

	private final Client mongoDBClient;

	@Autowired
	public ClientFactory(Client mongoDBClient) {
		this.mongoDBClient = mongoDBClient;
	}

	@Override
	public Client getClient() {
		return mongoDBClient;
	}
}
