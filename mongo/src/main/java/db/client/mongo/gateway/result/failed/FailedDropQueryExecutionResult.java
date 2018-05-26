package db.client.mongo.gateway.result.failed;

import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoGatewayException;

public class FailedDropQueryExecutionResult implements QueryExecutionResult{
	private final String result;

	public FailedDropQueryExecutionResult(String name) {
		this.result = "Collection " + name + " was not found";
		throw new MongoGatewayException(result);
	}

	public FailedDropQueryExecutionResult(Exception e) {
		this.result = "Drop failed due to internal error: "+ e.getMessage();
		throw new MongoGatewayException(result);
	}

	@Override
	public String getResult() {
		return result;
	}
}
