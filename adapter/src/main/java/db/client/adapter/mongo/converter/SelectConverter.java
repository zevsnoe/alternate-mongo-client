package db.client.adapter.mongo.converter;

import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.ArrayList;
import java.util.List;

import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldName;
import static db.client.adapter.mongo.helper.ExpressionHelper.toFieldValue;

public class SelectConverter {

	public AdoptedStatement convert(Select statement) {
		if (!(statement.getSelectBody() instanceof PlainSelect))
			throw new UnsupportedOperationException("Can handle plain select statements");

		PlainSelect plainSelect = (PlainSelect) statement.getSelectBody();
		if (!(plainSelect.getFromItem() instanceof Table))
			throw new UnsupportedOperationException("Can select only tables");

		String tableName = convertFromStatement(plainSelect);
		List<String> fields = convertFields(plainSelect);
		Pair<String, Object> whereStatement = convertWhereStatement(plainSelect.getWhere());

		return new SelectAdoptedStatement().setFields(fields)
				.setWhereStatement(whereStatement)
				.setCollectionName(tableName);
	}

	private String convertFromStatement(PlainSelect ps) {
		return ((Table) ps.getFromItem()).getName();
	}

	private List<String> convertFields(PlainSelect ps) {
		List<String> fields = new ArrayList<>();
		for (Object o : ps.getSelectItems()) {
			SelectItem selectItem = (SelectItem) o;
			if (selectItem instanceof AllColumns) {
				if (fields.size() > 0)
					throw new UnsupportedOperationException("Can't select * and fields in the same query");
				break;
			} else if (selectItem instanceof SelectExpressionItem) {
				SelectExpressionItem item = (SelectExpressionItem) selectItem;
				fields.add(toFieldName(item.getExpression()));
			} else {
				throw new UnsupportedOperationException("Unknown select item: " + selectItem.getClass().getSimpleName());
			}
		}
		return fields;
	}

	//TODO: rid of code duplication - same code in SelectAdapter
	Pair<String, Object> convertWhereStatement(Expression e) {
		if (e == null)
			return null;

		if (e instanceof EqualsTo) {
			EqualsTo eq = (EqualsTo) e;
			return new Pair<>(toFieldName(eq.getLeftExpression()), toFieldValue(eq.getRightExpression()));
		} else {
			throw new UnsupportedOperationException("Can't adopt: " + e.getClass().getSimpleName() + " operation yet");
		}
	}

}
