package db.client.mongo.adapter.dto;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.StatementType;
import org.bson.conversions.Bson;

import static db.client.contract.StatementType.SELECT;

public class SelectAdoptedStatement extends CollectionNameHolder implements AdoptedStatement {

	private Bson filter;
	private Bson projections;

	public Bson getFilter() {
		return filter;
	}

	public SelectAdoptedStatement setFilter(Bson filter) {
		this.filter = filter;
		return this;
	}

	public Bson getProjections() {
		return projections;
	}

	public void setProjections(Bson projections) {
		this.projections = projections;
	}

	@Override
	public StatementType getType() {
		return SELECT;
	}
}
