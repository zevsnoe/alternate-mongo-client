package db.client.mongo.adapter;

import db.client.mongo.adapter.contract.Adapter;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.data.UpdateManyAdoptedStatement;
import db.client.mongo.converter.dto.UpdateConvertedStatement;
import db.client.mongo.validator.MongoClientException;
import javafx.util.Pair;
import net.sf.jsqlparser.expression.Expression;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;

public class UpdateAdapter extends WhereStatementAdapter implements Adapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof UpdateConvertedStatement))
			throw new MongoClientException("Wrong statement type - should be " + UpdateConvertedStatement.class);
		UpdateConvertedStatement updateStatement = (UpdateConvertedStatement) statement;

		UpdateManyAdoptedStatement adoptedStatement = new UpdateManyAdoptedStatement();
		adoptedStatement.setCollectionName(updateStatement.getCollectionName());
		adoptedStatement.setUpdateElements(buildUpdateElementsFrom(updateStatement.getValues()));
		adoptedStatement.setFilter(buildFilterFrom(updateStatement.getWhereStatement()));
		return adoptedStatement;
	}

	private List<Bson> buildUpdateElementsFrom(List<Pair<String, Object>> values) {
		List<Bson> updates = new ArrayList<>();
		for (Pair<String, Object> pair : values) {
			updates.add(set(pair.getKey(), pair.getValue()));
		}
		return updates;
	}

	private Bson buildFilterFrom(Expression expression) {
		return adoptWhereStatement(expression);
	}

}
