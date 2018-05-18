package db.client.mongo.helper;

import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;

public class ExpressionHelper {

	//TODO: consider remove
	public static String toFieldName(Expression e) {
		if (e instanceof StringValue)
			return e.toString();
		if (e instanceof Column)
			return e.toString();
		throw new UnsupportedOperationException("Can't turn [" + e + "] " + e.getClass() + " into field name");
	}

	public static Object toFieldValue(Expression e) {
		if (e instanceof StringValue)
			return ((StringValue) e).getValue();
		else if (e instanceof DoubleValue)
			return ((DoubleValue) e).getValue();
		else if (e instanceof LongValue)
			return ((LongValue) e).getValue();
		else if (e instanceof NullValue)
			return null;
		else if (e instanceof JdbcParameter)
			throw new UnsupportedOperationException("JDBC params not supported");

		throw new UnsupportedOperationException("Unknown type of [" + e + "] " + e.getClass().getName() + " - can't converter to literal");
	}
}
