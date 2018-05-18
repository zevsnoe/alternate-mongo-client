package db.client.mongo.adapter.dto;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.StatementType;
import org.bson.Document;

import static db.client.contract.StatementType.INSERT;

public class InsertSingleAdoptedStatement extends CollectionNameHolder implements AdoptedStatement {

	private Document document;

	public void setDocument(Document document) {
		this.document = document;
	}

	public Document getDocument() {
		return document;
	}

	@Override
	public StatementType getType() {
		return INSERT;
	}
}
