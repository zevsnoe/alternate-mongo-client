package db.client.mongo.adapter.data;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.StatementType;

import static db.client.contract.StatementType.DROP;

public class DropAdoptedStatement extends CollectionNameHolder implements AdoptedStatement {

	@Override
	public StatementType getType() {
		return DROP;
	}

}
