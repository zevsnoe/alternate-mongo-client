package db.client.mongo.adapter.statement;

import db.client.contract.StatementType;
import db.client.contract.mongo.AdoptedStatement;
import org.bson.conversions.Bson;

import static db.client.contract.StatementType.DELETE;

public class DeleteManyAdoptedStatement extends CollectionNameHolder implements AdoptedStatement {

	private Bson filter;

	public void setFilter(Bson filter) {
		this.filter = filter;
	}

	public Bson getFilter() {
		return filter;
	}

	@Override
	public StatementType getType() {
		return DELETE;
	}
}
