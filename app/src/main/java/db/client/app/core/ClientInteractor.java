package db.client.app.core;

import db.client.app.contract.Client;
import db.client.app.contract.Interactor;
import db.client.app.contract.QueryAdapter;
import db.client.app.interactor.validation.InteractionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ClientInteractor implements Interactor {

	private final QueryAdapter adapter;

	@Autowired
	public ClientInteractor(QueryAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void interactWith(Client client, String query) {
		throw new InteractionFailedError();
	}
}
