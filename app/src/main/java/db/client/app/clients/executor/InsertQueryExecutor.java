package db.client.app.clients.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.adapter.mongo.bean.InsertAdoptedStatement;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class InsertQueryExecutor implements QueryExecutor<InsertAdoptedStatement> {

	@Override
	public Object execute(InsertAdoptedStatement statement, MongoCollection collection) {
		collection.insertOne(insertable(statement));
		return new WriteResult(0, false, null);
	}

	private Document insertable(InsertAdoptedStatement statement) {
		Document doc = new Document();
		for (Pair<String, Object> pair : statement.getValues()) {
			doc.append(pair.getKey(), pair.getValue());
		}
		return doc;
	}
}
