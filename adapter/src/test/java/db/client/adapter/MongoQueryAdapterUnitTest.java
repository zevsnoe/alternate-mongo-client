package db.client.adapter;

import db.client.adapter.mongo.MongoQueryAdapter;
import db.client.adapter.mongo.validator.InvalidSQLException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

@RunWith(BlockJUnit4ClassRunner.class)
public class MongoQueryAdapterUnitTest {

	@Test(expected = InvalidSQLException.class)
	public void adoptFailedForInvalidQuery() {
		new MongoQueryAdapter().adopt("some strange query");
	}
}
