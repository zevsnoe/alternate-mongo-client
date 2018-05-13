package db.client.mongo.converter;

import db.client.contract.Converter;
import db.client.mongo.data.AdoptedStatement;
import net.sf.jsqlparser.statement.drop.Drop;

public class DropConverter implements Converter<Drop> {

	public AdoptedStatement convert(Drop statement) {
		return new AdoptedStatement().setCollectionName(fromTableName(statement));
	}

	private String fromTableName(Drop statement) {
		return statement.getName();
	}

}
