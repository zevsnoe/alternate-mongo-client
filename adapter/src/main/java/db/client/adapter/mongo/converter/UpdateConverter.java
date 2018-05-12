package db.client.adapter.mongo.converter;

import db.client.adapter.contract.Converter;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.adapter.mongo.helper.ExpressionHelper;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.statement.update.Update;

import java.util.ArrayList;
import java.util.List;

import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldName;
import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldValue;

public class UpdateConverter implements Converter<Update> {

	public AdoptedStatement convert(Update statement) {
		Pair<String, Object> whereStatement = convertWhereStatement(statement.getWhere());
		List<Pair<String, Object>> values = new ArrayList<>();

		for (int i = 0; i < statement.getColumns().size(); i++) {
			String k = statement.getColumns().get(i).toString();
			Expression v = (Expression) (statement.getExpressions().get(i));
			values.add(new Pair<>(k.toString(), ExpressionHelper.toFieldValue(v)));
		}

		return new UpdateAdoptedStatement().setValues(values)
				.setWhereStatement(whereStatement)
				.setCollectionName(statement.getTable().getName());
	}

	//TODO: rid of code duplication - same code in SelectAdapter
	Pair<String, Object> convertWhereStatement(Expression e) {
		if (e == null)
			return null;

		if (e instanceof EqualsTo) {
			EqualsTo eq = (EqualsTo) e;
			return new Pair<>(toFieldName(eq.getLeftExpression()), toFieldValue(eq.getRightExpression()));
		} else {
			throw new UnsupportedOperationException("Can't convert: " + e.getClass().getSimpleName() + " operation yet");
		}
	}


}
