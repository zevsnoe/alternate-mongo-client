package db.client.app.core;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import db.client.adapter.MongoQueryAdapter;
import db.client.app.contract.Client;
import db.client.app.interactor.validation.InteractionFailedError;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.update.Update;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.List;

@RunWith(BlockJUnit4ClassRunner.class)
public class ClientInteractorUnitTest {

	static final boolean DISPLAY_QUERY = true;

	DB db;
	private MongoClient mongoClient;

	@Before
	public void init() {
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("mydb");
	}

	@After
	public void after() {
		mongoClient.close();
	}

	@Test(expected = InteractionFailedError.class)
	public void interactionFailed() {
		new ClientInteractor(new MongoQueryAdapter()).interactWith(new Client() {
		}, "");
	}

	@Test
	public void mongoSelect() throws MongoSQLException {
		String sql = "select value, name from mycollection order by 1 desc";
		Statement statement = parse(sql);
		DBCursor cursor = selectOperation(statement);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	@Test
	public void mongoInsert() throws MongoSQLException {
		String sql = "insert into mycollection(value, name) VALUES('second', 'vasya')";
		Statement statement = parse(sql);
		WriteResult writeop = writeOperation(statement);
		System.out.println(writeop);
	}


	//PARSER:
	Statement parse(String s) throws MongoSQLException {
		s = s.trim();
		try {
			return (new CCJSqlParserManager()).parse(new StringReader(s));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidSQL(s);
		}
	}

	DBObject parseWhere(Expression e) {
		BasicDBObject o = new BasicDBObject();
		if (e == null)
			return o;

		if (e instanceof EqualsTo) {
			EqualsTo eq = (EqualsTo) e;
			o.put(toFieldName(eq.getLeftExpression()), toDBLiteral(eq.getRightExpression()));
		} else {
			throw new UnsupportedOperationException("can't handle: " + e.getClass() + " yet");
		}

		return o;
	}

	////////////////////////////////////////////////
	//COMMON-HELPERS {to parsers and operations}://
	///////////////////////////////////////////////
	String toFieldName(Expression e) {
		if (e instanceof StringValue)
			return e.toString();
		if (e instanceof Column)
			return e.toString();
		throw new UnsupportedOperationException("can't turn [" + e + "] " + e.getClass() + " into field name");
	}

	Object toDBLiteral(Expression e) {
		if (e instanceof StringValue)
			return ((StringValue) e).getValue();
		else if (e instanceof DoubleValue)
			return ((DoubleValue) e).getValue();
		else if (e instanceof LongValue)
			return ((LongValue) e).getValue();
		else if (e instanceof NullValue)
			return null;
		else if (e instanceof JdbcParameter)
			throw new UnsupportedOperationException("jdbc params not supported yet ");

		throw new UnsupportedOperationException("Unknown type of [" + e + "] " + e.getClass().getName() + " - can't convert to literal");
	}

	///////////
	//SELECT//
	//////////
	private DBCursor selectOperation(Statement statement) {
		//collection
		if (!(statement instanceof Select))
			throw new IllegalArgumentException("not a query sql statement");

		Select select = (Select) statement;
		if (!(select.getSelectBody() instanceof PlainSelect))
			throw new UnsupportedOperationException("can only handle PlainSelect so far");

		PlainSelect ps = (PlainSelect) select.getSelectBody();
		if (!(ps.getFromItem() instanceof Table))
			throw new UnsupportedOperationException("can only handle regular tables");

		DBCollection coll = db.getCollection(((Table) ps.getFromItem()).getName());

		//fields
		BasicDBObject fields = new BasicDBObject();
		for (Object o : ps.getSelectItems()) {
			SelectItem si = (SelectItem) o;
			if (si instanceof AllColumns) {
				if (fields.size() > 0)
					throw new UnsupportedOperationException("can't have * and fields");
				break;
			} else if (si instanceof SelectExpressionItem) {
				SelectExpressionItem sei = (SelectExpressionItem) si;
				fields.put(toFieldName(sei.getExpression()), 1);
			} else {
				throw new UnsupportedOperationException("unknown select item: " + si.getClass());
			}
		}

		// where
		DBObject query = parseWhere(ps.getWhere());

		//log, build DBCursor
		if (DISPLAY_QUERY) System.out.println("\t" + "table: " + coll);
		if (DISPLAY_QUERY) System.out.println("\t" + "fields: " + fields);
		if (DISPLAY_QUERY) System.out.println("\t" + "query : " + query);
		DBCursor cursor = coll.find(query, fields);

		{//order by
			List orderBylist = ps.getOrderByElements();
			if (orderBylist != null && orderBylist.size() > 0) {
				DBObject order = new BasicDBObject();
				for (int i = 0; i < orderBylist.size(); i++) {
					OrderByElement orderByElement = (OrderByElement) orderBylist.get(i);
					order.put(orderByElement.toString(), orderByElement.isAsc() ? 1 : -1);
				}
				cursor.sort(order);
			}
		}
		return cursor;
	}

	///////////
	//WRITE//
	//////////
	private WriteResult writeOperation(Statement statement) throws MongoSQLException {
		if (statement instanceof Insert)
			return insert((Insert) statement);
		else if (statement instanceof Update)
			return update((Update) statement);
		else if (statement instanceof Drop)
			//TODO: seems wrong, but will be back here later
			try {
				((Drop) statement).getParameters();
				drop((Drop) statement);
				return new WriteResult(((Drop) statement).getParameters().size(), false, null);
			} catch (MongoException e) {
				return new WriteResult(0, false, null);
			}

		throw new RuntimeException("Unknown write operation: " + statement.getClass());
	}

	WriteResult insert(Insert in) throws MongoSQLException {
		if (in.getColumns() == null)
			throw new InvalidSQL("have to give column names to insert");

		DBCollection coll = db.getCollection(in.getTable().getName());
		if (DISPLAY_QUERY) System.out.println("\t" + "table: " + coll);

		if (!(in.getItemsList() instanceof ExpressionList))
			throw new UnsupportedOperationException("need ExpressionList");

		BasicDBObject o = new BasicDBObject();

		List valueList = ((ExpressionList) in.getItemsList()).getExpressions();
		if (in.getColumns().size() != valueList.size())
			throw new InvalidSQL("number of values and columns have to match");

		for (int i = 0; i < valueList.size(); i++) {
			o.put(in.getColumns().get(i).toString(), toDBLiteral((Expression) valueList.get(i)));

		}

		return coll.insert(o);
	}

	WriteResult update(Update up) throws MongoSQLException {
		DBObject query = parseWhere(up.getWhere());
		BasicDBObject set = new BasicDBObject();

		for (int i = 0; i < up.getColumns().size(); i++) {
			String k = up.getColumns().get(i).toString();
			Expression v = (Expression) (up.getExpressions().get(i));
			set.put(k.toString(), toDBLiteral(v));
		}

		DBObject mod = new BasicDBObject("$set", set);

		DBCollection coll = db.getCollection(up.getTable().getName());
		WriteResult update = coll.update(query, mod);
		return update;
	}

	void drop(Drop d) {
		DBCollection c = db.getCollection(d.getName());
		c.drop();
	}

	public class MongoSQLException extends SQLException {
		public MongoSQLException(String msg) {
			super(msg);
		}
	}

	private class InvalidSQL extends MongoSQLException {
		public InvalidSQL(String sql) {
			super("Invalid sql: " + sql);
		}
	}

}