package db.client.adapter.mongo.bean;

import java.util.List;

public class SelectAdoptedStatement extends WhereAdoptedStatement {
	private List<String> projections;

	public List<String> getProjections() {
		return projections;
	}

	public SelectAdoptedStatement setProjections(List<String> projections) {
		this.projections = projections;
		return this;
	}

}
