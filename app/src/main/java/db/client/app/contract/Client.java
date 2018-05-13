package db.client.app.contract;

@FunctionalInterface
public interface Client {
	Object execute(String query);
}
