package ec.com.danylassosolution.users.models.constants;

public enum VaccineType {
	SPUTNIK("01"), 
	ASTRAZENECA("02"), 
	PFIZER("03"), 
	JHONSON_JHONSON("04");
	

	private String code;

	public String getCode() {
		return code;
	}

	private VaccineType(String code) {
		this.code = code;
	}
	
	
}
