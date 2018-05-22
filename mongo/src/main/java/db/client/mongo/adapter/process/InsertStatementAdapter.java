package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.InsertAdapter;
import db.client.mongo.adapter.statement.InsertSingleAdoptedStatement;
import db.client.mongo.converter.statement.InsertConvertedStatement;
import db.client.mongo.validator.MongoClientException;
import javafx.util.Pair;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public class InsertStatementAdapter implements InsertAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof InsertConvertedStatement))
			throw new MongoClientException("Wrong statement type - should be " + InsertConvertedStatement.class);
		InsertSingleAdoptedStatement adoptedStatement = new InsertSingleAdoptedStatement();
		adoptedStatement.setCollectionName(statement.getCollectionName());
		adoptedStatement.setDocument(adoptInsertable((InsertConvertedStatement)statement));
		return adoptedStatement;
	}

	private Document adoptInsertable(InsertConvertedStatement statement) {
		Document doc = new Document();
		for (Pair<String, Object> pair : statement.getValues()) {
			doc.append(pair.getKey(), pair.getValue());
		}
		return doc;
	}
}
