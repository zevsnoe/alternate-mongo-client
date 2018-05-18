package db.client.mongo.adapter;


import db.client.mongo.adapter.contract.Adapter;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.dto.DropAdoptedStatement;

public class DropAdapter implements Adapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		DropAdoptedStatement dropAdoptedStatement = new DropAdoptedStatement();
		dropAdoptedStatement.setCollectionName(statement.getCollectionName());
		return dropAdoptedStatement;
	}
}
