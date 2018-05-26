package db.client.mongo.gateway.repo;

import com.mongodb.client.MongoCollection;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.DeleteManyAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.result.success.DeleteQueryExecutionResult;
import db.client.mongo.validator.InvalidStatementException;
import db.client.mongo.validator.MongoGatewayException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteManyGatewayTest {

	private static final String COLLECTION_NAME = "test_collection";

	@InjectMocks
	private DeleteManyGateway gateway;

	@Mock
	private DBAwared client;

	@Mock
	@SuppressWarnings("unchecked")
	private MongoCollection<Document> collection;

	@Spy
	DeleteManyAdoptedStatement deleteAdoptedStatement;

	@Test(expected = InvalidStatementException.class)
	public void invalidStatement(){
		gateway.delete(mock(AdoptedStatement.class));
	}

	@Test(expected = MongoGatewayException.class)
	public void clientError(){
		when(deleteAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		gateway.delete(deleteAdoptedStatement);
	}

	@Test
	public void fullExecution(){
		when(deleteAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		when(deleteAdoptedStatement.getFilter()).thenReturn(mock(Bson.class));
		when(client.getCollection(eq(COLLECTION_NAME))).thenReturn(collection);

		QueryExecutionResult delete = gateway.delete(deleteAdoptedStatement);
		Assert.assertTrue(delete instanceof DeleteQueryExecutionResult);
	}

}