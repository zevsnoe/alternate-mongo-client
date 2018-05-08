package db.client.app.adapter;

import db.client.app.contract.QueryAdapter;
import org.springframework.stereotype.Component;

@Component
public class MongoQueryAdapter implements QueryAdapter {

	public String adopt(String query) {
		System.out.println("Adopting query to mongo syntax started: " + query);
		return null;
	}

}
