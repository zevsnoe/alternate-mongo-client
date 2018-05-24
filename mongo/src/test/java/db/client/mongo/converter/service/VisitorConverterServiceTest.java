package db.client.mongo.converter.service;

import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.converter.contract.DropConverter;
import db.client.mongo.converter.contract.InsertConverter;
import db.client.mongo.converter.contract.SelectConverter;
import db.client.mongo.converter.contract.UpdateConverter;
import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.StringReader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class VisitorConverterServiceTest {

	@InjectMocks
	private VisitorConverter converter;

	@Mock
	private SelectConverter selectConverter;

	@Mock
	private InsertConverter insertConverter;

	@Mock
	private UpdateConverter updateConverter;

	@Mock
	private DropConverter dropConverter;

	@Mock
	private QueryConvertedStatement statement;

	@Test
	public void visitSelect() throws JSQLParserException {
		test(selectConverter.convert(any()),
	      "select * from mycollection");
	}

	@Test
	public void visitInsert() throws JSQLParserException {
		test(insertConverter.convert(any()),
		 "insert into mycollection(name, value) values('name', 'value')");
	}

	@Test
	public void visitUpdate() throws JSQLParserException {
		test(updateConverter.convert(any()),
		  "update mycollection set name = 'vasya' where value='5'");
	}

	@Test
	public void visitDrop() throws JSQLParserException {
		test(dropConverter.convert(any()),
		  "drop table mycollection");
	}

	@Test(expected = MongoSQLConverterException.class)
	public void visitCreateNotSupported()  {
		converter.convert(spy(CreateTable.class));
	}

	private void test(QueryConvertedStatement convert, String s) throws JSQLParserException {
		when(convert).thenReturn(statement);
		CCJSqlParserManager parser = new CCJSqlParserManager();
		Statement parsedStatement = parser.parse(new StringReader(s));
		QueryConvertedStatement convertedStatement = converter.convert(parsedStatement);
		Assert.assertEquals(statement, convertedStatement);
	}

}