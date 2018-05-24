package db.client.mongo.gateway.repo;

import com.mongodb.client.MongoCollection;
import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.validator.InvalidStatementException;
import db.client.mongo.validator.MongoGatewayException;
import org.bson.Document;
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
	public void invalidResult(){
		when(selectAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		when(client.getCollection(eq(COLLECTION_NAME))).thenReturn(collection);
		gateway.select(selectAdoptedStatement);
	}

	//...

}