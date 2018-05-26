package db.client.contract.client;

@FunctionalInterface
public interface Interactor {
	QueryExecutionResult interactWith(Client client, String query);
}
