package yjh.error;

public enum ErrorCode {
	SUCCESS (200, "Success"),
	LOGIN_FAILED(400, "Bad Request"),
	UNAUTHORIZED(403, "unauthorized"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Media Type Not Supported"),
	
	MISSING_PARAMETER(4001, "Missing Parameter"),
	INVALID_ARGUMENT(4002, "Invalid Argument"),
	MARFORMED_JSON_REQUEST(4005, "Malformed Json Request"),
	MARFORMED_JSON_RESPONSE(4005, "Malformed Json Request"),
	
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	END(999, "Final Error Code");

	private final int code;
	private final String description;

	private ErrorCode(int code, String description) {
		this.code = code;
	    this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}
}
