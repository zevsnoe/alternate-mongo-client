package db.client.app.core;

import db.client.adapter.MongoQueryAdapter;
import db.client.app.contract.Client;
import db.client.app.interactor.validation.InteractionFailedError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class ClientInteractorUnitTest {

	@Test(expected = InteractionFailedError.class)
	public void interactionFailed() {
		new ClientInteractor(new MongoQueryAdapter()).interactWith(new Client(){}, "");
	}
}