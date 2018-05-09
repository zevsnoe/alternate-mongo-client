package db.client.adapter;

@FunctionalInterface
public interface QueryAdapter {
	String adopt(String query);
}
