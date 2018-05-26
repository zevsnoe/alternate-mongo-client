package db.client.mongo.converter.contract;

import db.client.contract.mongo.QueryConvertedStatement;
import net.sf.jsqlparser.statement.Statement;

@FunctionalInterface
public interface Converter {
	QueryConvertedStatement convert(Statement statement);
}
