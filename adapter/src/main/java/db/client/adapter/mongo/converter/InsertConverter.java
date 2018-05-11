package db.client.adapter.mongo.converter;

import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.InsertAdoptedStatement;
import db.client.adapter.mongo.validator.InvalidSQLException;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldValue;

public class InsertConverter {

	public AdoptedStatement convert(Insert statement) {
		List columns = statement.getColumns();
		if (CollectionUtils.isEmpty(columns))
			throw new InvalidSQLException("Could not find columns to insert to");

		if (!(statement.getItemsList() instanceof ExpressionList)) {
			throw new UnsupportedOperationException("Sub selects are not supported");
		}

		String tableName = statement.getTable().getName();
		List<Pair<String, Object>> valuePairs = convertValues(statement, columns);

		return new InsertAdoptedStatement().setValues(valuePairs).setCollectionName(tableName);
	}

	private List<Pair<String, Object>> convertValues(Insert insertStatement, List columns) {
		List values = ((ExpressionList) insertStatement.getItemsList()).getExpressions();

		if (columns.size() != values.size())
			throw new InvalidSQLException("number of values and columns have to match");

		List<Pair<String, Object>> valuePairs = new ArrayList<>();
		for (int i = 0; i < values.size(); i++) {
			valuePairs.add(new Pair<>(columns.get(i).toString(), toFieldValue((Expression) values.get(i))));
		}
		return valuePairs;
	}

}
