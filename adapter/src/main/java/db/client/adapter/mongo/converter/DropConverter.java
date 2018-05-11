package db.client.adapter.mongo.converter;

import db.client.adapter.mongo.bean.AdoptedStatement;
import net.sf.jsqlparser.statement.drop.Drop;

public class DropConverter {

	public AdoptedStatement convert(Drop statement) {
		return new AdoptedStatement().setCollectionName(statement.getName());
	}

}
