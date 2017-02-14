package asw.participants;

import org.springframework.web.bind.annotation.RequestParam;

public interface ChangeInfo {
	/**
	 * Permite el cambio de clave u otra información a un
	 * ciudadano mediante una combinación de datos:
	 * email/contraseña/nuevaContraseña.
	 */
	public String changeInfo(@RequestParam String email,
			@RequestParam String password, @RequestParam String newPassword);

}
