package db.client.mongo.converter.service;

import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class SelectStatementConverterTest {

	@InjectMocks
	private SelectStatementConverter converter;

	@Spy
	private Select select;

	@Test(expected = MongoSQLConverterException.class)
	public void wrongStatementType() {
		converter.convert(mock(Statement.class));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void notPlainSelect() {
		converter.convert(select);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void fromIsNotTable() {
		PlainSelect plainSelect = mock(PlainSelect.class);
		doReturn(plainSelect).when(select).getSelectBody();
		converter.convert(select);
	}

	@Test
	public void test() {
		PlainSelect plainSelect = mock(PlainSelect.class);
		doReturn(plainSelect).when(select).getSelectBody();
		doReturn(mock(Table.class)).when(plainSelect).getFromItem();
		converter.convert(select);
		verify(plainSelect, times(2)).getFromItem();
		verify(plainSelect).getSelectItems();
		verify(plainSelect).getWhere();
	}

}