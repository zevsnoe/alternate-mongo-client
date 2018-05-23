package db.client.mongo;

import db.client.contract.client.Client;
import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.QueryAdapter;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.RepositoryService;
import db.client.mongo.gateway.result.QueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DBClient implements Client {

	private final QueryConverter converter;
	private final QueryAdapter adapter;
	private final RepositoryService repositoryService;

	@Autowired
	public DBClient(QueryConverter converter, QueryAdapter adapter, RepositoryService repositoryService) {
		this.converter = converter;
		this.adapter = adapter;
		this.repositoryService = repositoryService;
	}

	@Override
	public Object execute(String query) {
		try{
			QueryConvertedStatement convertedStatement = converter.convert(query);
			AdoptedStatement adoptedStatement = adapter.adopt(convertedStatement);
			return repositoryService.execute(adoptedStatement);
		} catch (InvalidSQLException e) {
			return QueryExecutionResult.from(e);
		} catch (UnsupportedOperationException e) {
			return QueryExecutionResult.from(e);
		}
	}

}
