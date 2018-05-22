package db.client.mongo.gateway.repo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.DropGateway;
import db.client.mongo.gateway.result.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DropCollectionGateway implements DropGateway {

	private final DBAwared client;

	@Autowired
	public DropCollectionGateway(DBAwared client) {
		this.client = client;
	}

	@Override
	public Object drop(AdoptedStatement statement) {
		String collectionName = statement.getCollectionName();
		MongoCollection collection = client.getCollection(collectionName);
		try {
			collection.drop();
			return QueryExecutionResult.dropSuccessfull(collectionName);
		} catch(MongoException e) {
			return QueryExecutionResult.dropFailed(collectionName);
		} catch (Exception e) {
			return QueryExecutionResult.internalError(e);
		}

	}
}
