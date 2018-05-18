package db.client.mongo.gateway;

import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.Gateway;
import db.client.mongo.gateway.contract.MongoDrop;
import db.client.mongo.gateway.contract.MongoInsertGateway;
import db.client.mongo.gateway.contract.MongoSelectGateway;
import db.client.mongo.gateway.contract.MongoUpdateGateway;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MongoGateway implements Gateway {

	private final MongoSelectGateway selectGateway;
	private final MongoInsertGateway insertGateway;
	private final MongoUpdateGateway updateGateway;
	private final MongoDrop dropGateway;

	@Autowired
	public MongoGateway(MongoSelectGateway selectGateway,
						MongoInsertGateway insertGateway,
						MongoUpdateGateway updateGateway,
						MongoDropGateway dropGateway) {
		this.selectGateway = selectGateway;
		this.insertGateway = insertGateway;
		this.updateGateway = updateGateway;
		this.dropGateway = dropGateway;
	}


	@Override
	public Object execute(AdoptedStatement adoptedStatement) {
		switch(adoptedStatement.getType()) {
			case SELECT: return selectGateway.select(adoptedStatement);
			case INSERT: return insertGateway.insert(adoptedStatement);
			case UPDATE: return updateGateway.update(adoptedStatement);
			case DROP:   return dropGateway.drop(adoptedStatement);
			default: throw new MongoSQLAdapterException("Undefined Statement");
		}
	}
}
