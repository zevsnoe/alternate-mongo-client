package db.client.app.clients.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.DropAdoptedStatement;
import db.client.adapter.mongo.bean.InsertAdoptedStatement;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.adapter.mongo.validator.MongoSQLAdapterException;
import db.client.app.MongoConfig;
import db.client.app.clients.executor.DropQueryExecutor;
import db.client.app.clients.executor.InsertQueryExecutor;
import db.client.app.clients.executor.SelectQueryExecutor;
import db.client.app.clients.executor.UpdateQueryExecutor;
import db.client.app.contract.Client;
import db.client.app.contract.QueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MongoDBClient implements Client {

	private final DropQueryExecutor dropQueryExecutor;
	private final InsertQueryExecutor insertQueryExecutor;
	private final UpdateQueryExecutor updateQueryExecutor;
	private final SelectQueryExecutor selectQueryExecutor;
	private final MongoConfig mongoConfig;

	MongoClient mongoClient;
	MongoDatabase database;

	@Autowired
	public MongoDBClient(DropQueryExecutor dropQueryExecutor,
						 InsertQueryExecutor insertQueryExecutor,
						 UpdateQueryExecutor updateQueryExecutor,
						 SelectQueryExecutor selectQueryExecutor,
						 MongoConfig mongoConfig) {
		this.dropQueryExecutor = dropQueryExecutor;
		this.insertQueryExecutor = insertQueryExecutor;
		this.updateQueryExecutor = updateQueryExecutor;
		this.selectQueryExecutor = selectQueryExecutor;
		this.mongoConfig = mongoConfig;
	}

	@PostConstruct
	public void init() {
		mongoClient = new MongoClient(mongoConfig.getHost(), mongoConfig.getPort());
		database = mongoClient.getDatabase(mongoConfig.getName());
	}

	//TODO: refactor, use polymorphic dispatch via pattern(visitor again - or mb go structural?)
	@Override
	public Object execute(AdoptedStatement adoptedStatement) {
		if (adoptedStatement instanceof SelectAdoptedStatement) {
			return executeVia(selectQueryExecutor, adoptedStatement);
		} else if (adoptedStatement instanceof InsertAdoptedStatement) {
			return executeVia(insertQueryExecutor, adoptedStatement);
		} else if (adoptedStatement instanceof UpdateAdoptedStatement) {
			return executeVia(updateQueryExecutor, adoptedStatement);
		} else if (adoptedStatement instanceof DropAdoptedStatement) {
			return executeVia(dropQueryExecutor, adoptedStatement);
		} else throw new MongoSQLAdapterException("Undefined Statement");
	}

	private Object executeVia(QueryExecutor queryExecutor, AdoptedStatement adoptedStatement) {
		return queryExecutor.execute(adoptedStatement, getCollection(adoptedStatement));
	}

	private MongoCollection getCollection(AdoptedStatement adoptedStatement) {
		return database.getCollection(adoptedStatement.getCollectionName());
	}
}
