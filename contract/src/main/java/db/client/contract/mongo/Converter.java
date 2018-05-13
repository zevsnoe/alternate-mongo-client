package db.client.contract.mongo;

public interface Converter<T> {
	QueryAdoptedStatement convert(T statement);
}
