package db.client.contract.mongo;

@FunctionalInterface
public interface QueryAdapter {
	QueryAdoptedStatement adopt(String query);
}
