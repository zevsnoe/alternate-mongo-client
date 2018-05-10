package db.client.app.contract;

@FunctionalInterface
public interface ClientFactoryInterface {
	Client getClient(String dbType);
}
