package db.client.app.interactor;

import com.mongodb.MongoException;
import db.client.app.adapter.mongo.bean.AdoptedStatement;
import db.client.app.adapter.mongo.validator.InvalidSQLException;
import db.client.app.contract.Client;
import db.client.app.contract.Interactor;
import db.client.app.contract.QueryAdapter;
import db.client.app.interactor.validation.InteractionFailedError;
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
			AdoptedStatement adoptedStatement = adapter.adopt(query);
			return client.execute(adoptedStatement);
		} catch(InvalidSQLException e) {
			throw new InteractionFailedError();
		} catch (MongoException e) {
			throw new InteractionFailedError();
		}
	}
}
