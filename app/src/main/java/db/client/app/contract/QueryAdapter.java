package db.client.app.contract;

@FunctionalInterface
public interface QueryAdapter {
	MongoQueryAdoptedStatement adopt(String query);
}
