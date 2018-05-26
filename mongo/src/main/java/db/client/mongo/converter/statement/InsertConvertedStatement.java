package db.client.mongo.converter.statement;

import db.client.contract.StatementType;
import javafx.util.Pair;

import java.util.List;

import static db.client.contract.StatementType.INSERT;

public class InsertConvertedStatement extends ConvertedStatement {
	private List<Pair<String, Object>> values;

	public List<Pair<String, Object>> getValues() {
		return values;
	}

	public InsertConvertedStatement setValues(List<Pair<String, Object>> values) {
		this.values = values;
		return this;
	}

	@Override
	public StatementType getType() {
		return INSERT;
	}
}
