package db.client.mongo.gateway.result.success;

import com.mongodb.client.result.UpdateResult;
import db.client.contract.client.QueryExecutionResult;

public class UpdateQueryExecutionResult implements QueryExecutionResult {
	private final UpdateResult result;

	public UpdateQueryExecutionResult(UpdateResult updateResult) {
		result = updateResult;
	}

	@Override
	public UpdateResult getResult() {
		return result;
	}
}
