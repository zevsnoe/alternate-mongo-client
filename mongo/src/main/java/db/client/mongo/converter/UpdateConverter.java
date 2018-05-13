package db.client.mongo.converter;

import db.client.contract.mongo.Converter;
import db.client.mongo.data.AdoptedStatement;
import db.client.mongo.data.UpdateAdoptedStatement;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.List;

import static db.client.mongo.helper.ExpressionHelper.toFieldValue;

public class UpdateConverter implements Converter<Update> {

	public AdoptedStatement convert(Update statement) {
		return new UpdateAdoptedStatement()
				.setValues(convertFromValuesOf(statement))
				.setWhereStatement(statement.getWhere())
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
