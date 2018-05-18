package db.client.contract.client;

@FunctionalInterface
public interface Client {
	Object execute(String query);
}
