package asw.participants;

import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;

public interface ChangeInfo {
	/**
	 * Cambio de contraseña
	 * @param accept header, puede ser xml o json, en funcion respondera una cosa u otra
	 * @param datos requeridos
	 * @return respuesta en xml o json
	 */
	public Object changePassword(String accept, PeticionChangePasswordREST datos);

	/**
	 * Cambio de email
	 * @param accept header, puede ser xml o json, en funcion respondera una cosa u otra
	 * @param datos requeridos
	 * @return respuesta en xml o json
	 */
	public Object changeEmail(String accept, PeticionChangeEmailREST datos);
}
