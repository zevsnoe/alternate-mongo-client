package db.client.contract.mongo;

import db.client.contract.StatementType;

public interface AdoptedStatement {
	String getCollectionName();
	StatementType getType();
}
