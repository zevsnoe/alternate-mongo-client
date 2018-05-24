package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.DropAdapter;
import db.client.mongo.adapter.statement.DropAdoptedStatement;
import db.client.mongo.converter.statement.DropConvertedStatement;
import db.client.mongo.converter.statement.InsertConvertedStatement;
import db.client.mongo.validator.MongoClientException;
import org.springframework.stereotype.Service;

@Service
public class DropStatementAdapter implements DropAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof InsertConvertedStatement))
			throw new MongoClientException("Wrong statement type - should be " + DropConvertedStatement.class);
		DropAdoptedStatement dropAdoptedStatement = new DropAdoptedStatement();
		dropAdoptedStatement.setCollectionName(statement.getCollectionName());
		return dropAdoptedStatement;
	}
}
