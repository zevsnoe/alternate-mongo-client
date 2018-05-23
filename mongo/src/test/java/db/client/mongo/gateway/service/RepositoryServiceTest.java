package db.client.mongo.gateway.service;

import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.DropGateway;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.contract.SelectGateway;
import db.client.mongo.gateway.contract.UpdateGateway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static db.client.contract.StatementType.DROP;
import static db.client.contract.StatementType.INSERT;
import static db.client.contract.StatementType.SELECT;
import static db.client.contract.StatementType.UPDATE;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryServiceTest {

	@InjectMocks
	private GatewayAwareRepositoryService service;

	@Mock
	private AdoptedStatement adoptedStatement;

	@Mock
	private DropGateway dropGateway;

	@Mock
	private UpdateGateway updateGateway;

	@Mock
	private InsertGateway insertGateway;

	@Mock
	private SelectGateway selectGateway;

	@Test
	public void executeViaDropGateway(){
		when(adoptedStatement.getType()).thenReturn(DROP);
		service.execute(adoptedStatement);
		verify(dropGateway).drop(eq(adoptedStatement));
	}

	@Test
	public void adoptWithUpdateAdapter(){
		when(adoptedStatement.getType()).thenReturn(UPDATE);
		service.execute(adoptedStatement);
		verify(updateGateway).update(eq(adoptedStatement));
	}

	@Test
	public void adoptWithInsertAdapter(){
		when(adoptedStatement.getType()).thenReturn(INSERT);
		service.execute(adoptedStatement);
		verify(insertGateway).insert(eq(adoptedStatement));
	}

	@Test
	public void adoptWithSelectAdapter(){
		when(adoptedStatement.getType()).thenReturn(SELECT);
		service.execute(adoptedStatement);
		verify(selectGateway).select(eq(adoptedStatement));
	}

}