package db.client.mongo.converter.dto;

import db.client.contract.StatementType;
import javafx.util.Pair;

import java.util.List;

import static db.client.contract.StatementType.UPDATE;

public class UpdateConvertedStatement extends WhereConvertedStatement {
	private List<Pair<String, Object>> values;

	public List<Pair<String, Object>> getValues() {
		return values;
	}

	public UpdateConvertedStatement setValues(List<Pair<String, Object>> values) {
		this.values = values;
		return this;
	}

	@Override
	public StatementType getType() {
		return UPDATE;
	}
}
