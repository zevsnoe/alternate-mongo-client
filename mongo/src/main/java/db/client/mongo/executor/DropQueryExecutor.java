package db.client.mongo.executor;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import db.client.contract.mongo.QueryAdoptedStatement;
import db.client.contract.mongo.QueryExecutor;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class DropQueryExecutor extends DatabaseHolder implements QueryExecutor {

	@Override
	public Object execute(QueryAdoptedStatement statement) {
		MongoCollection<Document> collection = getCollection(statement.getCollectionName());
		collection.drop();
		return new WriteResult(0, false, null);
	}

}
