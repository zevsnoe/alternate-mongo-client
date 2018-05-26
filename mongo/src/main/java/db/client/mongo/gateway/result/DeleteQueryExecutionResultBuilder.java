package db.client.mongo.gateway.result;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.result.DeleteResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedDeleteQueryExecutionResult;
import db.client.mongo.gateway.result.success.DeleteQueryExecutionResult;

public class DeleteQueryExecutionResultBuilder {
	public static QueryExecutionResult deleteSuccessful(DeleteResult deleteResult) {
		return new DeleteQueryExecutionResult(deleteResult);
	}

	public static QueryExecutionResult deleteFailed(MongoWriteException e) {
		return new FailedDeleteQueryExecutionResult(e);
	}

	public static QueryExecutionResult deleteFailed(MongoWriteConcernException e) {
		return new FailedDeleteQueryExecutionResult(e);
	}

	public static QueryExecutionResult deleteFailed(MongoException e) {
		return new FailedDeleteQueryExecutionResult(e);
	}

	public static QueryExecutionResult deleteFailed(Exception e) {
		return new FailedDeleteQueryExecutionResult(e);
	}
}
