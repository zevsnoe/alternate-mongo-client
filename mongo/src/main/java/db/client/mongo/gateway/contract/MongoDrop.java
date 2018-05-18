package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface MongoDrop {
	Object drop(AdoptedStatement statement);
}
