package db.client.adapter;

import db.client.adapter.validator.QueryValidationError;
import db.client.app.contract.QueryAdapter;
import org.springframework.stereotype.Component;

@Component
public class MongoQueryAdapter implements QueryAdapter {

	public String adopt(String query) {
		throw new QueryValidationError();
	}

}
