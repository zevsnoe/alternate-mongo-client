package db.client.app.adapter.mongo.helper;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;

public class WhereExpressionAdapter {

	public static BasicDBObject adopt(Expression expression) {
		if (expression instanceof Parenthesis) {
			expression = ((Parenthesis) expression).getExpression();
		}
		if (expression instanceof AndExpression) {
			return parseAndExpression((AndExpression) expression);
		}
		if (expression instanceof OrExpression) {
			return parseOrExpression((OrExpression) expression);
		}
		if (expression instanceof EqualsTo) {
			return parseEqualsToExpression((EqualsTo) expression);
		}
		return new BasicDBObject();
	}

	private static BasicDBObject parseEqualsToExpression(EqualsTo expression) {
		final String field = ExpressionHelper.toFieldName(expression.getLeftExpression());
		final Object value = ExpressionHelper.toFieldValue(expression.getRightExpression());
		final BasicDBObject equalsClause = new BasicDBObject(field, value);
		return equalsClause;
	}

	private static BasicDBObject parseAndExpression(AndExpression expression) {
		return parseCondition(expression.getLeftExpression(), expression.getRightExpression(), "$and");
	}

	private static BasicDBObject parseOrExpression(OrExpression expression) {
		return parseCondition(expression.getLeftExpression(), expression.getRightExpression(), "$or");
	}

	private static BasicDBObject parseCondition(Expression leftExpression, Expression rightExpression, String conditionType) {
		final DBObject leftParsedExpression = adopt(leftExpression);
		final DBObject rightParsedExpression = adopt(rightExpression);

		final BasicDBList clause = new BasicDBList();
		clause.add(leftParsedExpression);
		clause.add(rightParsedExpression);
		final BasicDBObject query = new BasicDBObject(conditionType, clause);
		return query;
	}
}