package asw.participants.webService.htmlController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.util.Assert;
import asw.participants.util.Utilidades;
import asw.participants.webService.responses.errors.ErrorResponse;



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
		
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		Assert.isPasswordEmpty(password);

		Participant participant = getParticipant.getParticipant(email);
		
		Assert.isParticipantNull(participant);
		
		Assert.isPasswordCorrect(password, participant);

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
	
	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model)
	{
		model.addAttribute("error", excep.getMessageJSONFormat());
		
		return "error";
	}
}
