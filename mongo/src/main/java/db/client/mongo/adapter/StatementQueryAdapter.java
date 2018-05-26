package db.client.mongo.adapter;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.Adapter;
import db.client.mongo.adapter.contract.DeleteAdapter;
import db.client.mongo.adapter.contract.DropAdapter;
import db.client.mongo.adapter.contract.InsertAdapter;
import db.client.mongo.adapter.contract.SelectAdapter;
import db.client.mongo.adapter.contract.UpdateAdapter;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier
public class StatementQueryAdapter implements Adapter {

	private final SelectAdapter selectAdapter;
	private final InsertAdapter insertAdapter;
	private final UpdateAdapter updateAdapter;
	private final DeleteAdapter deleteAdapter;
	private final DropAdapter dropAdapter;

	@Autowired
	public StatementQueryAdapter(SelectAdapter selectAdapter,
								 InsertAdapter insertAdapter,
								 UpdateAdapter updateAdapter,
								 DeleteAdapter deleteAdapter,
								 DropAdapter dropAdapter) {
		this.selectAdapter = selectAdapter;
		this.insertAdapter = insertAdapter;
		this.updateAdapter = updateAdapter;
		this.deleteAdapter = deleteAdapter;
		this.dropAdapter = dropAdapter;
	}


	@Override
	public AdoptedStatement adopt(QueryConvertedStatement convertedStatement) {
		AdoptedStatement adoptedStatement;
		switch(convertedStatement.getType()) {
			case SELECT: adoptedStatement = adoptStatementWith(convertedStatement, selectAdapter); break;
			case INSERT: adoptedStatement = adoptStatementWith(convertedStatement, insertAdapter); break;
			case UPDATE: adoptedStatement = adoptStatementWith(convertedStatement, updateAdapter); break;
			case DROP: adoptedStatement = adoptStatementWith(convertedStatement, dropAdapter); break;
			case DELETE: adoptedStatement = adoptStatementWith(convertedStatement, deleteAdapter); break;
			default: throw new MongoSQLAdapterException("Undefined Statement");
		}
		return adoptedStatement;
	}

	private AdoptedStatement adoptStatementWith(QueryConvertedStatement convertedStatement, Adapter adapter) {
		return adapter.adopt(convertedStatement);
	}

}
