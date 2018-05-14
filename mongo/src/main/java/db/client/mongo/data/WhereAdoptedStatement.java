package db.client.mongo.data;

import com.mongodb.BasicDBObject;

public class WhereAdoptedStatement extends AdoptedStatement {

	private BasicDBObject whereStatement;

	public BasicDBObject getWhereStatement() {
		return whereStatement;
	}

	public WhereAdoptedStatement setWhereStatement(BasicDBObject whereStatement) {
		this.whereStatement = whereStatement;
		return this;
	}
}
