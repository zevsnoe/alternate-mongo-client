package db.client.app.contract;

import com.mongodb.client.MongoCollection;
import db.client.app.adapter.mongo.bean.AdoptedStatement;

@FunctionalInterface
public interface QueryExecutor<T extends AdoptedStatement> {
	Object execute(T statement, MongoCollection collection);
}
