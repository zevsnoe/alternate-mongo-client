package db.client.mongo.converter.dto;

import db.client.contract.StatementType;

import static db.client.contract.StatementType.DROP;

public class DropConvertedStatement extends ConvertedStatement {

	@Override
	public StatementType getType() {
		return DROP;
	}

}
