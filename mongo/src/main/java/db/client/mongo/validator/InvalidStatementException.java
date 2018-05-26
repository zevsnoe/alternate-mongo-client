package db.client.mongo.validator;

public class InvalidStatementException extends RuntimeException {
	public InvalidStatementException(String message) {
		super(message);
	}
}
