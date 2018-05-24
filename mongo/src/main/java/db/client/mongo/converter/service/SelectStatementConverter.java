package db.client.mongo.converter.service;

import db.client.mongo.converter.contract.SelectConverter;
import db.client.mongo.converter.statement.ConvertedStatement;
import db.client.mongo.converter.statement.SelectConvertedStatement;
import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static db.client.mongo.helper.ExpressionHelper.toFieldName;

@Service
public class SelectStatementConverter implements SelectConverter {

	@Override
	public ConvertedStatement convert(Statement statement) {
		if (!(statement instanceof Select))
			throw new MongoSQLConverterException("Statement is of wrong type.");
		Select select = (Select) statement;
		PlainSelect plainSelect = validateAndGetSelectBody(select);

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
			selectItem.accept(new ListSelectItemVisitor(fields));
		}
		return fields;
	}

	private String fromTableName(PlainSelect ps) {
		return ((Table) ps.getFromItem()).getName();
	}

	private class ListSelectItemVisitor implements SelectItemVisitor {
		private final List<String> fields;

		public ListSelectItemVisitor(List<String> fields) {
			this.fields = fields;
		}

		@Override
		public void visit(AllColumns element) {
			if (fields.size() > 0) {
				throw new UnsupportedOperationException("Can't select * and fields in the same query");
			}
		}

		@Override
		public void visit(AllTableColumns element) {
			throw new UnsupportedOperationException("Unknown select item: " + element);
		}

		@Override
		public void visit(SelectExpressionItem element) {
			fields.add(toFieldName(element.getExpression()));
		}
	}
}
