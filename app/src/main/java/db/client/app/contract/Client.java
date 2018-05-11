package db.client.app.contract;

import db.client.adapter.mongo.bean.AdoptedStatement;

public interface Client {
	Object execute(AdoptedStatement adoptedStatement);
}
