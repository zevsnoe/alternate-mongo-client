package db.client.mongo;

import db.client.contract.client.QueryExecutionResult;
import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.Adapter;
import db.client.mongo.client.DBClient;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.RepositoryService;
import db.client.mongo.validator.InvalidSQLException;
import db.client.mongo.validator.MongoGatewayException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DBClientTest {

	@InjectMocks
	private DBClient dbClient;

	@Mock
	private QueryConverter converter;

	@Mock
	private Adapter adapter;

	@Mock
	private RepositoryService gateway;

	String query = "select * from user";

	@Test(expected = MongoGatewayException.class)
	public void invalidQuery(){
		when(converter.convert(eq(query))).thenThrow(new InvalidSQLException(query));
		dbClient.execute(query);
	}

	@Test(expected = MongoGatewayException.class)
	public void unsupportedOperation(){
		QueryConvertedStatement convertedStatement = mock(QueryConvertedStatement.class);
		when(converter.convert(eq(query))).thenReturn(convertedStatement);
		when(adapter.adopt(eq(convertedStatement))).thenThrow(new UnsupportedOperationException("test"));
		dbClient.execute(query);
	}

	@Test
	public void executionSuccessful(){
		QueryConvertedStatement convertedStatement = mock(QueryConvertedStatement.class);
		AdoptedStatement adoptedStatement = mock(AdoptedStatement.class);
		QueryExecutionResult result = mock(QueryExecutionResult.class);
		when(converter.convert(eq(query))).thenReturn(convertedStatement);
		when(adapter.adopt(eq(convertedStatement))).thenReturn(adoptedStatement);
		when(gateway.execute(eq(adoptedStatement))).thenReturn(result);
		QueryExecutionResult executionResult = dbClient.execute(query);
		Assert.assertEquals(result, executionResult);


		verify(converter).convert(eq(query));
		verify(adapter).adopt(eq(convertedStatement));
		verify(gateway).execute(eq(adoptedStatement));
	}

}