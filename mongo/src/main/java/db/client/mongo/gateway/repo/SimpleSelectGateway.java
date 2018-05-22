package db.client.mongo.gateway.repo;

import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.SelectGateway;
import db.client.mongo.gateway.result.QueryExecutionResult;
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
	public Object select(AdoptedStatement statement) {
		SelectAdoptedStatement selectStatement = (SelectAdoptedStatement) statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		try {
			return QueryExecutionResult.from(collection.find(selectStatement.getFilter())
					.projection(selectStatement.getProjections())
					.iterator());
		} catch (Exception e) {
			return QueryExecutionResult.internalError(e);
		}
	}
}
