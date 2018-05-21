package db.client.app.interactor;

import db.client.contract.client.Client;
import db.client.contract.client.Interactor;
import org.springframework.stereotype.Service;

@Service
public class ClientInteractor implements Interactor {

	@Override
	public Object interactWith(Client client, String query) {
		return client.execute(query);
	}
}
