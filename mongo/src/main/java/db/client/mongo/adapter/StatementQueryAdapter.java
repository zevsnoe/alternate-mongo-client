package db.client.mongo.adapter;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.Adapter;
import db.client.mongo.adapter.contract.QueryAdapter;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.springframework.stereotype.Component;

@Component
public class StatementQueryAdapter implements QueryAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement convertedStatement) {
		AdoptedStatement adoptedStatement;
		switch(convertedStatement.getType()) {
			case SELECT: adoptedStatement = adoptStatementWith(convertedStatement, new SelectAdapter()); break;
			case INSERT: adoptedStatement = adoptStatementWith(convertedStatement, new InsertAdapter()); break;
			case UPDATE: adoptedStatement = adoptStatementWith(convertedStatement, new UpdateAdapter()); break;
			case DROP: adoptedStatement = adoptStatementWith(convertedStatement, new DropAdapter()); break;
			default: throw new MongoSQLAdapterException("Undefined Statement");
		}
		return adoptedStatement;
	}

	private AdoptedStatement adoptStatementWith(QueryConvertedStatement convertedStatement, Adapter adapter) {
		return adapter.adopt(convertedStatement);
	}

}
