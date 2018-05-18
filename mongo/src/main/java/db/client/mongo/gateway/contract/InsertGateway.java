package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface InsertGateway {
	Object insert(AdoptedStatement statement);
}
