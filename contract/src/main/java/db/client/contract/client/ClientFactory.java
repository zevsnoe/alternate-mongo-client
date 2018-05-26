package db.client.contract.client;

@FunctionalInterface
public interface ClientFactory {
	Client getClient();
}
