package db.client.mongo.converter.contract;

import db.client.contract.mongo.QueryConvertedStatement;

@FunctionalInterface
public interface Converter<T> {
	QueryConvertedStatement convert(T statement);
}
