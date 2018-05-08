package db.client.adapter;

import db.client.adapter.validator.QueryValidationError;
import db.client.app.contract.QueryAdapter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class MongoQueryAdapter implements QueryAdapter {

	public String adopt(String query) {
		throw new QueryValidationError();
	}

}
