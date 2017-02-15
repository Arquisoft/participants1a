package asw.dbManagement;

import asw.dbManagement.model.Participant;

public interface UpdateInfo {
	/**
	 * Permite la solicitud de cambio de contraseña
	 */
	public void updateInfo(Participant p, String password, String newPassword);
}
