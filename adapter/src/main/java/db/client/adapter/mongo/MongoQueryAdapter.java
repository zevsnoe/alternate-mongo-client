package db.client.adapter.mongo;

import db.client.adapter.contract.QueryAdapter;
import db.client.adapter.mongo.converter.DropConverter;
import db.client.adapter.mongo.converter.InsertConverter;
import db.client.adapter.mongo.converter.SelectConverter;
import db.client.adapter.mongo.converter.UpdateConverter;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.validator.InvalidSQLException;
import db.client.adapter.mongo.validator.MongoSQLAdapterException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
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

	public AdoptedStatement adopt(String query) {
		Statement statement = parse(query);

		if (statement instanceof Select) {
			return selectConverter.convert((Select)statement);
		} else if (statement instanceof Insert) {
			return insertConverter.convert((Insert)statement);
		} else if (statement instanceof Update) {
			return updateConverter.convert((Update)statement);
		} else if (statement instanceof Drop) {
			return dropConverter.convert((Drop)statement);
		}
		throw new InvalidSQLException(query);
	}

	Statement parse(String s) throws MongoSQLAdapterException {
		try {
			return parser.parse(new StringReader(s.trim()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidSQLException(s);
		}
	}

}
