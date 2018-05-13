package db.client.mongo.converter;

import db.client.contract.Converter;
import db.client.mongo.data.AdoptedStatement;
import db.client.mongo.data.InsertAdoptedStatement;
import db.client.mongo.helper.ExpressionHelper;
import db.client.mongo.validator.InvalidSQLException;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class InsertConverter implements Converter<Insert> {

	public AdoptedStatement convert(Insert statement) {
		List columns = statement.getColumns();
		validateColumns(statement, columns);

		return new InsertAdoptedStatement()
				.setValues(fromValuesOf(statement, columns))
				.setCollectionName(fromTableName(statement));
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
