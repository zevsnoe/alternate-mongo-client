package db.client.contract.mongo;

@FunctionalInterface
public interface QueryExecutor<T extends QueryAdoptedStatement> {
	Object execute(T statement);
}
