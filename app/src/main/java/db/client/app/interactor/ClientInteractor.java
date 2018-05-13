package db.client.app.interactor;

import db.client.app.interactor.validation.InteractionFailedError;
import db.client.contract.Client;
import db.client.contract.Interactor;
import db.client.contract.QueryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientInteractor implements Interactor {

	private final QueryAdapter adapter;

	@Autowired
	public ClientInteractor(QueryAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public Object interactWith(Client client, String query) {
		try {
			return client.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InteractionFailedError();
		}
	}
}
