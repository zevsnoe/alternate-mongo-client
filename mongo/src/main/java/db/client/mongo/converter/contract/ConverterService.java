package db.client.mongo.converter.contract;

import db.client.contract.mongo.QueryConvertedStatement;
import net.sf.jsqlparser.statement.Statement;

public interface ConverterService {
	QueryConvertedStatement convert(Statement statement);
}
