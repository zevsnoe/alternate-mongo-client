package db.client.mongo.gateway.result;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedInsertQueryExecutionResult;
import db.client.mongo.gateway.result.success.InsertQueryExecutionResult;

public class InsertQueryExecutionResultBuilder {

	public static QueryExecutionResult insertSuccessfull(WriteResult writeResult) {
		return new InsertQueryExecutionResult(writeResult);
	}

	public static FailedInsertQueryExecutionResult insertFailed(IllegalArgumentException e) {
		return new FailedInsertQueryExecutionResult(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(MongoWriteException e) {
		return new FailedInsertQueryExecutionResult(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(MongoBulkWriteException e) {
		return new FailedInsertQueryExecutionResult(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(Exception e) {
		return new FailedInsertQueryExecutionResult(e);
	}

}
