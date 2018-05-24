package db.client.mongo.gateway.repo;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.InsertSingleAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.result.QueryExecutionResultBuilder;
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
	public QueryExecutionResult insert(AdoptedStatement statement) {
		InsertSingleAdoptedStatement insertStatement = (InsertSingleAdoptedStatement)statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		try {
			collection.insertOne(insertStatement.getDocument());
			WriteResult writeResult = new WriteResult(1, false, null);
			return QueryExecutionResultBuilder.insertSuccessfull(writeResult);
		} catch (IllegalArgumentException e) {
			return QueryExecutionResultBuilder.insertFailed(e);
		} catch (MongoWriteException e) {
			return QueryExecutionResultBuilder.insertFailed(e);
		} catch (MongoBulkWriteException e) {
			return QueryExecutionResultBuilder.insertFailed(e);
		} catch (Exception e) {
			return QueryExecutionResultBuilder.insertFailed(e);
		}
	}
}
