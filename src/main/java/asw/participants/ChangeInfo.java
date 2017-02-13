package asw.participants;

import org.springframework.web.bind.annotation.RequestBody;

import asw.participants.webService.responses.ChangeInfoResponse;

public interface ChangeInfo {
	/**
	 * Permite el cambio de clave u otra información a un
	 * ciudadano mediante una combinación de datos:
	 * email/contraseña/nuevaContraseña.
	 */
	public String changeInfo(@RequestBody ChangeInfoResponse cir);

}
