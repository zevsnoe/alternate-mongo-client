package db.client.mongo.executor;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.contract.mongo.QueryExecutor;
import db.client.mongo.data.SelectAdoptedStatement;
import db.client.mongo.executor.helper.SelectProjections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SelectQueryExecutor extends DatabaseHolder implements QueryExecutor<SelectAdoptedStatement> {

	@Override
	public Object execute(SelectAdoptedStatement statement) {
		return iterateOver(cursorFrom(statement));
	}

	private List iterateOver(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}

	private MongoCursor<Document> cursorFrom(SelectAdoptedStatement statement) {
		MongoCollection<Document> collection = getCollection(statement.getCollectionName());
		Bson filter = statement.getWhereStatement();
		Bson projection = getProjections(statement);
		return collection.find(filter).projection(projection).iterator();
	}

	private Bson getProjections(SelectAdoptedStatement statement) {
		if (statement.hasIds()) {
			return SelectProjections.projectionsWithIds(statement);
		} else {
			return SelectProjections.projections(statement);
		}
	}

}
