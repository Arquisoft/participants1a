package asw.participants.impl.html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;
import asw.participants.util.Utilidades;



@Controller
public class GetParticipantInfoHTMLController {
	
	
	

	@Autowired
	private GetParticipant getParticipant;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model) {
		return "login";
	}


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getLogin(@RequestParam String email, @RequestParam String password, Model model) {

		// if(!utilidades.validarCorreo(email)){//Se lanza este error cuan
		// throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		// }

		Participant participant = getParticipant.getParticipant(email);

		if (participant == null) {
			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
		}
		if (!password.equals(participant.getPassword())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}

		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina
		 * html: datosParticipant
		 */
		model.addAttribute("name", participant.getNombre());
		model.addAttribute("secondName", participant.getApellidos());
		model.addAttribute("age", Utilidades.getEdad(participant.getFechaNacimiento()));
		model.addAttribute("adress", participant.getDireccion());
		model.addAttribute("email", participant.getEmail());
		model.addAttribute("dni", participant.getDNI());

		return "datosParticipant";

	}

}
