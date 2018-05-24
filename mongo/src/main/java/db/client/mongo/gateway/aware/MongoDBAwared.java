package db.client.mongo.gateway.aware;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.client.mongo.client.DBConfig;
import db.client.mongo.gateway.contract.DBAwared;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MongoDBAwared implements DBAwared {

	private DBConfig config;
	private MongoDatabase database;

	@Autowired
	public void setDBConfig(DBConfig config) {
		this.config = config;
	}

	@PostConstruct
	public void setDataBase() {
		MongoClient mongoClient = new MongoClient(config.host, config.port);
		this.database = mongoClient.getDatabase(config.name);
	}

	@Override
	public void createCollection(String collectionName) {
		try {
			getDataBase().createCollection(collectionName);
		} catch (Exception exception) {
			throw new MongoClientException("Can't create a collection with name " + collectionName);
		}
	}

	@Override
	public MongoCollection<Document> getCollection(String collectionName) {
		try {
			return getDataBase().getCollection(collectionName);
		} catch (IllegalArgumentException exception) {
			throw new MongoClientException("Collection name is invalid");
		} catch (NullPointerException exception) {
			throw new MongoClientException("Database not set");
		}
	}

	MongoDatabase getDataBase() {
		return this.database;
	}
}
