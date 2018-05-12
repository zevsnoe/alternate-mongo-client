package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectQueryExecutor implements QueryExecutor<SelectAdoptedStatement> {

	@Override
	public Object execute(SelectAdoptedStatement statement, DBCollection collection) {
		BasicDBObject fields = new BasicDBObject();
		fields.put("_id", 0); //TODO: consider showing if in fields
		for (String fieldName : statement.getFields()) {
			fields.put(fieldName, 1);
		}

		BasicDBObject query = new BasicDBObject();
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		if (null != whereStatement) query.put(whereStatement.getKey(), whereStatement.getValue());

		DBCursor cursor = collection.find(query, fields);
		List<DBObject> list = new ArrayList<>();
		while(cursor.hasNext()) {
			DBObject object = cursor.next();
			System.out.println(object);
			list.add(object);
		}
		return list;
	}
}
