package db.client.mongo.gateway.contract;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public interface DBAwared {
	void createCollection(String collectionName);

	MongoCollection<Document> getCollection(String collectionName);
}
