package db.client.mongo.executor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.contract.mongo.QueryExecutor;
import db.client.mongo.data.SelectAdoptedStatement;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Repository
public class SelectQueryExecutor extends DatabaseHolder implements QueryExecutor<SelectAdoptedStatement> {

	@Override
	public Object execute(SelectAdoptedStatement statement) {
		MongoCollection<Document> collection = getCollection(statement.getCollectionName());
		Bson filter = statement.getWhereStatement();
		MongoCursor cursor = collection.find(filter).projection(getProjections(statement)).iterator();
		return out(cursor);
	}

	private Bson getProjections(SelectAdoptedStatement statement) {
		if (statement.hasIds()) {
			return projectionsWithIds(statement);
		} else {
			return projections(statement);
		}
	}

	private Bson projections(SelectAdoptedStatement statement) {
		return fields(include(statement.getProjections()), excludeId());
	}

	private Bson projectionsWithIds(SelectAdoptedStatement statement) {
		return fields(include(statement.getProjections()));
	}

	private List out(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}

}
