package db.client.mongo;

import com.mongodb.MongoClientException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import db.client.mongo.client.DBClient;
import db.client.mongo.client.DBConfig;
import db.client.mongo.gateway.aware.MongoDBAwared;
import org.bson.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientConfiguration.class)
public class DatabaseSetupTest {

	public static final String VALUE = "value";
	public static final String NAME = "name";

	@Autowired
	private MongoDBAwared dbAwared;

	@Autowired
	private DBClient dbclient;

	@Autowired
	public DBConfig config;

	@Test
	public void testAwareness() {
		dbAwared.setDataBase();
		try {
			dbAwared.createCollection("test_collection");
		} catch (MongoClientException e) {
			e.printStackTrace();
		}
		MongoCollection<Document> testCollection = dbAwared.getCollection("test_collection");
		testCollection.insertOne(new Document().append(NAME, VALUE));
		MongoCursor<Document> iterator = testCollection.find().iterator();
		while (iterator.hasNext()) {
			Document document = iterator.next();
			Assert.assertEquals(VALUE, document.get(NAME));
		}
		testCollection.drop();
	}

	@Test
	public void testDBClient() {
		dbclient.execute("insert into test_collection("+NAME+", "+VALUE+") values('"+NAME+"', '"+VALUE+"')");
		Object selectResult = dbclient.execute("select * from test_collection");
		dbclient.execute("drop table test_collection");
		List<Document> documents = (List<Document>) selectResult;
		for (Document document : documents) {
			Assert.assertEquals(NAME, document.get(NAME));
			Assert.assertEquals(VALUE, document.get(VALUE));
		}
	}
}
