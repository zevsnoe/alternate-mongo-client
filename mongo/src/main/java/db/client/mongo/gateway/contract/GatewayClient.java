package db.client.mongo.gateway.contract;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface GatewayClient {
	MongoCollection<Document> getCollection(String collectionName);
}
