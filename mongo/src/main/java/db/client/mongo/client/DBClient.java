package db.client.mongo.client;

import db.client.contract.client.Client;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.Adapter;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.RepositoryService;
import db.client.mongo.gateway.result.QueryExecutionResultBuilder;
import db.client.mongo.validator.InvalidSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DBClient implements Client {

	private final QueryConverter converter;
	private final Adapter adapter;
	private final RepositoryService repositoryService;

	@Autowired
	public DBClient(QueryConverter converter,
					@Qualifier Adapter adapter,
					RepositoryService repositoryService) {
		this.converter = converter;
		this.adapter = adapter;
		this.repositoryService = repositoryService;
	}

	@Override
	public QueryExecutionResult execute(String query) {
		try{
			QueryConvertedStatement convertedStatement = converter.convert(query);
			AdoptedStatement adoptedStatement = adapter.adopt(convertedStatement);
			return repositoryService.execute(adoptedStatement);
		} catch (InvalidSQLException e) {
			return QueryExecutionResultBuilder.invalidQuery(e);
		} catch (UnsupportedOperationException e) {
			return QueryExecutionResultBuilder.notSupported(e);
		} catch (Exception e) {
			return QueryExecutionResultBuilder.internalError(e);
		}
	}

}
