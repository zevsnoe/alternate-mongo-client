package db.client.app.interactor;

import db.client.app.interactor.validation.InteractionFailedError;
import db.client.contract.client.Client;
import db.client.contract.client.Interactor;
import org.springframework.stereotype.Service;

@Service
public class ClientInteractor implements Interactor {

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
