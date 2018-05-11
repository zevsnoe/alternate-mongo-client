package db.client.adapter.contract;

import db.client.adapter.mongo.bean.AdoptedStatement;

@FunctionalInterface
public interface QueryAdapter {
	AdoptedStatement adopt(String query);
}
