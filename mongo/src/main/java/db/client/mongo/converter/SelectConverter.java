package db.client.mongo.converter;

import db.client.mongo.converter.contract.Converter;
import db.client.mongo.converter.dto.ConvertedStatement;
import db.client.mongo.converter.dto.SelectConvertedStatement;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

import java.util.ArrayList;
import java.util.List;

import static db.client.mongo.helper.ExpressionHelper.toFieldName;

public class SelectConverter implements Converter<Select> {

	public ConvertedStatement convert(Select statement) {
		PlainSelect plainSelect = validateAndGetSelectBody(statement);
		return new SelectConvertedStatement()
				.setProjections(fromFieldsOf(plainSelect))
				.setWhereStatement(plainSelect.getWhere())
				.setCollectionName(fromTableName(plainSelect));
	}

	private PlainSelect validateAndGetSelectBody(Select statement) {
		if (!(statement.getSelectBody() instanceof PlainSelect))
			throw new UnsupportedOperationException("Can handle plain select statements at the moment.");

		PlainSelect plainSelect = (PlainSelect) statement.getSelectBody();
		if (!(plainSelect.getFromItem() instanceof Table))
			throw new UnsupportedOperationException("Can select only from tables at the moment.");

		return plainSelect;
	}

	private List<String> fromFieldsOf(PlainSelect ps) {
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

	private String fromTableName(PlainSelect ps) {
		return ((Table) ps.getFromItem()).getName();
	}

}
