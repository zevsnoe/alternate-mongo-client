package db.client.mongo.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import db.client.contract.MongoQueryExecutor;
import db.client.mongo.data.UpdateAdoptedStatement;
import db.client.mongo.helper.WhereExpressionAdapter;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

@Repository
public class UpdateQueryExecutor implements MongoQueryExecutor<UpdateAdoptedStatement> {

	@Override
	public Object execute(UpdateAdoptedStatement statement, MongoCollection collection) {
		Expression whereStatement = statement.getWhereStatement();
		Bson filter = new BasicDBObject();
		if (null != whereStatement) {
			filter = WhereExpressionAdapter.adopt(whereStatement);
		}

		List<Bson> updates = new ArrayList<>();
		for (Pair<String, Object> pair : statement.getValues()) {
			updates.add(set(pair.getKey(), pair.getValue()));
		}
		return collection.updateMany(filter, combine(updates));
	}

}
