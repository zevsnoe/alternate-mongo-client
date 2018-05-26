package db.client.mongo.gateway.result.failed;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.validator.MongoGatewayException;

public class FailedInsertQueryExecutionResult implements QueryExecutionResult {

	private final String result;

	public FailedInsertQueryExecutionResult(IllegalArgumentException e) {
		e.printStackTrace();
		this.result = "Document can't be null for insert: " + e.getMessage();
	}

	public FailedInsertQueryExecutionResult(MongoWriteException e) {
		e.printStackTrace();
		this.result = "Insert failed: " + e.getMessage();
	}

	public FailedInsertQueryExecutionResult(MongoBulkWriteException e) {
		e.printStackTrace();
		this.result = "Bulk Insert failed: " + e.getMessage();
	}

	public FailedInsertQueryExecutionResult(Exception e) {
		e.printStackTrace();
		this.result = "Internal error: " + e.getMessage();
	}

	@Override
	public Object getResult() {
		throw new MongoGatewayException(result);
	}
}
