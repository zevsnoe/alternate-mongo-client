package db.client.mongo.gateway.result.success;

import db.client.contract.client.QueryExecutionResult;

import java.util.List;

public class SelectQueryExecutionResult implements QueryExecutionResult {
	private final List result;

	public SelectQueryExecutionResult(List result) {
		this.result = result;
	}

	@Override
	public List getResult() {
		return result;
	}
}
