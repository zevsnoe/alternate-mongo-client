package db.client.mongo.adapter.process;

import db.client.contract.mongo.AdoptedStatement;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.statement.SelectAdoptedStatement;
import db.client.mongo.converter.statement.SelectConvertedStatement;
import db.client.mongo.validator.MongoSQLAdapterException;
import org.bson.conversions.Bson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class SelectStatementAdapterTest {

	public static final String COLLECTION_NAME = "test-collection";
	@InjectMocks
	private SelectStatementAdapter adapter;

	@Spy
	SelectConvertedStatement selectConvertedStatement;

	@Test(expected = MongoSQLAdapterException.class)
	public void wrongStatementType() {
		adapter.adopt(mock(QueryConvertedStatement.class));
	}

	@Test
	public void adopt() {
		adapter = spy(adapter);
		Bson filter = mock(Bson.class);
		Bson projections = mock(Bson.class);
		doReturn(filter).when(adapter).buildFilterFrom(any());
		doReturn(projections).when(adapter).buildProjectionsFrom(any());
		doReturn(COLLECTION_NAME).when(selectConvertedStatement).getCollectionName();
		AdoptedStatement adoptedStatement = adapter.adopt(selectConvertedStatement);

		Assert.assertTrue(adoptedStatement instanceof SelectAdoptedStatement);
		SelectAdoptedStatement selectAdoptedStatement = (SelectAdoptedStatement) adoptedStatement;
		Assert.assertEquals(filter, selectAdoptedStatement.getFilter());
		Assert.assertEquals(projections, selectAdoptedStatement.getProjections());
		Assert.assertEquals(projections, selectAdoptedStatement.getProjections());
		Assert.assertEquals(COLLECTION_NAME, selectAdoptedStatement.getCollectionName());
	}

}