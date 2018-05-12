package db.client.app.contract;

import com.mongodb.BasicDBObject;
import db.client.adapter.mongo.bean.WhereAdoptedStatement;

@FunctionalInterface
public interface FilterService {
	BasicDBObject filter(WhereAdoptedStatement statement);
}
