package db.client.mongo.gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.data.SelectAdoptedStatement;
import db.client.mongo.gateway.contract.GatewayClient;
import db.client.mongo.gateway.contract.MongoSelectGateway;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MongoSimpleSelectGateway implements MongoSelectGateway {

	private final GatewayClient client;

	@Autowired
	public MongoSimpleSelectGateway(GatewayClient client) {
		this.client = client;
	}

	//TODO: cover exceptional cases
	@Override
	public Object select(AdoptedStatement statement) {
		SelectAdoptedStatement selectStatement = (SelectAdoptedStatement) statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		MongoCursor cursor = collection.find(selectStatement.getFilter())
				.projection(selectStatement.getProjections())
				.iterator();

		return QueryExecutionResult.from(cursor);
	}
}
