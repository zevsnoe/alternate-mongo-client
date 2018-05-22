package db.client.mongo.converter.statement;

import net.sf.jsqlparser.expression.Expression;

public abstract class WhereConvertedStatement extends ConvertedStatement {
	private Expression whereStatement;

	public Expression getWhereStatement() {
		return whereStatement;
	}

	public WhereConvertedStatement setWhereStatement(Expression whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
