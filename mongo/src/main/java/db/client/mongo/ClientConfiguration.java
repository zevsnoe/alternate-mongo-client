package db.client.mongo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
@ComponentScan(basePackages = {"db.client.mongo.converter", //converts query string to adapter friendly data structure
							   "db.client.mongo.adapter",   //adopts converted data to gateway specific data structure
							   "db.client.mongo.gateway",   //db gateway for executing scripts
							   "db.client.mongo.client",    //db client
							  })
@Lazy
public class ClientConfiguration {}
