package db.client.mongo.gateway.repo;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.UpdateManyAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.UpdateGateway;
import db.client.mongo.gateway.result.QueryExecutionResult;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Updates.combine;

@Repository
public class UpdateManyGateway implements UpdateGateway {

	private final DBAwared client;

	@Autowired
	public UpdateManyGateway(DBAwared client) {
		this.client = client;
	}

	@Override
	public Object update(AdoptedStatement statement) {
		UpdateManyAdoptedStatement updateStatement = (UpdateManyAdoptedStatement) statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		Bson elements = combine(updateStatement.getUpdateElements());
		try {
			return QueryExecutionResult.from(collection.updateMany(updateStatement.getFilter(), elements));
		} catch (IllegalArgumentException e) {
			return QueryExecutionResult.documentIsAbsent(e);
		} catch (MongoBulkWriteException e) {
			return QueryExecutionResult.writeFailed(e);
		} catch (MongoWriteException e) {
			return QueryExecutionResult.writeFailed(e);
		} catch (Exception e) {
			return QueryExecutionResult.internalError(e);
		}
	}
}
