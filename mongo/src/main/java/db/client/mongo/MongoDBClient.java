package db.client.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import db.client.contract.Client;
import db.client.contract.mongo.QueryAdoptedStatement;
import db.client.contract.mongo.QueryExecutor;
import db.client.mongo.data.DropAdoptedStatement;
import db.client.mongo.data.InsertAdoptedStatement;
import db.client.mongo.data.SelectAdoptedStatement;
import db.client.mongo.data.UpdateAdoptedStatement;
import db.client.mongo.executor.DropQueryExecutor;
import db.client.mongo.executor.InsertQueryExecutor;
import db.client.mongo.executor.SelectQueryExecutor;
import db.client.mongo.executor.UpdateQueryExecutor;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MongoDBClient implements Client {
	private final DropQueryExecutor dropQueryExecutor;
	private final InsertQueryExecutor insertQueryExecutor;
	private final UpdateQueryExecutor updateQueryExecutor;
	private final SelectQueryExecutor selectQueryExecutor;
	private final MongoDBConfig mongoConfig;
	private final MongoQueryAdapter adapter;

	@Autowired
	public MongoDBClient(DropQueryExecutor dropQueryExecutor,
						 InsertQueryExecutor insertQueryExecutor,
						 UpdateQueryExecutor updateQueryExecutor,
						 SelectQueryExecutor selectQueryExecutor,
						 MongoDBConfig mongoConfig,
						 MongoQueryAdapter adapter) {
		this.dropQueryExecutor = dropQueryExecutor;
		this.insertQueryExecutor = insertQueryExecutor;
		this.updateQueryExecutor = updateQueryExecutor;
		this.selectQueryExecutor = selectQueryExecutor;
		this.mongoConfig = mongoConfig;
		this.adapter = adapter;
	}

	@PostConstruct
	public void init() {
		MongoClient mongoClient = new MongoClient(mongoConfig.getHost(), mongoConfig.getPort());
		MongoDatabase database = mongoClient.getDatabase(mongoConfig.getName());
		dropQueryExecutor.setDatabase(database);
		insertQueryExecutor.setDatabase(database);
		updateQueryExecutor.setDatabase(database);
		selectQueryExecutor.setDatabase(database);
	}

	//TODO: refactor, use polymorphic dispatch via pattern(visitor again - or mb go structural?)
	@Override
	public Object execute(String query) {
		QueryAdoptedStatement adoptedStatement = adapter.adopt(query);
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

	private Object executeVia(QueryExecutor queryExecutor, QueryAdoptedStatement adoptedStatement) {
		return queryExecutor.execute(adoptedStatement);
	}

}