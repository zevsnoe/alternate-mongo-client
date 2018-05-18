package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface SelectGateway {
	Object select(AdoptedStatement statement);
}
