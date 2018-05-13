package db.client.app.adapter.mongo.bean;

import net.sf.jsqlparser.expression.Expression;

public class WhereAdoptedStatement extends AdoptedStatement {

	private Expression whereStatement;

	public Expression getWhereStatement() {
		return whereStatement;
	}

	public WhereAdoptedStatement setWhereStatement(Expression whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
