package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import db.client.adapter.mongo.bean.InsertAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.springframework.stereotype.Repository;

@Repository
public class InsertQueryExecutor implements QueryExecutor<InsertAdoptedStatement> {

	@Override
	public Object execute(InsertAdoptedStatement statement, DBCollection collection) {
		return collection.insert(insertable(statement));
	}

	private BasicDBObject insertable(InsertAdoptedStatement statement) {
		BasicDBObject o = new BasicDBObject();
		for (Pair<String, Object> pair : statement.getValues()) {
			o.put(pair.getKey(), pair.getValue());
		}
		return o;
	}
}
