package db.client.mongo.client;

import db.client.mongo.ClientConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClientConfiguration.class)
public class DBConfigTest {

	@Autowired
	private DBConfig config;

	@Test
	public void testConfig() {
		assertNotNull(config.host);
		assertNotNull(config.name);
		assertNotNull(config.port);
	}
}