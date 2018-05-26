package db.client.mongo.gateway.service;

import db.client.contract.mongo.AdoptedStatement;
import db.client.mongo.gateway.contract.DeleteGateway;
import db.client.mongo.gateway.contract.DropGateway;
import db.client.mongo.gateway.contract.InsertGateway;
import db.client.mongo.gateway.contract.SelectGateway;
import db.client.mongo.gateway.contract.UpdateGateway;
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
public class GatewayAwareRepositoryServiceTest {

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

	@Mock
	private DeleteGateway deleteGateway;

	@Test
	public void adoptWithDropGateway(){
		when(adoptedStatement.getType()).thenReturn(DROP);
		service.execute(adoptedStatement);
		verify(dropGateway).drop(eq(adoptedStatement));
	}

	@Test
	public void adoptWithUpdateGateway(){
		when(adoptedStatement.getType()).thenReturn(UPDATE);
		service.execute(adoptedStatement);
		verify(updateGateway).update(eq(adoptedStatement));
	}

	@Test
	public void adoptWithInsertGateway(){
		when(adoptedStatement.getType()).thenReturn(INSERT);
		service.execute(adoptedStatement);
		verify(insertGateway).insert(eq(adoptedStatement));
	}

	@Test
	public void adoptWithSelectGateway(){
		when(adoptedStatement.getType()).thenReturn(SELECT);
		service.execute(adoptedStatement);
		verify(selectGateway).select(eq(adoptedStatement));
	}

	@Test
	public void adoptWithDeleteGateway(){
		when(adoptedStatement.getType()).thenReturn(DELETE);
		service.execute(adoptedStatement);
		verify(deleteGateway).delete(eq(adoptedStatement));
	}


}