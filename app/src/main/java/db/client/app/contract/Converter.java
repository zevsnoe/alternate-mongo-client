package db.client.app.contract;

import db.client.app.adapter.mongo.bean.AdoptedStatement;

public interface Converter<T> {
	AdoptedStatement convert(T statement);
}
