package db.client.mongo.gateway.repo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.gateway.result.success.SelectQueryExecutionResult;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleSelectGatewayTest {

	private static final String COLLECTION_NAME = "test_collection";

	@InjectMocks
	private SimpleSelectGateway gateway;

	@Mock
	private DBAwared client;

	@Mock
	@SuppressWarnings("unchecked")
	private MongoCollection<Document> collection;

	@Spy
	SelectAdoptedStatement selectAdoptedStatement;

	@Test(expected = InvalidStatementException.class)
	public void invalidStatement(){
		gateway.select(mock(AdoptedStatement.class));
	}

	@Test(expected = MongoGatewayException.class)
	public void clientError(){
		when(selectAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		gateway.select(selectAdoptedStatement);
	}

	@Test(expected = MongoGatewayException.class)
	public void invalidResult(){
		when(selectAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		when(client.getCollection(eq(COLLECTION_NAME))).thenReturn(collection);
		gateway.select(selectAdoptedStatement);
	}

	@Test
	public void fullExecution(){
		FindIterable iterable = mock(FindIterable.class);
		MongoCursor cursor = mock(MongoCursor.class);
		when(selectAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		when(selectAdoptedStatement.getFilter()).thenReturn(mock(Bson.class));
		when(selectAdoptedStatement.getProjections()).thenReturn(mock(Bson.class));
		when(client.getCollection(eq(COLLECTION_NAME))).thenReturn(collection);
		when(iterable.projection(any(Bson.class))).thenReturn(iterable);
		when(iterable.iterator()).thenReturn(cursor);
		when(collection.find(any(Bson.class))).thenReturn(iterable);

		QueryExecutionResult select = gateway.select(selectAdoptedStatement);
		Assert.assertTrue(select instanceof SelectQueryExecutionResult);
	}


}