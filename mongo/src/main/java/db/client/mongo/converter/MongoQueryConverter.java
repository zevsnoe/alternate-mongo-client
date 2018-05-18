package db.client.mongo.converter;

import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.converter.contract.ConverterService;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoSQLAdapterException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringReader;

@Component
public class MongoQueryConverter implements QueryConverter {

	private final CCJSqlParserManager parser;

	private final ConverterService sevice;

	@Autowired
	public MongoQueryConverter(ConverterService sevice ) {
		this.sevice = sevice;
		this.parser = new CCJSqlParserManager();
	}

	public QueryConvertedStatement convert(String query) {
		Statement statement = parse(query);
		return sevice.convert(statement);
	}

	private Statement parse(String s) throws MongoSQLAdapterException {
		try {
			return parser.parse(new StringReader(s.trim()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvalidSQLException(s);
		}
	}


}