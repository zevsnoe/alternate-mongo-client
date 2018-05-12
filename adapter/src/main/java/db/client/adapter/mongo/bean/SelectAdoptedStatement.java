package db.client.adapter.mongo.bean;

import java.util.List;

public class SelectAdoptedStatement extends WhereAdoptedStatement {
	private List<String> fields;

	public List<String> getFields() {
		return fields;
	}

	public SelectAdoptedStatement setFields(List<String> fields) {
		this.fields = fields;
		return this;
	}

}
