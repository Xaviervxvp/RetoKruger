package ec.com.danylassosolution.users.models.constants;

public enum AppErrorsCodesAndMessages {
	FIELD_VALIDATION("U0001", "Field validation"),
	
	USER_SAVE_ERROR("U0002", "Unexpected error in saved user data"),
	USER_SAVE_ERROR_CONSTRAINT_VALIDATION("U0003", "Constraint Validation in user data"),
	USER_NOT_FOUND("U0004", "User not found"),
	USER_ILLEGAL_DATA("U0005", "Users register has illegal data"),
	USER_NUM_DOCUMENT_ALREADY_USED("U0006", "Another previously registered user with this document number"),
	
	
	VACCINES_IS_EMPTY("U0007", "list of vaccines is empty"),
	VACCINE_TYPE_INVALID("U0008", "vaccine type is invalid"),
	VACCINE_STATE_INVALID("U0009", "Vaccine status is invalid"),
	VACCINES_SAVE_ERROR("U0010", "Unexpected error at to save vaccine"),
	
	ADRESS_SAVE_ERROR("U0011", "Unexpected error at to save address"),
	
	USER_DELETE_ERROR("U0012", "Unexpected error in deleted user data"),
	;
	
	private String code;
	private String message;
	
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private AppErrorsCodesAndMessages(String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	
	
}
