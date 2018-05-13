package db.client.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"db.client.app", "db.client.mongo"})
public class CoreConfig {}
