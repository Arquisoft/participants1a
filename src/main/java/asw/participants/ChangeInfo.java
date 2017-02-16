package asw.participants;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;

public interface ChangeInfo {
	/**
	 * Permite el cambio de clave u otra información a un
	 * ciudadano mediante una combinación de datos:
	 * email/contraseña/nuevaContraseña.
	 */
	public String changeInfo(HttpSession session, @RequestParam String password, 
			@RequestParam String newPassword, @RequestParam String repeatNewPassword);

}
