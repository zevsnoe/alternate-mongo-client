package db.client.mongo.converter.service;

import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.converter.contract.Converter;
import db.client.mongo.converter.contract.DropConverter;
import db.client.mongo.converter.contract.InsertConverter;
import db.client.mongo.converter.contract.SelectConverter;
import db.client.mongo.converter.contract.UpdateConverter;
import db.client.mongo.validator.MongoSQLConverterException;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier
public class VisitorConverter implements Converter {

	private final InsertConverter insertConverter;
	private final UpdateConverter updateConverter;
	private final SelectConverter selectConverter;
	private final DropConverter dropConverter;

	@Autowired
	public VisitorConverter(InsertConverter insertConverter,
							UpdateConverter updateConverter,
							SelectConverter selectConverter,
							DropConverter dropConverter) {
		this.insertConverter = insertConverter;
		this.updateConverter = updateConverter;
		this.selectConverter = selectConverter;
		this.dropConverter = dropConverter;
	}

	@Override
	public QueryConvertedStatement convert(Statement statement) {
		QueryStatementVisitor statementVisitor = new QueryStatementVisitor();
		try {
			statement.accept(statementVisitor);
		} catch (UnsupportedOperationException e){
			throw new MongoSQLConverterException(e.getMessage());
		}

		return statementVisitor.statement;
	}

	private class QueryStatementVisitor implements StatementVisitor {
		private QueryConvertedStatement statement = null;

		@Override
		public void visit(Select select) {
			statement = selectConverter.convert(select);
		}

		@Override
		public void visit(Update update) {
			statement = updateConverter.convert(update);
		}

		@Override
		public void visit(Insert insert) {
			statement = insertConverter.convert(insert);
		}

		@Override
		public void visit(Drop drop) {
			statement = dropConverter.convert(drop);
		}

		public void visit(Delete delete) { throw new UnsupportedOperationException(); }
		public void visit(Replace replace) { throw new UnsupportedOperationException(); }
		public void visit(Truncate truncate) { throw new UnsupportedOperationException(); }
		public void visit(CreateTable createTable) { throw new UnsupportedOperationException(); }
	}

}
