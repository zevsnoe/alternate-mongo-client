package db.client.adapter;

import db.client.adapter.validator.QueryValidationError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class MongoQueryAdapterUnitTest {

	@Test(expected = QueryValidationError.class)
	public void adoptFailed() {
		new MongoQueryAdapter().adopt("SELECT * FROM USER");
	}
}
