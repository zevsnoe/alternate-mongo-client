package db.client.app.contract;

@FunctionalInterface
public interface QueryAdapter {
	String adopt(String query);
}
