package db.client.api.controller;

import db.client.api.dto.QueryResultDto;
import db.client.contract.client.ClientFactory;
import db.client.contract.client.Interactor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Api(value = "DataBase Client Controller", description = "Run sql queries on specified db", produces = APPLICATION_JSON_VALUE)
@RequestMapping(value = "/db/client/", produces = APPLICATION_JSON_VALUE)
public class DBClientInteractionController {

	private final Interactor interactor;
	private final ClientFactory clientFactory;

	@Autowired
	public DBClientInteractionController(Interactor interactor, ClientFactory clientFactory) {
		this.interactor = interactor;
		this.clientFactory = clientFactory;
	}

	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Execute query", notes = "Executing query")
	@ApiResponses(value = {
			@ApiResponse(code = 204, message = "Success run but no result"),
			@ApiResponse(code = 400, message = "Query is not valid"),
			@ApiResponse(code = 500, message = "Error occurred while executing query")
	})
	@RequestMapping(value = "/execute", method = RequestMethod.POST)
	public QueryResultDto execute(@RequestBody @Valid String query) {
		Object o = interactor.interactWith(clientFactory.getClient(), query);
		QueryResultDto queryResultDto = new QueryResultDto();
		queryResultDto.setResult(o);
		return queryResultDto;
	}

}
