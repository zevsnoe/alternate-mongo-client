package db.client.app.clients.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.app.contract.QueryExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class DropQueryExecutor implements QueryExecutor {

	@Override
	public Object execute(AdoptedStatement statement, MongoCollection collection) {
		collection.drop();
		return new WriteResult(0, false, null);
	}
}
