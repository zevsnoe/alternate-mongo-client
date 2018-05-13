package db.client.app.clients.mongo.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.app.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.app.adapter.mongo.helper.WhereExpressionAdapter;
import db.client.app.contract.QueryExecutor;
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
		MongoCursor cursor = collection.find(filter).projection(fields(include(statement.getProjections()), excludeId())).iterator();
		return out(cursor);
	}

	private List out(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}

}
