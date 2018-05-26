package db.client.mongo.converter.service;

import db.client.mongo.converter.contract.DeleteConverter;
import db.client.mongo.converter.statement.ConvertedStatement;
import db.client.mongo.converter.statement.DeleteConvertedStatement;
import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springframework.stereotype.Component;

@Component
public class DeleteStatementConverter implements DeleteConverter {

	public ConvertedStatement convert(Statement statement) {
		if (!(statement instanceof Delete)) throw new MongoSQLConverterException("Statement is of wrong type.");
		Delete delete = (Delete) statement;

		return new DeleteConvertedStatement()
				.setWhereStatement(delete.getWhere())
				.setCollectionName(convertFromTableName(delete));
	}

	private String convertFromTableName(Delete statement) {
		return statement.getTable().getName();
	}

}
