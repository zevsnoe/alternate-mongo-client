package db.client.mongo.data;

import javafx.util.Pair;

import java.util.List;

public class UpdateAdoptedStatement extends WhereAdoptedStatement {
	private List<Pair<String, Object>> values;

	public List<Pair<String, Object>> getValues() {
		return values;
	}

	public UpdateAdoptedStatement setValues(List<Pair<String, Object>> values) {
		this.values = values;
		return this;
	}

}
