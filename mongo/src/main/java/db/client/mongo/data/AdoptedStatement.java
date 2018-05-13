package db.client.mongo.data;

import db.client.app.contract.MongoQueryAdoptedStatement;

public class AdoptedStatement implements MongoQueryAdoptedStatement {
	private String collectionName;
	public String getCollectionName() {
		return collectionName;
	}

	public AdoptedStatement setCollectionName(String name) {
		this.collectionName = name;
		return this;
	}

}
