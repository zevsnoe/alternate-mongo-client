package db.client.adapter.mongo.bean;

import javafx.util.Pair;

import java.util.List;

public class SelectAdoptedStatement extends AdoptedStatement {
	private List<String> fields;
	private Pair<String, Object> whereStatement;

	public List<String> getFields() {
		return fields;
	}

	public SelectAdoptedStatement setFields(List<String> fields) {
		this.fields = fields;
		return this;
	}

	public Pair<String, Object> getWhereStatement() {
		return whereStatement;
	}

	public SelectAdoptedStatement setWhereStatement(Pair<String, Object> whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
