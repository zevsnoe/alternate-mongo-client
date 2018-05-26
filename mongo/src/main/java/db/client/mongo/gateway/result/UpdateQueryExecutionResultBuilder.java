package db.client.mongo.gateway.result;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.result.UpdateResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedUpdateQueryExecutionResult;
import db.client.mongo.gateway.result.success.UpdateQueryExecutionResult;

public class UpdateQueryExecutionResultBuilder {

	public static QueryExecutionResult updateSuccessfull(UpdateResult updateResult) {
		return new UpdateQueryExecutionResult(updateResult);
	}

	public static QueryExecutionResult updateFailed(IllegalArgumentException e) {
		return new FailedUpdateQueryExecutionResult(e);
	}

	public static QueryExecutionResult updateFailed(MongoBulkWriteException e) {
		return new FailedUpdateQueryExecutionResult(e);
	}

	public static QueryExecutionResult updateFailed(MongoWriteException e) {
		return new FailedUpdateQueryExecutionResult(e);
	}

	public static QueryExecutionResult updateFailed(Exception e) {
		return new FailedUpdateQueryExecutionResult(e);
	}

}
