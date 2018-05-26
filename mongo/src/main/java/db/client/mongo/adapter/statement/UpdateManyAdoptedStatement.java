package db.client.mongo.adapter.statement;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.StatementType;
import org.bson.conversions.Bson;

import java.util.List;

import static db.client.contract.StatementType.UPDATE;

public class UpdateManyAdoptedStatement extends CollectionNameHolder implements AdoptedStatement {

	private Bson filter;
	private List<Bson> updateElements;

	public void setFilter(Bson filter) {
		this.filter = filter;
	}

	public Bson getFilter() {
		return filter;
	}

	public List<Bson> getUpdateElements() {
		return updateElements;
	}

	public void setUpdateElements(List<Bson> updateElements) {
		this.updateElements = updateElements;
	}

	@Override
	public StatementType getType() {
		return UPDATE;
	}
}
