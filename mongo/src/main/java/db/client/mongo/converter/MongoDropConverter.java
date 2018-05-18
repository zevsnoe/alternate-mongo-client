package db.client.mongo.converter;

import db.client.mongo.converter.contract.DropConverter;
import db.client.mongo.converter.dto.ConvertedStatement;
import db.client.mongo.converter.dto.DropConvertedStatement;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.stereotype.Service;

@Service
public class MongoDropConverter implements DropConverter {

	public ConvertedStatement convert(Statement statement) {
		Drop drop = (Drop) statement;
		return new DropConvertedStatement().setCollectionName(fromTableName(drop));
	}

	private String fromTableName(Drop statement) {
		return statement.getName();
	}

}
