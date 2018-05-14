package db.client.mongo.executor;

import com.mongodb.MongoClientException;
import db.client.mongo.converter.SelectConverter;
import db.client.mongo.data.AdoptedStatement;
import db.client.mongo.data.SelectAdoptedStatement;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.StringReader;

@RunWith(MockitoJUnitRunner.class)
public class SelectQueryExecutorTest {

	@Spy
	private SelectQueryExecutor queryExecutor;

	private SelectConverter converter;
	private CCJSqlParserManager parser;

	@Before
	public void setup() {
		parser = new CCJSqlParserManager();
		converter = new SelectConverter();
	}

	@Test(expected = JSQLParserException.class)
	public void verifyParser() throws JSQLParserException {
		parse("SELECT *");
	}

	@Test(expected = ClassCastException.class)
	public void onlySelectsAllowed() throws JSQLParserException {
		Statement statement = parse("insert into mycollection(name) values('alex')");
		AdoptedStatement adoptedStatement = converter.convert((Select) statement);
		queryExecutor.execute((SelectAdoptedStatement) adoptedStatement);
	}

	@Test(expected = MongoClientException.class)
	public void databaseNotSet() throws JSQLParserException {
		Statement statement = parse("SELECT * from table_name");
		AdoptedStatement convert = converter.convert((Select) statement);
		queryExecutor.execute((SelectAdoptedStatement) convert);
	}

	private Statement parse(String sql) throws JSQLParserException {
		return parser.parse(new StringReader(sql));
	}
}