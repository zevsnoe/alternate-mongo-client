package db.client.mongo.helper;

import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ValueExpressionHelperTest {

	@Test(expected = UnsupportedOperationException.class)
	public void notSupported() {
		ExpressionHelper.toFieldValue(mock(Expression.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void stringifyJDBCParameter() {
		JdbcParameter jdbcParameter = mock(JdbcParameter.class);
		Assert.assertNull(ExpressionHelper.toFieldValue(jdbcParameter));
	}

	@Test
	public void stringifyNull() {
		NullValue element = mock(NullValue.class);
		Assert.assertNull(ExpressionHelper.toFieldValue(element));
	}

	@Test
	public void stringifyStringValue() {
		String string = "string";
		StringValue element = mock(StringValue.class);
		when(element.getValue()).thenReturn(string);

		Object result = ExpressionHelper.toFieldValue(element);
		Assert.assertTrue(result instanceof String);
		Assert.assertEquals(string, result.toString());
	}

	@Test
	public void stringifyDouble() {
		Double value = 15.0;
		DoubleValue element = mock(DoubleValue.class);
		when(element.getValue()).thenReturn(value);

		Object result = ExpressionHelper.toFieldValue(element);
		Assert.assertTrue(result instanceof Double);
		Assert.assertEquals(value, result);
	}

	@Test
	public void stringifyLong() {
		Long value = 144444L;
		LongValue element = mock(LongValue.class);
		when(element.getValue()).thenReturn(value);

		Object result = ExpressionHelper.toFieldValue(element);
		Assert.assertTrue(result instanceof Long);
		Assert.assertEquals(value, result);
	}
}