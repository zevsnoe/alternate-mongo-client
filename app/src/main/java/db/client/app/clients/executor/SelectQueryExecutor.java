package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.app.contract.FilterService;
import db.client.app.contract.QueryExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectQueryExecutor implements QueryExecutor<SelectAdoptedStatement> {

	private final FilterService filterService;

	@Autowired
	public SelectQueryExecutor(FilterService filterService) {
		this.filterService = filterService;
	}

	@Override
	public Object execute(SelectAdoptedStatement statement, DBCollection collection) {
		BasicDBObject fields = fields(statement);
		BasicDBObject filter = filterService.filter(statement);
		return out(collection.find(filter, fields));
	}

	private BasicDBObject fields(SelectAdoptedStatement statement) {
		BasicDBObject fields = new BasicDBObject();
		fields.put("_id", 0); //TODO: consider showing if in fields
		for (String fieldName : statement.getFields()) {
			fields.put(fieldName, 1);
		}
		return fields;
	}

	//TODO: revamp the out design
	private Object out(DBCursor cursor) {
		List<DBObject> list = new ArrayList<>();
		while(cursor.hasNext()) {
			DBObject object = cursor.next();
			System.out.println(object);
			list.add(object);
		}
		return list;
	}
}
