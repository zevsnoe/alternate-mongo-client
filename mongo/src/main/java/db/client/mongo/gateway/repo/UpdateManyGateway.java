package db.client.mongo.gateway.repo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.dto.UpdateManyAdoptedStatement;
import db.client.mongo.gateway.contract.GatewayClient;
import db.client.mongo.gateway.contract.UpdateGateway;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Updates.combine;

@Repository
public class UpdateManyGateway implements UpdateGateway {

	private final GatewayClient client;

	@Autowired
	public UpdateManyGateway(GatewayClient client) {
		this.client = client;
	}

	//TODO: cover exceptional cases
	@Override
	public Object update(AdoptedStatement statement) {
		UpdateManyAdoptedStatement updateStatement = (UpdateManyAdoptedStatement) statement;
		MongoCollection collection = client.getCollection(statement.getCollectionName());
		Bson elements = combine(updateStatement.getUpdateElements());
		UpdateResult updateResult = collection.updateMany(updateStatement.getFilter(), elements);
		return QueryExecutionResult.from(updateResult);
	}
}
