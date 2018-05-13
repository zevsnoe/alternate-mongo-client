package db.client.mongo.data;

import db.client.contract.mongo.QueryAdoptedStatement;

public class AdoptedStatement implements QueryAdoptedStatement {
	private String collectionName;
	public String getCollectionName() {
		return collectionName;
	}

	public AdoptedStatement setCollectionName(String name) {
		this.collectionName = name;
		return this;
	}

}
