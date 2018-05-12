package db.client.adapter.mongo.bean;

import javafx.util.Pair;

public class WhereAdoptedStatement extends AdoptedStatement{

	private Pair<String, Object> whereStatement;

	public Pair<String, Object> getWhereStatement() {
		return whereStatement;
	}

	public WhereAdoptedStatement setWhereStatement(Pair<String, Object> whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
