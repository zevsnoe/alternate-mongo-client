package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateQueryExecutor implements QueryExecutor<UpdateAdoptedStatement> {
	@Override
	public Object execute(UpdateAdoptedStatement statement, DBCollection collection) {
		BasicDBObject query = new BasicDBObject();
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		if (null != whereStatement) query.put(whereStatement.getKey(), whereStatement.getValue());

		BasicDBObject set = new BasicDBObject();
		for (Pair<String, Object> pair : statement.getValues()) {
			set.put(pair.getKey(), pair.getValue());
		}
		return collection.update(query, new BasicDBObject("$set", set));
	}
}
