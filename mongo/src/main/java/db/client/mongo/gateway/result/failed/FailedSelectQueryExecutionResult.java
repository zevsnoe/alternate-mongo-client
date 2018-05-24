package db.client.mongo.gateway.result.failed;

import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoClientException;
import db.client.mongo.validator.MongoGatewayException;

public class FailedSelectQueryExecutionResult implements QueryExecutionResult {

	private final String result;

	public FailedSelectQueryExecutionResult(MongoClientException e) {
		e.printStackTrace();
		this.result = "Mongo client error: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedSelectQueryExecutionResult(Exception e) {
		e.printStackTrace();
		this.result = "Internal error: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	@Override
	public String getResult() {
		return result;
	}
}