package db.client.mongo.adapter;

import db.client.contract.StatementType;
import db.client.contract.mongo.QueryConvertedStatement;
import db.client.mongo.adapter.contract.Adapter;
import db.client.mongo.adapter.contract.DeleteAdapter;
import db.client.mongo.adapter.contract.DropAdapter;
import db.client.mongo.adapter.contract.InsertAdapter;
import db.client.mongo.adapter.contract.SelectAdapter;
import db.client.mongo.adapter.contract.UpdateAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static db.client.contract.StatementType.DELETE;
import static db.client.contract.StatementType.DROP;
import static db.client.contract.StatementType.INSERT;
import static db.client.contract.StatementType.SELECT;
import static db.client.contract.StatementType.UPDATE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatementQueryAdapterTest {

	@InjectMocks
	private StatementQueryAdapter adapter;

	@Mock
	private QueryConvertedStatement convertedStatement;

	@Mock
	private DropAdapter dropAdapter;

	@Mock
	private UpdateAdapter updateAdapter;

	@Mock
	private InsertAdapter insertAdapter;

	@Mock
	private SelectAdapter selectAdapter;

	@Mock
	private DeleteAdapter deleteAdapter;

	@Test
	public void adoptWithDropAdapter(){
		verifyTheRightAdapterFor(DROP, dropAdapter);
	}

	@Test
	public void adoptWithUpdateAdapter(){
		verifyTheRightAdapterFor(UPDATE, updateAdapter);
	}

	@Test
	public void adoptWithInsertAdapter(){
		verifyTheRightAdapterFor(INSERT, insertAdapter);
	}

	@Test
	public void adoptWithSelectAdapter(){
		verifyTheRightAdapterFor(SELECT, selectAdapter);
	}

	@Test
	public void adoptWithDeleteAdapter(){
		verifyTheRightAdapterFor(DELETE, deleteAdapter);
	}

	private void verifyTheRightAdapterFor(StatementType type, Adapter statementAdapter) {
		when(convertedStatement.getType()).thenReturn(type);
		adapter.adopt(convertedStatement);
		verify(statementAdapter).adopt(eq(convertedStatement));
	}

}