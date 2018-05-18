package db.client.mongo;

import db.client.contract.client.Client;
import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.QueryAdapter;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.Gateway;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MongoDBClient implements Client {

	private final QueryConverter converter;
	private final QueryAdapter adapter;
	private final Gateway gateway;

	@Autowired
	public MongoDBClient(QueryConverter converter, QueryAdapter adapter, Gateway gateway) {
		this.converter = converter;
		this.adapter = adapter;
		this.gateway = gateway;
	}

	//TODO: cover exceptional cases
	//TODO: return some dto object
	@Override
	public Object execute(String query) {
		try{
			QueryConvertedStatement convertedStatement = converter.convert(query);
			AdoptedStatement adoptedStatement = adapter.adopt(convertedStatement);
			return gateway.execute(adoptedStatement);
		} catch (UnsupportedOperationException e) {
			return QueryExecutionResult.from(e);
		}
	}

}
