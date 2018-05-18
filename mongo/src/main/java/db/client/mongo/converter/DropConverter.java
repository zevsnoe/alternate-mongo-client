package db.client.mongo.converter;

import db.client.mongo.converter.contract.Converter;
import db.client.mongo.converter.dto.ConvertedStatement;
import db.client.mongo.converter.dto.DropConvertedStatement;
import net.sf.jsqlparser.statement.drop.Drop;

public class DropConverter implements Converter<Drop> {

	public ConvertedStatement convert(Drop statement) {
		return new DropConvertedStatement().setCollectionName(fromTableName(statement));
	}

	private String fromTableName(Drop statement) {
		return statement.getName();
	}

}
