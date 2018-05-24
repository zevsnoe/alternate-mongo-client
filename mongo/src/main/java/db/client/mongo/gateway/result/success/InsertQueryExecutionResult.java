package db.client.mongo.gateway.result.success;

import com.mongodb.WriteResult;
import db.client.contract.client.QueryExecutionResult;

public class InsertQueryExecutionResult implements QueryExecutionResult {

	private final WriteResult writeResult;

	public InsertQueryExecutionResult(WriteResult writeResult) {
		this.writeResult = writeResult;
	}

	@Override
	public WriteResult getResult() {
		return writeResult;
	}
}
