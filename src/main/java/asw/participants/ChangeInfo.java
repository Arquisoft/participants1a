package asw.participants;

import org.springframework.http.ResponseEntity;

import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;
import asw.participants.webService.responses.RespuestaChangeInfoREST;

public interface ChangeInfo {
	/**
	 * Cambio de contraseña
	 * 
	 * @param accept header, puede ser xml o json, en funcion respondera una cosa u otra
	 * @param datos requeridos
	 * @return respuesta en xml o json
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changePassword(String accept, PeticionChangePasswordREST datos);

	/**
	 * Cambio de email
	 * 
	 * @param accept header, puede ser xml o json, en funcion respondera una cosa otra
	 * @param datos requeridos
	 * @return respuesta en xml o json
	 */
	public ResponseEntity<RespuestaChangeInfoREST> changeEmail(String accept, PeticionChangeEmailREST datos);
}
