package db.client.app.clients.executor;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.app.contract.FilterService;
import db.client.app.contract.QueryExecutor;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UpdateQueryExecutor implements QueryExecutor<UpdateAdoptedStatement> {

	private final FilterService filterService;

	@Autowired
	public UpdateQueryExecutor(FilterService filterService) {
		this.filterService = filterService;
	}

	@Override
	public Object execute(UpdateAdoptedStatement statement, DBCollection collection) {
		BasicDBObject filter = filterService.filter(statement);

		BasicDBObject set = new BasicDBObject();
		for (Pair<String, Object> pair : statement.getValues()) {
			set.put(pair.getKey(), pair.getValue());
		}
		return collection.update(filter, new BasicDBObject("$set", set));
	}

}
