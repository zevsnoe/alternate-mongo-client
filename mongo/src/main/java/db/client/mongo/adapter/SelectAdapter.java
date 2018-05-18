package db.client.mongo.adapter;


import db.client.mongo.adapter.contract.Adapter;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.data.SelectAdoptedStatement;
import db.client.mongo.converter.dto.SelectConvertedStatement;
import db.client.mongo.validator.MongoClientException;
import net.sf.jsqlparser.expression.Expression;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class SelectAdapter extends WhereStatementAdapter implements Adapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof SelectConvertedStatement))
			throw new MongoClientException("Wrong statement type - should be " + SelectConvertedStatement.class);
		SelectConvertedStatement selectStatement = (SelectConvertedStatement) statement;

		SelectAdoptedStatement adoptedStatement = new SelectAdoptedStatement();
		adoptedStatement.setCollectionName(selectStatement.getCollectionName());
		adoptedStatement.setFilter(buildFilterFrom(selectStatement.getWhereStatement()));
		adoptedStatement.setProjections(buildProjectionsFrom(selectStatement));
		return adoptedStatement;
	}

	private Bson buildFilterFrom(Expression whereExpression) {
		return adoptWhereStatement(whereExpression);
	}

	private Bson buildProjectionsFrom(SelectConvertedStatement statement) {
		if (statement.hasIds()) {
			return projectionsWithIds(statement);
		} else {
			return projections(statement);
		}
	}

	public Bson projections(SelectConvertedStatement statement) {
		return fields(include(statement.getProjections()), excludeId());
	}

	public Bson projectionsWithIds(SelectConvertedStatement statement) {
		return fields(include(statement.getProjections()));
	}

}
