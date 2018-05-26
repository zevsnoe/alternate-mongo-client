package db.client.mongo.gateway.result.failed;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoGatewayException;

public class FailedDeleteQueryExecutionResult implements QueryExecutionResult {
	private final String result;

	public FailedDeleteQueryExecutionResult(MongoWriteException e) {
		this.result = "Delete failed: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedDeleteQueryExecutionResult(MongoWriteConcernException e) {
		this.result = "Delete failed: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedDeleteQueryExecutionResult(MongoException e) {
		this.result = "Delete failed: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	public FailedDeleteQueryExecutionResult(Exception e) {
		this.result = "Internal error: " + e.getMessage();
		throw new MongoGatewayException(result);
	}

	@Override
	public String getResult() {
		return result;
	}
}
