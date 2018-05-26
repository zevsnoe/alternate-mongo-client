package db.client.api.controller;

import db.client.contract.client.Client;
import db.client.contract.client.ClientFactory;
import db.client.contract.client.Interactor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DBClientInteractionControllerTest {

	@InjectMocks
	private DBClientInteractionController controller;

	@Mock
	private Interactor interactor;

	@Mock
	private ClientFactory clientFactory;

	@Mock
	private Client client;

	@Test
	public void verifyInteraction(){
		when(clientFactory.getClient()).thenReturn(client);
		String query = "query";
		controller.execute(query);
		verify(interactor).interactWith(eq(client), eq(query));
	}
}