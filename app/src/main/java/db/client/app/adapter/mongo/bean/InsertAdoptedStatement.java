package db.client.app.adapter.mongo.bean;

import javafx.util.Pair;

import java.util.List;

public class InsertAdoptedStatement extends AdoptedStatement {
	private List<Pair<String, Object>> values;

	public List<Pair<String, Object>> getValues() {
		return values;
	}

	public InsertAdoptedStatement setValues(List<Pair<String, Object>> values) {
		this.values = values;
		return this;
	}
}
