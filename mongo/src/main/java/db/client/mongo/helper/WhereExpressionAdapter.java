package db.client.mongo.helper;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;

//TODO: refactor with visitor
public class WhereExpressionAdapter {

	public static BasicDBObject adopt(Expression expression) {
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

	private static BasicDBObject adoptEqualsToExpression(EqualsTo expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$eq");
	}

	private static BasicDBObject adoptGreaterThenExpression(GreaterThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gt");
	}

	private static BasicDBObject adoptMinorThenExpression(MinorThan expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lt");
	}

	private static BasicDBObject adoptGreaterThenEqualsExpression(GreaterThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$gte");
	}

	private static BasicDBObject adoptMinorThenEqualsExpression(MinorThanEquals expression) {
		return adoptConditionalExpression(expression.getLeftExpression(), expression.getRightExpression(), "$lte");
	}

	private static BasicDBObject adoptAndExpression(AndExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$and");
	}

	private static BasicDBObject adoptOrExpression(OrExpression expression) {
		return adoptCondition(expression.getLeftExpression(), expression.getRightExpression(), "$or");
	}

	private static BasicDBObject adoptConditionalExpression(Expression leftExpression, Expression rightExpression, String expressionType) {
		final String field = ExpressionHelper.toFieldName(leftExpression);
		final Object value = ExpressionHelper.toFieldValue(rightExpression);
		final BasicDBObject clause = new BasicDBObject(expressionType, value);
		return new BasicDBObject(field, clause);
	}

	private static BasicDBObject adoptCondition(Expression leftExpression, Expression rightExpression, String conditionType) {
		final DBObject leftAdoptedExpression = adopt(leftExpression);
		final DBObject rightAdoptedExpression = adopt(rightExpression);

		final BasicDBList clause = new BasicDBList();
		clause.add(leftAdoptedExpression);
		clause.add(rightAdoptedExpression);
		final BasicDBObject query = new BasicDBObject(conditionType, clause);
		return query;
	}
}