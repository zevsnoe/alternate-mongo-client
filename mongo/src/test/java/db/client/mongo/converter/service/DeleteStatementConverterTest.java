package db.client.mongo.converter.service;

import db.client.contract.StatementType;
import db.client.mongo.converter.statement.ConvertedStatement;
import db.client.mongo.converter.statement.DeleteConvertedStatement;
import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class DeleteStatementConverterTest {

	public static final String COLLECTION = "test_collection";
	@InjectMocks
	private DeleteStatementConverter converter;

	@Spy
	private Delete delete;

	@Test(expected = MongoSQLConverterException.class)
	public void wrongStatementType() {
		converter.convert(mock(Statement.class));
	}

	@Test
	public void verifyConverter() {
		Table table = mock(Table.class);
		Expression expression = mock(Expression.class);
		doReturn(table).when(delete).getTable();
		doReturn(expression).when(delete).getWhere();
		when(table.getName()).thenReturn(COLLECTION);
		ConvertedStatement statement = converter.convert(delete);
		Assert.assertTrue(statement instanceof DeleteConvertedStatement);
		DeleteConvertedStatement deleteStatement = (DeleteConvertedStatement) statement;
		Assert.assertEquals(StatementType.DELETE, deleteStatement.getType());
		Assert.assertEquals(expression, deleteStatement.getWhereStatement());
		Assert.assertEquals(COLLECTION, deleteStatement.getCollectionName());
		verify(delete).getWhere();
		verify(table).getName();
	}

}