package db.client.contract.mongo;

import com.mongodb.client.MongoCollection;

@FunctionalInterface
public interface QueryExecutor<T extends QueryAdoptedStatement> {
	Object execute(T statement, MongoCollection collection); //TODO: remove
}
