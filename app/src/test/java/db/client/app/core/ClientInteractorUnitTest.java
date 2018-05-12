package db.client.app.core;

import db.client.adapter.mongo.MongoQueryAdapter;
import db.client.app.clients.executor.DropQueryExecutor;
import db.client.app.clients.executor.InsertQueryExecutor;
import db.client.app.clients.executor.SelectQueryExecutor;
import db.client.app.clients.executor.UpdateQueryExecutor;
import db.client.app.clients.mongo.MongoDBClient;
import db.client.app.interactor.validation.InteractionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ClientInteractorUnitTest {
	private DropQueryExecutor dropQueryExecutor;
	private InsertQueryExecutor insertQueryExecutor;
	private UpdateQueryExecutor updateQueryExecutor;
	private SelectQueryExecutor selectQueryExecutor;

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

	private MongoDBClient client;

	@Before
	public void init() {
		client = new MongoDBClient(dropQueryExecutor, insertQueryExecutor, updateQueryExecutor, selectQueryExecutor);
	}

	@Test(expected = InteractionFailedError.class)
	public void interactionFailed() {
		new ClientInteractor(new MongoQueryAdapter()).interactWith(client, "");
	}

}