package db.client.contract.client;

@FunctionalInterface
public interface Interactor {
	Object interactWith(Client client, String query);
}
