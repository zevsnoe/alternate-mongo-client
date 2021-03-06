package db.client.mongo.gateway.repo;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.SelectGateway;
import db.client.mongo.gateway.result.QueryExecutionResultBuilderFacade;
import db.client.mongo.validator.InvalidStatementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleSelectGateway implements SelectGateway {

	private final DBAwared client;

	@Autowired
	public SimpleSelectGateway(DBAwared client) {
		this.client = client;
	}

	@Override
	public QueryExecutionResult select(AdoptedStatement statement) {
		if (!(statement instanceof SelectAdoptedStatement)) {
			throw new InvalidStatementException("Statement " + statement.getClass().getSimpleName() +  "is of wrong type");
		}
		SelectAdoptedStatement selectStatement = (SelectAdoptedStatement) statement;

		try {
			MongoCollection collection = client.getCollection(statement.getCollectionName());
			return QueryExecutionResultBuilderFacade.selectSuccessfull(collection.find(selectStatement.getFilter())
					.projection(selectStatement.getProjections())
					.iterator());
		} catch (MongoClientException e) {
			return QueryExecutionResultBuilderFacade.selectFailed(e);
		} catch (Exception e) {
			return QueryExecutionResultBuilderFacade.selectFailed(e);
		}
	}

}
