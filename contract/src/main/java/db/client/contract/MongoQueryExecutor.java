package db.client.contract;

import com.mongodb.client.MongoCollection;

@FunctionalInterface
public interface MongoQueryExecutor<T extends MongoQueryAdoptedStatement> {
	Object execute(T statement, MongoCollection collection); //TODO: remove
}
