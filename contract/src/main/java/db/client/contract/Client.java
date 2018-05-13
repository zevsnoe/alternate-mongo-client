package db.client.contract;

@FunctionalInterface
public interface Client {
	Object execute(String query);
}
