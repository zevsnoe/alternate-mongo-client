package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.DropAdapter;
import db.client.mongo.adapter.statement.DropAdoptedStatement;
import org.springframework.stereotype.Service;

@Service
public class DropStatementAdapter implements DropAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		DropAdoptedStatement dropAdoptedStatement = new DropAdoptedStatement();
		dropAdoptedStatement.setCollectionName(statement.getCollectionName());
		return dropAdoptedStatement;
	}
}
