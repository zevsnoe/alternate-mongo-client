package db.client.mongo.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.app.contract.MongoQueryAdoptedStatement;
import db.client.app.contract.MongoQueryExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class DropQueryExecutor implements MongoQueryExecutor {

	@Override
	public Object execute(MongoQueryAdoptedStatement statement, MongoCollection collection) {
		collection.drop();
		return new WriteResult(0, false, null);
	}
}
