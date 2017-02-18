package asw.participants;

import javax.servlet.http.HttpSession;

public interface ChangeInfo {
	/**
	 * Permite el cambio de la contraseña de un usuario ya logeado en el sistema.
	 * @param session para acceder al participante logeado
	 * @param accept para saber si generar la respuesta en JSON o XML, por defecto JSON
	 * @param password antigua
	 * @param newPassword nueva contraseña
	 * @param repeatNewPassword confirmar nueva contrasñea
	 * @return depende de la negociacion con el cliente, XML o JSON
	 */
	public Object changePassword(HttpSession session, String accept, String password,
			String newPassword, String repeatNewPassword);

	/**
	 * Permite el cambio del email del usuario ya logeado
	 * @param session para acceder al participante logeado
	 * @param accept para saber si generar la respuesta en JSON o XML, por defecto JSON
	 * @param email nuevo
	 * @return depende de la negociacion con el cliente, XML o JSON
	 */
	public Object changeEmail(HttpSession session, String accept, String email);
}
