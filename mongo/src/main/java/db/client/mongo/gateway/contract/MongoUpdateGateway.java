package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface MongoUpdateGateway {
	Object update(AdoptedStatement statement);
}
