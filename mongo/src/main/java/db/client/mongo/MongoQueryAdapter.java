package db.client.mongo;

import db.client.contract.mongo.QueryAdapter;
import db.client.contract.mongo.QueryAdoptedStatement;
import db.client.mongo.converter.DropConverter;
import db.client.mongo.converter.InsertConverter;
import db.client.mongo.converter.SelectConverter;
import db.client.mongo.converter.UpdateConverter;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoSQLAdapterException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class MongoQueryAdapter implements QueryAdapter {

	private final CCJSqlParserManager parser = new CCJSqlParserManager();
	private final SelectConverter selectConverter = new SelectConverter();
	private final InsertConverter insertConverter = new InsertConverter();
	private final UpdateConverter updateConverter = new UpdateConverter();
	private final DropConverter dropConverter = new DropConverter();

	public QueryAdoptedStatement adopt(String query) {
		Statement statement = parse(query);
		MongoStatementVisitor statementVisitor = new MongoStatementVisitor();
		try {
			statement.accept(statementVisitor);
		} catch (Exception e){
			e.printStackTrace();
			throw new MongoSQLAdapterException("Can't adopt due to internal error");
		}

		return statementVisitor.statement;
	}

	private Statement parse(String s) throws MongoSQLAdapterException {
		try {
			return parser.parse(new StringReader(s.trim()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidSQLException(s);
		}
	}

	//TODO: consider transactional aspect & extraction
	private class MongoStatementVisitor implements StatementVisitor {
		private QueryAdoptedStatement statement = null;

		@Override
		public void visit(Select select) {
			statement = selectConverter.convert(select);
		}

		@Override
		public void visit(Update update) {
			statement = updateConverter.convert(update);
		}

		@Override
		public void visit(Insert insert) {
			statement = insertConverter.convert(insert);
		}

		@Override
		public void visit(Drop drop) {
			statement = dropConverter.convert(drop);
		}

		public void visit(Delete delete) { throw new UnsupportedOperationException(); }
		public void visit(Replace replace) { throw new UnsupportedOperationException(); }
		public void visit(Truncate truncate) { throw new UnsupportedOperationException(); }
		public void visit(CreateTable createTable) { throw new UnsupportedOperationException(); }
	}

}
