package db.client.api.dto;

public class QueryResultDto {
	private String message;

	public String getMessage() {
		return message;
	}

	public QueryResultDto setMessage(String message) {
		this.message = message;
		return this;
	}
}
