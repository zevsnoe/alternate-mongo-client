package db.client.adapter.mongo.helper;

import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;

import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldName;
import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldValue;

public class WhereStatementHelper {

	public static Pair<String, Object> from(Expression e) {
		if (e == null)
			return null;

		if (e instanceof EqualsTo) {
			EqualsTo eq = (EqualsTo) e;
			return new Pair<>(toFieldName(eq.getLeftExpression()), toFieldValue(eq.getRightExpression()));
		} else {
			throw new UnsupportedOperationException("Can't adopt: " + e.getClass().getSimpleName() + " operation yet");
		}
	}
}
