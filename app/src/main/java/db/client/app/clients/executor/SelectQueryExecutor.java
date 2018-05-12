package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Repository
public class SelectQueryExecutor implements QueryExecutor<SelectAdoptedStatement> {

	@Override
	public Object execute(SelectAdoptedStatement statement, MongoCollection collection) {
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		Bson filter = new BasicDBObject();
		if (null != whereStatement) {
			filter = eq(whereStatement.getKey(), whereStatement.getValue());
		}
		MongoCursor cursor = collection.find(filter).projection(fields(include(statement.getProjections()), excludeId())).iterator();
		return out(cursor);
	}

	private List out(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}
}
