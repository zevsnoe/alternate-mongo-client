package db.client.mongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackages = {"db.client.mongo.converter",
							   "db.client.mongo.adapter",
							   "db.client.mongo.gateway",
							   "db.client.mongo.client",
							  })
@Lazy
public class ClientConfiguration {}
