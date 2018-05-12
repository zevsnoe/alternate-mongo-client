package db.client.adapter.mongo.converter;

import db.client.adapter.contract.Converter;
import db.client.adapter.mongo.bean.AdoptedStatement;
import net.sf.jsqlparser.statement.drop.Drop;

public class DropConverter implements Converter<Drop> {

	public AdoptedStatement convert(Drop statement) {
		return new AdoptedStatement().setCollectionName(fromTableName(statement));
	}

	private String fromTableName(Drop statement) {
		return statement.getName();
	}

}
