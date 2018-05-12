package db.client.adapter.mongo.converter;

import db.client.adapter.contract.Converter;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.List;

import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldValue;
import static db.client.adapter.mongo.helper.WhereStatementHelper.from;

public class UpdateConverter implements Converter<Update> {

	public AdoptedStatement convert(Update statement) {
		return new UpdateAdoptedStatement()
				.setValues(convertFromValuesOf(statement))
				.setWhereStatement(from(statement.getWhere()))
				.setCollectionName(convertFromTableName(statement));
	}

	private List<Pair<String, Object>> convertFromValuesOf(Update statement) {
		List<Pair<String, Object>> values = new ArrayList<>();

		for (int i = 0; i < statement.getColumns().size(); i++) {
			String k = statement.getColumns().get(i).toString();
			Expression v = (Expression) (statement.getExpressions().get(i));
			values.add(new Pair<>(k.toString(), toFieldValue(v)));
		}
		return values;
	}

	private String convertFromTableName(Update statement) {
		return statement.getTable().getName();
	}

}
