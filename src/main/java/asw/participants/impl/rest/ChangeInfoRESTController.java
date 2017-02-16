package asw.participants.impl.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.participants.ChangeInfo;
import asw.participants.errors.ErrorResponse;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;

@RestController
@RequestMapping("user")
public class ChangeInfoRESTController implements ChangeInfo {
	
	@Autowired
	private GetParticipant getParticipant;
	
	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changeInfo(@RequestParam String email, @RequestParam String password,
			@RequestParam String newPassword) {

		validarCampos(email, password, newPassword);
		
		Participant p = getParticipant.getParticipant(email);
		validarPassword(password, p.getPassword());

		updateInfo.updateInfo(p, password, newPassword);
		
		return "{\"Resultado\": \"Datos actualizados correctamente\"}";
	}

	private void validarCampos(String email, String password, String newPassword) {
		if (email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_EMAIL);
		else if (password.trim().isEmpty() || newPassword.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
	}

	private void validarPassword(String password, String passwordParticipant) {
		if (!password.equals(passwordParticipant))
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}
