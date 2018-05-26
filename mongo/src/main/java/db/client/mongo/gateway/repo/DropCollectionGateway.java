package db.client.mongo.gateway.repo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.DropGateway;
import db.client.mongo.gateway.result.QueryExecutionResultBuilderFacade;
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
	public QueryExecutionResult drop(AdoptedStatement statement) {
		String collectionName = statement.getCollectionName();
		MongoCollection collection = client.getCollection(collectionName);
		try {
			collection.drop();
			return QueryExecutionResultBuilderFacade.dropSuccessfull(collectionName);
		} catch(MongoException e) {
			return QueryExecutionResultBuilderFacade.dropFailed(collectionName);
		} catch (Exception e) {
			return QueryExecutionResultBuilderFacade.dropFailed(e);
		}

	}
}
