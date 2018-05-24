package db.client.mongo.gateway.result;

import com.mongodb.client.MongoCursor;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedSelectQueryExecutionResult;
import db.client.mongo.gateway.result.success.SelectQueryExecutionResult;
import db.client.mongo.validator.MongoClientException;

import java.util.ArrayList;
import java.util.List;

public class SelectQueryExecutionResultBuilder {

	public static QueryExecutionResult selectSuccessfull(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return new SelectQueryExecutionResult(list);
	}

	public static FailedSelectQueryExecutionResult selectFailed(MongoClientException e) {
		return new FailedSelectQueryExecutionResult(e);
	}

	public static FailedSelectQueryExecutionResult selectFailed(Exception e) {
		return new FailedSelectQueryExecutionResult(e);
	}

}
