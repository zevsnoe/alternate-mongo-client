package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface MongoSelectGateway {
	//TODO: cover exceptional cases
	Object select(AdoptedStatement statement);
}
