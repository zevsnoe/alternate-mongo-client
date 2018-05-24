package db.client.mongo.gateway.contract;

import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;

public interface SelectGateway {
	QueryExecutionResult select(AdoptedStatement statement);
}
