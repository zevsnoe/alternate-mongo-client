package db.client.mongo.converter.contract;

import db.client.contract.mongo.QueryConvertedStatement;

@FunctionalInterface
public interface QueryConverter {
	QueryConvertedStatement convert(String query);
}
