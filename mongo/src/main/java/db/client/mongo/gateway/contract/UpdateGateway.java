package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface UpdateGateway {
	Object update(AdoptedStatement statement);
}
