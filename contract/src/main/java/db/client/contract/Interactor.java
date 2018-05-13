package db.client.contract;

@FunctionalInterface
public interface Interactor {
	Object interactWith(Client client, String query);
}
