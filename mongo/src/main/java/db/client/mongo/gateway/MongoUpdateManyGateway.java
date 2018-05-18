package db.client.mongo.gateway;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.data.UpdateManyAdoptedStatement;
import db.client.mongo.gateway.contract.GatewayClient;
import db.client.mongo.gateway.contract.MongoUpdateGateway;
import db.client.mongo.gateway.dto.QueryExecutionResult;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.mongodb.client.model.Updates.combine;

@Repository
public class MongoUpdateManyGateway implements MongoUpdateGateway {

	private final GatewayClient client;

	@Autowired
	public MongoUpdateManyGateway(GatewayClient client) {
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
