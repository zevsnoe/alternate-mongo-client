package db.client.app.contract;

@FunctionalInterface
public interface Interactor {
	Object interactWith(Client client, String query);
}
