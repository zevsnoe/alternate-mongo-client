package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.DeleteAdapter;
import db.client.mongo.adapter.process.where.WhereStatementAdapter;
import db.client.mongo.adapter.statement.DeleteManyAdoptedStatement;
import db.client.mongo.converter.statement.DeleteConvertedStatement;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.springframework.stereotype.Service;

@Service
public class DeleteStatementAdapter extends WhereStatementAdapter implements DeleteAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof DeleteConvertedStatement))
			throw new MongoSQLAdapterException("Wrong statement type - should be " + DeleteConvertedStatement.class);
		DeleteConvertedStatement deleteStatement = (DeleteConvertedStatement) statement;

		DeleteManyAdoptedStatement adoptedStatement = new DeleteManyAdoptedStatement();
		adoptedStatement.setCollectionName(deleteStatement.getCollectionName());
		adoptedStatement.setFilter(adoptWhereStatement(deleteStatement.getWhereStatement()));
		return adoptedStatement;
	}

}
