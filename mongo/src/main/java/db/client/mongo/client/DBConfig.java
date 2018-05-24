package db.client.mongo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class DBConfig {
	public final String name;
	public final String host;
	public final int port;

	@Autowired
	public DBConfig(@Value("${db.name}") String name,
					@Value("${db.host}") String host,
					@Value("${db.port}") int port) {
		this.name = name;
		this.host = host;
		this.port = port;
	}
}
