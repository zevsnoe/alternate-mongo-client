package db.client.contract;

public interface Converter<T> {
	MongoQueryAdoptedStatement convert(T statement);
}
