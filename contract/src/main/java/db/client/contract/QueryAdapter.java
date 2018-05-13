package db.client.contract;

@FunctionalInterface
public interface QueryAdapter {
	MongoQueryAdoptedStatement adopt(String query);
}
