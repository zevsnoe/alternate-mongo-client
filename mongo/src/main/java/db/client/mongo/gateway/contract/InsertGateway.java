package db.client.mongo.gateway.contract;

import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;

public interface InsertGateway {
	QueryExecutionResult insert(AdoptedStatement statement);
}
