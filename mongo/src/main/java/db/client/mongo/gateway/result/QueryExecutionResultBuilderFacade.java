package db.client.mongo.gateway.result;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedDropQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedInsertQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedQueryExecutionResult;
import db.client.mongo.gateway.result.failed.FailedSelectQueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoClientException;

public class QueryExecutionResultBuilderFacade {

	public static QueryExecutionResult selectSuccessfull(MongoCursor cursor) {
		return SelectQueryExecutionResultBuilder.selectSuccessfull(cursor);
	}

	public static FailedSelectQueryExecutionResult selectFailed(MongoClientException e) {
		return SelectQueryExecutionResultBuilder.selectFailed(e);
	}

	public static FailedSelectQueryExecutionResult selectFailed(Exception e) {
		return SelectQueryExecutionResultBuilder.selectFailed(e);
	}

	public static QueryExecutionResult insertSuccessfull(WriteResult writeResult) {
		return InsertQueryExecutionResultBuilder.insertSuccessfull(writeResult);
	}

	public static FailedInsertQueryExecutionResult insertFailed(IllegalArgumentException e) {
		return InsertQueryExecutionResultBuilder.insertFailed(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(MongoWriteException e) {
		return InsertQueryExecutionResultBuilder.insertFailed(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(MongoBulkWriteException e) {
		return InsertQueryExecutionResultBuilder.insertFailed(e);
	}

	public static FailedInsertQueryExecutionResult insertFailed(Exception e) {
		return InsertQueryExecutionResultBuilder.insertFailed(e);
	}

	public static QueryExecutionResult updateSuccessfull(UpdateResult updateResult) {
		return UpdateQueryExecutionResultBuilder.updateSuccessfull(updateResult);
	}

	public static QueryExecutionResult updateFailed(IllegalArgumentException e) {
		return UpdateQueryExecutionResultBuilder.updateFailed(e);
	}

	public static QueryExecutionResult updateFailed(MongoBulkWriteException e) {
		return UpdateQueryExecutionResultBuilder.updateFailed(e);
	}

	public static QueryExecutionResult updateFailed(MongoWriteException e) {
		return UpdateQueryExecutionResultBuilder.updateFailed(e);
	}

	public static QueryExecutionResult updateFailed(Exception e) {
		return UpdateQueryExecutionResultBuilder.updateFailed(e);
	}

	public static QueryExecutionResult dropSuccessfull(String name) {
		return DropQueryExecutionResultBuilder.dropSuccessfull(name);
	}

	public static FailedDropQueryExecutionResult dropFailed(String name) {
		return DropQueryExecutionResultBuilder.dropFailed(name);
	}

	public static QueryExecutionResult dropFailed(Exception e) {
		return DropQueryExecutionResultBuilder.dropFailed(e);
	}

	public static FailedQueryExecutionResult invalidQuery(InvalidSQLException e) {
		return new FailedQueryExecutionResult(e);
	}

	public static QueryExecutionResult notSupported(UnsupportedOperationException e) {
		return new FailedQueryExecutionResult(e);
	}

	public static QueryExecutionResult internalError(Exception e) {
		return new FailedQueryExecutionResult(e);
	}

	public static QueryExecutionResult deleteSuccesful(DeleteResult deleteResult) {
		return DeleteQueryExecutionResultBuilder.deleteSuccessful(deleteResult);
	}

	public static QueryExecutionResult deleteFailed(MongoWriteException e) {
		return DeleteQueryExecutionResultBuilder.deleteFailed(e);
	}

	public static QueryExecutionResult deleteFailed(MongoWriteConcernException e) {
		return DeleteQueryExecutionResultBuilder.deleteFailed(e);
	}

	public static QueryExecutionResult deleteFailed(MongoException e) {
		return DeleteQueryExecutionResultBuilder.deleteFailed(e);
	}

	public static QueryExecutionResult deleteFailed(Exception e) {
		return DeleteQueryExecutionResultBuilder.deleteFailed(e);
	}
}
