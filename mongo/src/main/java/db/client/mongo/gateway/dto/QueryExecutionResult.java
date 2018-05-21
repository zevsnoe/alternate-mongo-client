package db.client.mongo.gateway.dto;

import com.mongodb.WriteResult;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.result.UpdateResult;
import db.client.mongo.validator.InvalidSQLException;

import java.util.ArrayList;
import java.util.List;

public class QueryExecutionResult {

	private final Object result;

	private QueryExecutionResult(Object result) {
		this.result = result;
	}

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

	public static Object from(UnsupportedOperationException e) {
		return "Operation is not supported";
	}

	public static Object from(InvalidSQLException e) {
		return e.getMessage();
	}
}
