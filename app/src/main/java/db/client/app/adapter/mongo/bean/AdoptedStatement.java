package db.client.app.adapter.mongo.bean;

public class AdoptedStatement {
	private String collectionName;
	public String getCollectionName() {
		return collectionName;
	}

	public AdoptedStatement setCollectionName(String name) {
		this.collectionName = name;
		return this;
	}

}
