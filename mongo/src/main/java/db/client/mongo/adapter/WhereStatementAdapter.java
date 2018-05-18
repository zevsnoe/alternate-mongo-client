package db.client.mongo.adapter;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import db.client.mongo.helper.ExpressionHelper;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;

public class WhereStatementAdapter {
	//TODO: visitor
	public BasicDBObject adoptWhereStatement(Expression expression) {
		if (null == expression) return new BasicDBObject();
		if (expression instanceof Parenthesis) {
			expression = ((Parenthesis) expression).getExpression();
		}
		if (expression instanceof AndExpression) {
			return adoptAndExpression((AndExpression) expression);
		}
		if (expression instanceof OrExpression) {
			return adoptOrExpression((OrExpression) expression);
		}
		if (expression instanceof EqualsTo) {
			return adoptEqualsToExpression((EqualsTo) expression);
		}
		if (expression instanceof GreaterThan) {
			return adoptGreaterThenExpression((GreaterThan) expression);
		}
		if (expression instanceof MinorThan) {
			return adoptMinorThenExpression((MinorThan) expression);
		}
		if (expression instanceof GreaterThanEquals) {
			return adoptGreaterThenEqualsExpression((GreaterThanEquals) expression);
		}
		if (expression instanceof MinorThanEquals) {
			return adoptMinorThenEqualsExpression((MinorThanEquals) expression);
		}
		return new BasicDBObject();
	}

	private BasicDBObject adoptEqualsToExpression(EqualsTo expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$eq");
	}

	private BasicDBObject adoptGreaterThenExpression(GreaterThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gt");
	}

	private BasicDBObject adoptMinorThenExpression(MinorThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lt");
	}

	private BasicDBObject adoptGreaterThenEqualsExpression(GreaterThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gte");
	}

	private BasicDBObject adoptMinorThenEqualsExpression(MinorThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lte");
	}

	private BasicDBObject adoptAndExpression(AndExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$and");
	}

	private BasicDBObject adoptOrExpression(OrExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$or");
	}

	private BasicDBObject adoptConditionalExpression(Expression leftExpression, Expression rightExpression, String expressionType) {
		final String field = ExpressionHelper.toFieldName(leftExpression);
		final Object value = ExpressionHelper.toFieldValue(rightExpression);
		final BasicDBObject clause = new BasicDBObject(expressionType, value);
		return new BasicDBObject(field, clause);
	}

	private BasicDBObject adoptCondition(Expression leftExpression, Expression rightExpression, String conditionType) {
		final DBObject leftAdoptedExpression = adoptWhereStatement(leftExpression);
		final DBObject rightAdoptedExpression = adoptWhereStatement(rightExpression);

		final BasicDBList clause = new BasicDBList();
		clause.add(leftAdoptedExpression);
		clause.add(rightAdoptedExpression);
		final BasicDBObject query = new BasicDBObject(conditionType, clause);
		return query;
	}
}
