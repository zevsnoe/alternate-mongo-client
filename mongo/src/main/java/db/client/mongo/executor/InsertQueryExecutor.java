package db.client.mongo.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.QueryExecutor;
import db.client.mongo.data.InsertAdoptedStatement;
import javafx.util.Pair;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class InsertQueryExecutor extends DatabaseHolder implements QueryExecutor<InsertAdoptedStatement> {

	@Override
	public Object execute(InsertAdoptedStatement statement) {
		MongoCollection<Document> collection = getCollection(statement.getCollectionName());
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
