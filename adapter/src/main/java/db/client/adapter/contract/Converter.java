package db.client.adapter.contract;

import db.client.adapter.mongo.bean.AdoptedStatement;

public interface Converter<T> {
	AdoptedStatement convert(T statement);
}
