package db.client.mongo.converter;

import db.client.mongo.converter.contract.ConverterService;
import db.client.mongo.validator.InvalidSQLException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConverterWithParserTest {

	@InjectMocks
	private ConverterWithParser converter;

	@Mock
	private ConverterService service;

	@Captor
	private ArgumentCaptor<Statement> statementCaptor;

	public static final String TABLE_NAME = "user";
	public static final String COLUMN_NAME = "email";
	public static final String EMAIL = "email@mail.com";

	@Test(expected = InvalidSQLException.class)
	public void queryIsInvalid() {
		converter.convert("select *");
	}

	@Test
	public void selectAllQuery() {
		converter.convert("select * from " + TABLE_NAME);
		verify(service).convert(statementCaptor.capture());
		Statement statement = statementCaptor.getValue();
		Assert.assertTrue(statement instanceof Select);

		Select select = (Select) statement;
		SelectBody selectBody = select.getSelectBody();

		Assert.assertTrue(selectBody instanceof PlainSelect);
		PlainSelect plainSelect = (PlainSelect) selectBody;
		Assert.assertNull(plainSelect.getWhere());

		FromItem fromItem = plainSelect.getFromItem();
		Assert.assertTrue(fromItem instanceof Table);

		Table table = (Table) fromItem;
		Assert.assertEquals(TABLE_NAME, table.getName());

		List selectItems = plainSelect.getSelectItems();
		Object selectItem = selectItems.get(0);
		Assert.assertTrue(selectItem instanceof AllColumns);
		Assert.assertNotNull(selectItem);
	}

	@Test
	public void selectNameFromUserWithWhere() {
		converter.convert("select name from " + TABLE_NAME + " where " + COLUMN_NAME + " = '" + EMAIL + "'");
		verify(service).convert(statementCaptor.capture());
		Statement statement = statementCaptor.getValue();
		Select select = (Select) statement;
		PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
		Expression where = plainSelect.getWhere();
		Assert.assertNotNull(where);
		Assert.assertTrue(where instanceof EqualsTo);
		EqualsTo equalsTo = (EqualsTo) where;
		Column leftExpression = (Column) equalsTo.getLeftExpression();
		StringValue rightExpression = (StringValue) equalsTo.getRightExpression();

		Assert.assertEquals(COLUMN_NAME, leftExpression.getColumnName());
		Assert.assertEquals(EMAIL, rightExpression.getValue());
	}

}