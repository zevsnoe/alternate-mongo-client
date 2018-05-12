package db.client.app.clients.executor.enrich;

import com.mongodb.BasicDBObject;
import db.client.adapter.mongo.bean.WhereAdoptedStatement;
import db.client.app.contract.FilterService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class FilterEnrichmentService implements FilterService {

	@Override
	public BasicDBObject filter(WhereAdoptedStatement statement) {
		BasicDBObject query = new BasicDBObject();
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		if (null != whereStatement) query.put(whereStatement.getKey(), whereStatement.getValue());
		return query;
	}

}
