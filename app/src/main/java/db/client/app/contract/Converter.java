package db.client.app.contract;

public interface Converter<T> {
	MongoQueryAdoptedStatement convert(T statement);
}
