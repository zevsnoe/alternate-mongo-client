package db.client.app.contract;

import db.client.app.adapter.mongo.bean.AdoptedStatement;

@FunctionalInterface
public interface QueryAdapter {
	AdoptedStatement adopt(String query);
}
