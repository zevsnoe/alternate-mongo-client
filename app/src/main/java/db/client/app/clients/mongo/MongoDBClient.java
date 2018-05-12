package db.client.app.clients.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import db.client.adapter.mongo.bean.AdoptedStatement;
import db.client.adapter.mongo.bean.DropAdoptedStatement;
import db.client.adapter.mongo.bean.InsertAdoptedStatement;
import db.client.adapter.mongo.bean.SelectAdoptedStatement;
import db.client.adapter.mongo.bean.UpdateAdoptedStatement;
import db.client.adapter.mongo.validator.MongoSQLAdapterException;
import db.client.app.contract.Client;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class MongoDBClient implements Client {

	MongoClient mongoClient;
	DB db;

	//TODO: pass from properties
	@PostConstruct
	public void init() {
		mongoClient = new MongoClient("localhost", 27017);
		db = mongoClient.getDB("mydb");
	}

	//TODO: refactor, use polymorphic dispatch
	@Override
	public Object execute(AdoptedStatement adoptedStatement) {
		if (adoptedStatement instanceof SelectAdoptedStatement) {
			return executeSelect((SelectAdoptedStatement)adoptedStatement);
		} else if (adoptedStatement instanceof InsertAdoptedStatement) {
			return executeInsert((InsertAdoptedStatement)adoptedStatement);
		} else if (adoptedStatement instanceof UpdateAdoptedStatement) {
			return executeUpdate((UpdateAdoptedStatement)adoptedStatement);
		} else if (adoptedStatement instanceof DropAdoptedStatement) {
			return executeDrop((DropAdoptedStatement)adoptedStatement);
		} else throw new MongoSQLAdapterException("Undefined Statement");
	}

	private Object executeSelect(SelectAdoptedStatement statement) {
		DBCollection collection = db.getCollection(statement.getCollectionName());
		BasicDBObject fields = new BasicDBObject();
		fields.put("_id", 0); //TODO: consider showing if in fields
		for (String fieldName : statement.getFields()) {
			fields.put(fieldName, 1);
		}

		BasicDBObject query = new BasicDBObject();
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		if (null != whereStatement) query.put(whereStatement.getKey(), whereStatement.getValue());

		DBCursor cursor = collection.find(query, fields);
		List<DBObject> list = new ArrayList<>();
		while(cursor.hasNext()) {
			DBObject object = cursor.next();
			System.out.println(object);
			list.add(object);
		}
		return list;
	}

	private Object executeInsert(InsertAdoptedStatement statement) {
		DBCollection collection = db.getCollection(statement.getCollectionName());
		BasicDBObject o = new BasicDBObject();
		for (Pair<String, Object> pair : statement.getValues()) {
			o.put(pair.getKey(), pair.getValue());
		}
		return collection.insert(o);
	}

	private Object executeUpdate(UpdateAdoptedStatement statement) {
		DBCollection collection = db.getCollection(statement.getCollectionName());

		BasicDBObject query = new BasicDBObject();
		Pair<String, Object> whereStatement = statement.getWhereStatement();
		if (null != whereStatement) query.put(whereStatement.getKey(), whereStatement.getValue());

		BasicDBObject set = new BasicDBObject();
		for (Pair<String, Object> pair : statement.getValues()) {
			set.put(pair.getKey(), pair.getValue());
		}
		return collection.update(query, new BasicDBObject("$set", set));
	}

	private Object executeDrop(DropAdoptedStatement statement) {
		DBCollection collection = db.getCollection(statement.getCollectionName());
		collection.drop();
		return new WriteResult(0, false, null);
	}

}
