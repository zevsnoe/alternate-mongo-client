package db.client.mongo.gateway.result.success;

import db.client.contract.client.QueryExecutionResult;
import org.bson.Document;

import java.util.List;

public class SelectQueryExecutionResult implements QueryExecutionResult {
	private final List<Document> result;

	public SelectQueryExecutionResult(List<Document> result) {
		this.result = result;
	}

	@Override
	public List<Document> getResult() {
		return result;
	}
}
