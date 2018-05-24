package db.client.mongo.gateway.result;

import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedDropQueryExecutionResult;
import db.client.mongo.gateway.result.success.DropQueryExecutionResult;

public class DropQueryExecutionResultBuilder {

	public static QueryExecutionResult dropSuccessfull(String name) {
		return new DropQueryExecutionResult(name);
	}

	public static FailedDropQueryExecutionResult dropFailed(String name) {
		return new FailedDropQueryExecutionResult(name);
	}

	public static QueryExecutionResult dropFailed(Exception e) {
		return new FailedDropQueryExecutionResult(e);
	}

}
