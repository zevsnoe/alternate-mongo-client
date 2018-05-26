package db.client.mongo.gateway.result.failed;

import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoGatewayException;

public class FailedQueryExecutionResult implements QueryExecutionResult{

	private final String result;

	public FailedQueryExecutionResult(InvalidSQLException e) {
		this.result = "Invalid query: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedQueryExecutionResult(UnsupportedOperationException e) {
		this.result = "Unsupported operation + " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedQueryExecutionResult(Exception e) {
		this.result = "Internal error + " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	@Override
	public String getResult() {
		return result;
	}
}
