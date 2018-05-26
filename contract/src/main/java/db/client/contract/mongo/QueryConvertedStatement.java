package db.client.contract.mongo;

import db.client.contract.StatementType;

public interface QueryConvertedStatement {
	String getCollectionName();
	StatementType getType();
}
