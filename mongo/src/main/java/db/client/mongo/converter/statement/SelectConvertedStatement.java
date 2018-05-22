package db.client.mongo.converter.statement;

import db.client.contract.StatementType;

import java.util.List;

import static db.client.contract.StatementType.SELECT;

public class SelectConvertedStatement extends WhereConvertedStatement {
	private List<String> projections;

	public boolean hasIds() {
		return projections.stream().anyMatch("id"::equals);
	}

	public List<String> getProjections() {
		return projections;
	}

	public SelectConvertedStatement setProjections(List<String> projections) {
		this.projections = projections;
		return this;
	}

	@Override
	public StatementType getType() {
		return SELECT;
	}
}
