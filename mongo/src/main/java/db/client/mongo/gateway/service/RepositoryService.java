package db.client.mongo.gateway.service;

import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.DropGateway;
import db.client.mongo.gateway.contract.Gateway;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.contract.SelectGateway;
import db.client.mongo.gateway.contract.UpdateGateway;
import db.client.mongo.validator.MongoGatewayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryService implements Gateway {

	private final SelectGateway selectGateway;
	private final InsertGateway insertGateway;
	private final UpdateGateway updateGateway;
	private final DropGateway dropGateway;

	@Autowired
	public RepositoryService(SelectGateway selectGateway,
							 InsertGateway insertGateway,
							 UpdateGateway updateGateway,
							 DropGateway dropGateway) {
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
			default: throw new MongoGatewayException("Undefined Statement");
		}
	}

}
