package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface MongoInsertGateway {
	Object insert(AdoptedStatement statement);
}
