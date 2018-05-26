package db.client.mongo.gateway.result.success;

import com.mongodb.client.result.DeleteResult;
import db.client.contract.client.QueryExecutionResult;

public class DeleteQueryExecutionResult implements QueryExecutionResult {

	private final DeleteResult result;

	public DeleteQueryExecutionResult(DeleteResult deleteResult) {
		this.result = deleteResult;
	}

	@Override
	public DeleteResult getResult() {
		return result;
	}
}
