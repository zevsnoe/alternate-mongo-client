package db.client.mongo.gateway.repo;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.DeleteManyAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.contract.DeleteGateway;
import db.client.mongo.gateway.result.QueryExecutionResultBuilderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DeleteManyGateway implements DeleteGateway {

	private final DBAwared client;

	@Autowired
	public DeleteManyGateway(DBAwared client) {
		this.client = client;
	}

	@Override
	public QueryExecutionResult delete(AdoptedStatement statement) {
		DeleteManyAdoptedStatement deleteStatement = (DeleteManyAdoptedStatement) statement;
		String collectionName = statement.getCollectionName();
		MongoCollection collection = client.getCollection(collectionName);
		try {
			DeleteResult deleteResult = collection.deleteMany(deleteStatement.getFilter());
			return QueryExecutionResultBuilderFacade.deleteSuccesful(deleteResult);
		} catch(MongoWriteException e) {
			return QueryExecutionResultBuilderFacade.deleteFailed(e);
		} catch(MongoWriteConcernException e) {
			return QueryExecutionResultBuilderFacade.deleteFailed(e);
		} catch(MongoException e) {
			return QueryExecutionResultBuilderFacade.deleteFailed(e);
		} catch (Exception e) {
			return QueryExecutionResultBuilderFacade.deleteFailed(e);
		}

	}

}
