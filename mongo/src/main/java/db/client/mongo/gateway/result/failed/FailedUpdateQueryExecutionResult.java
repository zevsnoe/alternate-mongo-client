package db.client.mongo.gateway.result.failed;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoGatewayException;

public class FailedUpdateQueryExecutionResult implements QueryExecutionResult {
	private final String result;

	public FailedUpdateQueryExecutionResult(IllegalArgumentException e) {
		this.result = "Document can't be null for update: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedUpdateQueryExecutionResult(MongoWriteException e) {
		this.result = "Update failed: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedUpdateQueryExecutionResult(MongoBulkWriteException e) {
		this.result = "Bulk Update failed: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedUpdateQueryExecutionResult(Exception e) {
		this.result = "Internal error: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	@Override
	public String getResult() {
		return result;
	}
}
