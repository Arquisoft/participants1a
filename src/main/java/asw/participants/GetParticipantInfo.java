package asw.participants;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

public interface GetParticipantInfo {
	/**
	 * Acceso a los datos de un participante
	 * mediante la combinación email/contraseña
	 */
	public String getLogin( @RequestParam String mail,  @RequestParam String password,Model model);
	
	
	
		






}
