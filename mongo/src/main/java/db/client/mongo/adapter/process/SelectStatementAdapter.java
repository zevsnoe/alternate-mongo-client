package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.SelectAdapter;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.adapter.process.where.WhereStatementAdapter;
import db.client.mongo.converter.statement.SelectConvertedStatement;
import db.client.mongo.validator.MongoSQLAdapterException;
import net.sf.jsqlparser.expression.Expression;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

@Service
public class SelectStatementAdapter extends WhereStatementAdapter implements SelectAdapter {

	@Override
	public AdoptedStatement adopt(QueryConvertedStatement statement) {
		if (!(statement instanceof SelectConvertedStatement))
			throw new MongoSQLAdapterException("Wrong statement type - should be " + SelectConvertedStatement.class);
		SelectConvertedStatement selectStatement = (SelectConvertedStatement) statement;

		SelectAdoptedStatement adoptedStatement = new SelectAdoptedStatement();
		adoptedStatement.setCollectionName(selectStatement.getCollectionName());
		adoptedStatement.setFilter(buildFilterFrom(selectStatement.getWhereStatement()));
		adoptedStatement.setProjections(buildProjectionsFrom(selectStatement));
		return adoptedStatement;
	}

	Bson buildFilterFrom(Expression whereExpression) {
		return adoptWhereStatement(whereExpression);
	}

	Bson buildProjectionsFrom(SelectConvertedStatement statement) {
		if (statement.hasIds()) {
			return projectionsWithIds(statement);
		} else {
			return projections(statement);
		}
	}

	private Bson projections(SelectConvertedStatement statement) {
		return fields(include(statement.getProjections()), excludeId());
	}

	private Bson projectionsWithIds(SelectConvertedStatement statement) {
		return fields(include(statement.getProjections()));
	}

}
