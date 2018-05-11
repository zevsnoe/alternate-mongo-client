package db.client.app.contract;

public interface Interactor {
	Object interactWith(Client client, String query);
}
