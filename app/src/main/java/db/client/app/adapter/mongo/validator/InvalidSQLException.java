package db.client.app.adapter.mongo.validator;

public class InvalidSQLException extends MongoSQLAdapterException {
	public InvalidSQLException(String sql) {
		super("Invalid query: " + sql);
	}
}
