package db.client.mongo.gateway.result.success;

import db.client.contract.client.QueryExecutionResult;

public class DropQueryExecutionResult implements QueryExecutionResult{

	private final String result;

	public DropQueryExecutionResult(String name) {
		this.result = "Collection " + name + " was successfully dropped";
	}

	@Override
	public String getResult() {
		return result;
	}
}
