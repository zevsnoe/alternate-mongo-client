package db.client.mongo.adapter;

import db.client.mongo.adapter.contract.Adapter;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.dto.InsertSingleAdoptedStatement;
import db.client.mongo.converter.dto.InsertConvertedStatement;
import db.client.mongo.validator.MongoClientException;
import javafx.util.Pair;
import org.bson.Document;

public class InsertAdapter implements Adapter {

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
