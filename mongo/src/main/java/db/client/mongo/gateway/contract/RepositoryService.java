package db.client.mongo.gateway.contract;

import db.client.contract.mongo.AdoptedStatement;

public interface RepositoryService {
	Object execute(AdoptedStatement adoptedStatement);
}
