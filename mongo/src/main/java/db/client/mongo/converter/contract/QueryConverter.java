package db.client.mongo.converter.contract;

import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.validator.InvalidSQLException;

@FunctionalInterface
public interface QueryConverter {
	QueryConvertedStatement convert(String query) throws InvalidSQLException;
}
