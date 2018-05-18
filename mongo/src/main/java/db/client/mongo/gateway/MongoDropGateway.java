package db.client.mongo.gateway;

import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.GatewayClient;
import db.client.mongo.gateway.contract.MongoDrop;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MongoDropGateway implements MongoDrop {

	private final GatewayClient client;

	@Autowired
	public MongoDropGateway(GatewayClient client) {
		this.client = client;
	}

	//TODO: cover exceptional cases
	@Override
	public Object drop(AdoptedStatement statement) {
		String collectionName = statement.getCollectionName();
		MongoCollection collection = client.getCollection(collectionName);
		collection.drop();
		return QueryExecutionResult.dropSuccessfull(collectionName);
	}
}
