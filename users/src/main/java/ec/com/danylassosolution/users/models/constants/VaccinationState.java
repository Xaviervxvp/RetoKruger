package ec.com.danylassosolution.users.models.constants;

public enum VaccinationState {
	VACCINATED("01"),
	NOT_VACCINATED("02");
	
	private String code;

	public String getCode() {
		return code;
	}

	private VaccinationState(String code) {
		this.code = code;
	}
	
	
}
