package db.client.mongo;

import db.client.contract.client.Client;
import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.QueryAdapter;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.Gateway;
import db.client.mongo.gateway.result.QueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBClient implements Client {

	private final QueryConverter converter;
	private final QueryAdapter adapter;
	private final Gateway gateway;

	@Autowired
	public DBClient(QueryConverter converter, QueryAdapter adapter, Gateway gateway) {
		this.converter = converter;
		this.adapter = adapter;
		this.gateway = gateway;
	}

	@Override
	public Object execute(String query) {
		try{
			QueryConvertedStatement convertedStatement = converter.convert(query);
			AdoptedStatement adoptedStatement = adapter.adopt(convertedStatement);
			return gateway.execute(adoptedStatement);
		} catch (InvalidSQLException e) {
			return QueryExecutionResult.from(e);
		} catch (UnsupportedOperationException e) {
			return QueryExecutionResult.from(e);
		}
	}

}
