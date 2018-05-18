package db.client.mongo.adapter.where;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import db.client.mongo.helper.ExpressionHelper;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;

public abstract class WhereExpressionAdapter {

	protected BasicDBObject adoptEqualsToExpression(EqualsTo expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$eq");
	}

	protected BasicDBObject adoptNotEqualsToExpression(NotEqualsTo expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$ne");
	}

	protected BasicDBObject adoptGreaterThenExpression(GreaterThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gt");
	}

	protected BasicDBObject adoptMinorThenExpression(MinorThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lt");
	}

	protected BasicDBObject adoptGreaterThenEqualsExpression(GreaterThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gte");
	}

	protected BasicDBObject adoptMinorThenEqualsExpression(MinorThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lte");
	}

	protected BasicDBObject adoptAndExpression(AndExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$and");
	}

	protected BasicDBObject adoptOrExpression(OrExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$or");
	}

	protected BasicDBObject adoptConditionalExpression(Expression leftExpression, Expression rightExpression, String expressionType) {
		final String field = ExpressionHelper.toFieldName(leftExpression);
		final Object value = ExpressionHelper.toFieldValue(rightExpression);
		final BasicDBObject clause = new BasicDBObject(expressionType, value);
		return new BasicDBObject(field, clause);
	}

	protected BasicDBObject adoptCondition(Expression leftExpression, Expression rightExpression, String conditionType) {
		final DBObject leftAdoptedExpression = adoptWhereStatement(leftExpression);
		final DBObject rightAdoptedExpression = adoptWhereStatement(rightExpression);

		final BasicDBList clause = new BasicDBList();
		clause.add(leftAdoptedExpression);
		clause.add(rightAdoptedExpression);
		final BasicDBObject query = new BasicDBObject(conditionType, clause);
		return query;
	}

	abstract DBObject adoptWhereStatement(Expression expression);
}
