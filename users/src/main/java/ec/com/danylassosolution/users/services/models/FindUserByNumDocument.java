package ec.com.danylassosolution.users.services.models;


import ec.com.danylassosolution.users.models.entities.User;

public interface FindUserByNumDocument {

	/**
	 * Encuentra un usurio según un numero de documento
	 * @param numDocument
	 * @throws NotFoundException - En caso de no existir el usuario
	 * @throws BusinessRulesException - si existe varios usuarios con el num de documento 
	 * @return
	 */
	public User find(String numDocument);
}
