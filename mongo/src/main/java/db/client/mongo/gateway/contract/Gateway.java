package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface Gateway {
	Object execute(AdoptedStatement adoptedStatement);
}