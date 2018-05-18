package db.client.mongo.gateway.repo;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.dto.InsertSingleAdoptedStatement;
import db.client.mongo.gateway.contract.GatewayClient;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InsertOneGateway implements InsertGateway {

	private final GatewayClient client;

	@Autowired
	public InsertOneGateway(GatewayClient client) {
		this.client = client;
	}

	//TODO: cover exceptional cases
	@Override
	public Object insert(AdoptedStatement statement) {
		InsertSingleAdoptedStatement insertStatement = (InsertSingleAdoptedStatement)statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		collection.insertOne(insertStatement.getDocument());
		return QueryExecutionResult.from(new WriteResult(1, false, null));
	}
}
