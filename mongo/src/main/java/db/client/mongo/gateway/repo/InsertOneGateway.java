package db.client.mongo.gateway.repo;

import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.InsertSingleAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.result.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InsertOneGateway implements InsertGateway {

	private final DBAwared client;

	@Autowired
	public InsertOneGateway(DBAwared client) {
		this.client = client;
	}

	@Override
	public Object insert(AdoptedStatement statement) {
		InsertSingleAdoptedStatement insertStatement = (InsertSingleAdoptedStatement)statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		try {
			collection.insertOne(insertStatement.getDocument());
			return QueryExecutionResult.from(new WriteResult(1, false, null));
		} catch (IllegalArgumentException e) {
			return QueryExecutionResult.documentIsAbsent(e);
		} catch (MongoWriteException e) {
			return QueryExecutionResult.writeFailed(e);
		} catch (Exception e) {
			return QueryExecutionResult.internalError(e);
		}
	}
}
