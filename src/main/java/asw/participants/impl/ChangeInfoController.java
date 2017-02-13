package asw.participants.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.participants.ChangeInfo;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;
import asw.participants.webService.responses.ChangeInfoResponse;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
@RequestMapping("user")
public class ChangeInfoController implements ChangeInfo {

	@Override
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST, produces = "application/json")
	public String changeInfo(ChangeInfoResponse cir) {
		String email = cir.getEmail(); // Para buscar al usuario en la BBDD
		String password = cir.getPassword();
		String newPassword = cir.getNewPassword();
		
		validarEmail(email);
		validarPassword(password, newPassword);
		
		// Actualizarlo en la base de datos
		
		return "{\"Resultado\":\"Se ha actualizado al usuario.\"}";
	}
	
	private void validarEmail(String email) {
		if(email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_EMAIL); //lanzar error: email no puede estar vacio
	}

	private void validarPassword(String password, String newPassword) {
		if(password.trim().isEmpty() || newPassword.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);// lanzar error: contraseñas no pueden estar vacias
		else if(password.equals(newPassword))
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);// lanzar error: contraseñas iguales
	}
	
	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error)
	{
		return error.getMessageJSONFormat();
	}
}
