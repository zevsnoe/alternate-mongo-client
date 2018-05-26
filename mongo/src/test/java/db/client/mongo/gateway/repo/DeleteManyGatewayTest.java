package db.client.mongo.gateway.repo;

import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.adapter.statement.DeleteManyAdoptedStatement;
import db.client.mongo.gateway.contract.DBAwared;
import db.client.mongo.validator.InvalidStatementException;
import db.client.mongo.validator.MongoClientException;
import db.client.mongo.validator.MongoGatewayException;
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

	@Spy
	DeleteManyAdoptedStatement deleteAdoptedStatement;

	@Test(expected = InvalidStatementException.class)
	public void invalidStatement(){
		gateway.delete(mock(AdoptedStatement.class));
	}

	@Test(expected = MongoGatewayException.class)
	public void clientError(){
		when(deleteAdoptedStatement.getCollectionName()).thenReturn(COLLECTION_NAME);
		when(client.getCollection(eq(COLLECTION_NAME))).thenThrow(new MongoClientException("error"));
		gateway.delete(deleteAdoptedStatement);
	}

}