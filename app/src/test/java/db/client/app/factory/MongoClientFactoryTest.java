package db.client.app.factory;

import db.client.contract.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MongoClientFactoryTest {

	@InjectMocks
	private MongoClientFactory factory;

	@Mock
	private Client client;

	@Test
	public void getClient() {
		assertEquals(client, factory.getClient());
	}
}