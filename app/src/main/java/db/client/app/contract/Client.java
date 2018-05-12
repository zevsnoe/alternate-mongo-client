package db.client.app.contract;

import db.client.adapter.mongo.bean.AdoptedStatement;

@FunctionalInterface
public interface Client {
	Object execute(AdoptedStatement adoptedStatement);
}
