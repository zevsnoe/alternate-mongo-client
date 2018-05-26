package db.client.mongo.helper;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NameExpressionHelperTest {

	@Test(expected = UnsupportedOperationException.class)
	public void notSupported() {
		ExpressionHelper.toFieldName(mock(Expression.class));
	}

	@Test
	public void stringifyStringValue() {
		StringValue element = mock(StringValue.class);
		String string = "string";
		when(element.toString()).thenReturn(string);
		String result = ExpressionHelper.toFieldName(element);
		Assert.assertEquals(string, result);
	}

	@Test
	public void stringifyColumn() {
		Column element = mock(Column.class);
		String column = "column";
		when(element.toString()).thenReturn(column);
		String result = ExpressionHelper.toFieldName(element);
		Assert.assertEquals(column, result);
	}
}