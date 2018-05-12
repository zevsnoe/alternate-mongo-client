package db.client.app.clients.executor;

import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.app.contract.QueryExecutor;
import org.springframework.stereotype.Repository;

@Repository
public class DropQueryExecutor implements QueryExecutor {

	@Override
	public Object execute(AdoptedStatement statement, DBCollection collection) {
		collection.drop();
		return new WriteResult(0, false, null);
	}
}
