package db.client.mongo.converter.statement;

import db.client.contract.StatementType;

import static db.client.contract.StatementType.DELETE;

public class DeleteConvertedStatement extends WhereConvertedStatement {

	@Override
	public StatementType getType() {
		return DELETE;
	}
}
