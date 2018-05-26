package db.client.mongo.gateway.repo;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.UpdateManyAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.UpdateGateway;
import db.client.mongo.gateway.result.QueryExecutionResultBuilderFacade;
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
	public QueryExecutionResult update(AdoptedStatement statement) {
		UpdateManyAdoptedStatement updateStatement = (UpdateManyAdoptedStatement) statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		Bson elements = combine(updateStatement.getUpdateElements());
		try {
			return QueryExecutionResultBuilderFacade.updateSuccessfull(collection.updateMany(updateStatement.getFilter(), elements));
		} catch (IllegalArgumentException e) {
			return QueryExecutionResultBuilderFacade.updateFailed(e);
		} catch (MongoBulkWriteException e) {
			return QueryExecutionResultBuilderFacade.updateFailed(e);
		} catch (MongoWriteException e) {
			return QueryExecutionResultBuilderFacade.updateFailed(e);
		} catch (Exception e) {
			return QueryExecutionResultBuilderFacade.updateFailed(e);
		}
	}
}
