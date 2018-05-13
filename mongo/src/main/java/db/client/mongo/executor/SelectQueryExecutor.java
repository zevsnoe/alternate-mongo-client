package db.client.mongo.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.contract.mongo.QueryExecutor;
import db.client.mongo.data.SelectAdoptedStatement;
import db.client.mongo.helper.WhereExpressionAdapter;
import net.sf.jsqlparser.expression.Expression;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Repository
public class SelectQueryExecutor implements QueryExecutor<SelectAdoptedStatement> {

	@Override
	public Object execute(SelectAdoptedStatement statement, MongoCollection collection) {
		Expression whereStatement = statement.getWhereStatement();
		Bson filter = new BasicDBObject();
		if (null != whereStatement) {
			filter = WhereExpressionAdapter.adopt(whereStatement);
		}
		MongoCursor cursor = collection.find(filter).projection(getProjections(statement)).iterator();
		return out(cursor);
	}

	private Bson getProjections(SelectAdoptedStatement statement) {
		//TODO: include id case
		return fields(include(statement.getProjections()), excludeId());
	}

	private List out(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}

}
