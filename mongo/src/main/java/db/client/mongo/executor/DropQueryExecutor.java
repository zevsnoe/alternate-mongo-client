package db.client.mongo.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.QueryAdoptedStatement;
import db.client.contract.mongo.QueryExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class DropQueryExecutor implements QueryExecutor {

	@Override
	public Object execute(QueryAdoptedStatement statement, MongoCollection collection) {
		collection.drop();
		return new WriteResult(0, false, null);
	}
}
