package db.client.mongo.executor.helper;

import db.client.mongo.data.SelectAdoptedStatement;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class SelectProjections {

	public static Bson projections(SelectAdoptedStatement statement) {
		return fields(include(statement.getProjections()), excludeId());
	}

	public static Bson projectionsWithIds(SelectAdoptedStatement statement) {
		return fields(include(statement.getProjections()));
	}
}
