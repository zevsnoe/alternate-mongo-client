package db.client.mongo.executor;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public abstract class DatabaseHolder {

	private MongoDatabase database;

	public synchronized void setDatabase(MongoDatabase database) {
		if(this.database == null) this.database = database;
	}

	protected MongoCollection<Document> getCollection(String collectionName) {
		try {
			return database.getCollection(collectionName);
		} catch (IllegalArgumentException exception) {
			throw new MongoClientException("Collection name is invalid");
		} catch (Exception exception) {
			throw new MongoClientException("Database not set");
		}
	}
}
