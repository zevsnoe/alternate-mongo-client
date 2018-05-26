package db.client.mongo.validator;

public class InvalidSQLException extends MongoSQLAdapterException {
	public InvalidSQLException(String sql) {
		super(sql);
	}
}
