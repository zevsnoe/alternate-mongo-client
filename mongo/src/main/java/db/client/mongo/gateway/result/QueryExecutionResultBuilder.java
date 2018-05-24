package db.client.mongo.gateway.result;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedDropQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedInsertQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedSelectQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedUpdateQueryExecutionResult;
import db.client.mongo.gateway.result.success.DropQueryExecutionResult;
import db.client.mongo.gateway.result.success.InsertQueryExecutionResult;
import db.client.mongo.gateway.result.success.SelectQueryExecutionResult;
import db.client.mongo.gateway.result.success.UpdateQueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoClientException;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutionResultBuilder {

	//SELECT
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

	//INSERT
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

	//UPDATE
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

	//DROP
	public static QueryExecutionResult dropSuccessfull(String name) {
		return new DropQueryExecutionResult(name);
	}

	public static FailedDropQueryExecutionResult dropFailed(String name) {
		return new FailedDropQueryExecutionResult(name);
	}

	public static QueryExecutionResult dropFailed(Exception e) {
		return new FailedDropQueryExecutionResult(e);
	}

	//OTHER
	public static FailedQueryExecutionResult invalidQuery(InvalidSQLException e) {
		return new FailedQueryExecutionResult(e);
	}

	public static QueryExecutionResult notSupported(UnsupportedOperationException e) {
		return new FailedQueryExecutionResult(e);
	}

	public static QueryExecutionResult internalError(Exception e) {
		return new FailedQueryExecutionResult(e);
	}

}
