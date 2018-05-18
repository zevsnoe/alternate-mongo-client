package db.client.mongo.gateway;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.client.mongo.DBConfig;
import db.client.mongo.gateway.contract.GatewayClient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoDBAwared implements GatewayClient {

	private MongoDatabase database;

	@Autowired
	public MongoDBAwared(DBConfig config) {
		MongoClient mongoClient = new MongoClient(config.getHost(), config.getPort());
		this.database = mongoClient.getDatabase(config.getName());
	}

	@Override
	public MongoCollection<Document> getCollection(String collectionName) {
		try {
			return database.getCollection(collectionName);
		} catch (IllegalArgumentException exception) {
			throw new MongoClientException("Collection name is invalid");
		} catch (Exception exception) {
			throw new MongoClientException("Database not set");
		}
	}
}
