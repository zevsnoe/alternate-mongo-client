package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface MongoDropGateway {
	Object drop(AdoptedStatement statement);
}
