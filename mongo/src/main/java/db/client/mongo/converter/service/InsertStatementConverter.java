package db.client.mongo.converter.service;

import db.client.mongo.converter.contract.InsertConverter;
import db.client.mongo.converter.statement.ConvertedStatement;
import db.client.mongo.converter.statement.InsertConvertedStatement;
import db.client.mongo.helper.ExpressionHelper;
import db.client.mongo.validator.InvalidSQLException;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class InsertStatementConverter implements InsertConverter {

	@Override
	public ConvertedStatement convert(Statement statement) {
		Insert insertStatement = (Insert) statement;
		List columns = insertStatement.getColumns();
		validateColumns(insertStatement, columns);

		return new InsertConvertedStatement()
				.setValues(fromValuesOf(insertStatement, columns))
				.setCollectionName(fromTableName(insertStatement));
	}

	private void validateColumns(Insert statement, List columns) {
		if (CollectionUtils.isEmpty(columns))
			throw new InvalidSQLException("Could not find columns to insert to");

		if (!(statement.getItemsList() instanceof ExpressionList)) {
			throw new UnsupportedOperationException("Sub selects are not supported");
		}
	}

	private List<Pair<String, Object>> fromValuesOf(Insert insertStatement, List columns) {
		List values = ((ExpressionList) insertStatement.getItemsList()).getExpressions();

		if (columns.size() != values.size())
			throw new InvalidSQLException("number of values and columns have to match");

		List<Pair<String, Object>> valuePairs = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			valuePairs.add(new Pair<>(columns.get(i).toString(), ExpressionHelper.toFieldValue((Expression) values.get(i))));
		}
		return valuePairs;
	}

	private String fromTableName(Insert statement) {
		return statement.getTable().getName();
	}

}
