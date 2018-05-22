package db.client.mongo.gateway.aware;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MongoDBAwaredTest {

	private static final MongoDatabase MONGO_DATABASE = mock(MongoDatabase.class);
	private static final MongoCollection MONGO_COLLECTION = mock(MongoCollection.class);
	public static final String COLLECTION_NAME = "collectionName";

	@Spy
	private MongoDBAwared db;

	@Test(expected = MongoClientException.class)
	public void databaseNotSet() {
		db.getCollection(COLLECTION_NAME);
	}

	@Test(expected = MongoClientException.class)
	public void IllegalCollectionName() {
		mockDB();
		when(MONGO_DATABASE.getCollection(eq(COLLECTION_NAME))).thenThrow(new IllegalArgumentException());
		db.getCollection(COLLECTION_NAME);
	}

	@Test
	public void testGetCollectionTransparency() {
		mockDB();
		when(MONGO_DATABASE.getCollection(eq(COLLECTION_NAME))).thenReturn(MONGO_COLLECTION);
		MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
		Assert.assertEquals(MONGO_COLLECTION, collection);
	}

	private void mockDB() {
		doReturn(MONGO_DATABASE).when(db).getDataBase();
	}



}