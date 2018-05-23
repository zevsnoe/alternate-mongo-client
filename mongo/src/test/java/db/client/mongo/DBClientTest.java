package db.client.mongo;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.QueryAdapter;
import db.client.mongo.converter.contract.QueryConverter;
import db.client.mongo.gateway.contract.RepositoryService;
import db.client.mongo.gateway.result.QueryExecutionResult;
import db.client.mongo.validator.InvalidSQLException;
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
	private QueryAdapter adapter;

	@Mock
	private RepositoryService gateway;

	String query = "select * from user";

	@Test
	public void invalidQuery(){
		InvalidSQLException e = new InvalidSQLException(query);
		when(converter.convert(eq(query))).thenThrow(e);
		Object executionResult = dbClient.execute(query);
		Assert.assertEquals(QueryExecutionResult.from(e), executionResult.toString());

		verify(converter).convert(eq(query));
		verify(adapter, times(0)).adopt(any());
		verify(gateway, times(0)).execute(any());
	}

	@Test
	public void unsupportedOperation(){
		UnsupportedOperationException e = new UnsupportedOperationException();
		QueryConvertedStatement convertedStatement = mock(QueryConvertedStatement.class);
		when(converter.convert(eq(query))).thenReturn(convertedStatement);
		when(adapter.adopt(eq(convertedStatement))).thenThrow(e);
		Object executionResult = dbClient.execute(query);
		Assert.assertEquals(QueryExecutionResult.from(e), executionResult.toString());

		verify(converter).convert(eq(query));
		verify(adapter).adopt(eq(convertedStatement));
		verify(gateway, times(0)).execute(any());
	}

	@Test
	public void executionSuccessful(){
		QueryConvertedStatement convertedStatement = mock(QueryConvertedStatement.class);
		AdoptedStatement adoptedStatement = mock(AdoptedStatement.class);
		Object result = mock(Object.class);
		when(converter.convert(eq(query))).thenReturn(convertedStatement);
		when(adapter.adopt(eq(convertedStatement))).thenReturn(adoptedStatement);
		when(gateway.execute(eq(adoptedStatement))).thenReturn(result);
		Object executionResult = dbClient.execute(query);
		Assert.assertEquals(result, executionResult);


		verify(converter).convert(eq(query));
		verify(adapter).adopt(eq(convertedStatement));
		verify(gateway).execute(eq(adoptedStatement));
	}

}