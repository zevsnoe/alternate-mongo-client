package db.client.app.contract;

import db.client.app.adapter.mongo.bean.AdoptedStatement;

@FunctionalInterface
public interface Client {
	Object execute(AdoptedStatement adoptedStatement);
}
