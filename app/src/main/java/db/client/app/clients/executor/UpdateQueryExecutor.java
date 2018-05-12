package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@Repository
public class UpdateQueryExecutor implements QueryExecutor<UpdateAdoptedStatement> {

	@Override
	public Object execute(UpdateAdoptedStatement statement, MongoCollection collection) {
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		Bson filter = new BasicDBObject();
		if (null != whereStatement) {
			filter = eq(whereStatement.getKey(), whereStatement.getValue());
		}

		List<Bson> updates = new ArrayList<>();
		for (Pair<String, Object> pair : statement.getValues()) {
			updates.add(set(pair.getKey(), pair.getValue()));
		}
		return collection.updateMany(filter, combine(updates));
	}

}
