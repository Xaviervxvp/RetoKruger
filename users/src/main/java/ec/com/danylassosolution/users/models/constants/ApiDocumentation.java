package ec.com.danylassosolution.users.models.constants;

public enum ApiDocumentation {

	USERS_FILTER("<b>La libreria Swagger no reconoce API que manejan las especificacion. Ejemplo de consumo:</b>"
			+ "<p>{base_url}?page=numeroPagina&size=numeroFilas&sort= atributoAOrdenar,desc"
			+ "?vaccinationState=''"
			+ "&vaccineType=''"
			+ "&dateVaccineAfter=''&dateVaccineBefore=''</p>"
			+ "<ul><li>vaccinationState: puede tomar los valores de VACCINATED y NOT_VACCINATED</li>"
			+ "<li>vaccineType:  puede tomar los valores de SPUTNIK,ASTRAZENECA, PFIZER, JHONSON_JHONSON  </li>"
			+ "</ul>");
	
	private String notes;

	public String getNotes() {
		return notes;
	}

	private ApiDocumentation(String notes) {
		this.notes = notes;
	}
	
	
}
