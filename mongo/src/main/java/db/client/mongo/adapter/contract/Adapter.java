package db.client.mongo.adapter.contract;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;

@FunctionalInterface
public interface Adapter {
	AdoptedStatement adopt(QueryConvertedStatement statement);
}
