package db.client.contract.client;

@FunctionalInterface
public interface Client {
	QueryExecutionResult execute(String query);
}
