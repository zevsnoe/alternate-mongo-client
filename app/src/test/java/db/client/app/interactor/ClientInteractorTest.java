package db.client.app.interactor;

import db.client.contract.client.Client;
import db.client.contract.client.QueryExecutionResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClientInteractorTest {

	@InjectMocks
	private ClientInteractor interactor;

	@Mock
	private Client client;

	String query = "select * from user";

	@Test
	public void interactionTest() {
		QueryExecutionResult assertionResult = mock(QueryExecutionResult.class);
		when(client.execute(eq(query))).thenReturn(assertionResult);
		Object result = interactor.interactWith(client, query);
		assertEquals(assertionResult, result);
		verify(client).execute(eq(query));
	}

}