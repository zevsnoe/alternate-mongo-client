package db.client.mongo.converter.dto;

import db.client.contract.mongo.QueryConvertedStatement;

public abstract class ConvertedStatement implements QueryConvertedStatement {
	private String collectionName;

	public String getCollectionName() {
		return collectionName;
	}

	public ConvertedStatement setCollectionName(String name) {
		this.collectionName = name;
		return this;
	}

}