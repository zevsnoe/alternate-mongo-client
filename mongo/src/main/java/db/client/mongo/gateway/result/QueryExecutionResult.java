package db.client.mongo.gateway.result;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import db.client.mongo.validator.InvalidSQLException;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutionResult {

	public static Object from(MongoCursor cursor) {
		List list = new ArrayList<>();
		while(cursor.hasNext()) list.add(cursor.next());
		return list;
	}

	public static Object from(UpdateResult updateResult) {
		return updateResult;
	}

	public static Object from(WriteResult writeResult) {
		return writeResult;
	}

	public static Object dropSuccessfull(String name) {
		return new String("Collection " + name + " was successfully dropped");
	}

	public static Object dropFailed(String name) {
		return new String("Collection " + name + " was not found");
	}

	public static Object from(UnsupportedOperationException e) {
		return "Operation is not supported";
	}

	public static Object from(InvalidSQLException e) {
		return e.getMessage();
	}

	public static Object documentIsAbsent(IllegalArgumentException e) {
		return "Document can't be null for insert: " + e.getMessage();
	}

	public static Object writeFailed(MongoWriteException e) {
		return "Write failed: " + e.getMessage();
	}

	public static Object writeFailed(MongoBulkWriteException e) {
		return "Write failed: " + e.getMessage();
	}

	public static Object internalError(Exception e) {
		return "Internal error: " + e.getMessage();
	}
}
