package db.client.app.core;

import db.client.adapter.mongo.MongoQueryAdapter;
import db.client.app.clients.mongo.MongoDBClient;
import db.client.app.interactor.validation.InteractionFailedError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ClientInteractorUnitTest {

	/*DB db;
	private MongoClient mongoClient;

	@Before
	public void init() {
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("mydb");
	}

	@After
	public void after() {
		mongoClient.close();
	}*/

	@Test(expected = InteractionFailedError.class)
	public void interactionFailed() {
		new ClientInteractor(new MongoQueryAdapter()).interactWith(new MongoDBClient(), "");
	}

}