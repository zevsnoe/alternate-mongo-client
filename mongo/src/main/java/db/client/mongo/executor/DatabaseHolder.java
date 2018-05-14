package db.client.mongo.executor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DatabaseHolder {

	private MongoDatabase database;

	public synchronized void setDatabase(MongoDatabase database) {
		if(this.database == null) this.database = database;
	}

	protected MongoCollection<Document> getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}
}
