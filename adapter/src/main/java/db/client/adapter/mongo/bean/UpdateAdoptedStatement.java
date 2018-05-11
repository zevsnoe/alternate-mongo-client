package db.client.adapter.mongo.bean;

import javafx.util.Pair;

import java.util.List;

public class UpdateAdoptedStatement extends AdoptedStatement {
	private List<Pair<String, Object>> values;
	private Pair<String, Object> whereStatement;

	public List<Pair<String, Object>> getValues() {
		return values;
	}

	public UpdateAdoptedStatement setValues(List<Pair<String, Object>> values) {
		this.values = values;
		return this;
	}

	public Pair<String, Object> getWhereStatement() {
		return whereStatement;
	}

	public UpdateAdoptedStatement setWhereStatement(Pair<String, Object> whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
