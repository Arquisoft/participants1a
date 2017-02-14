package asw.participants.impl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.participants.ChangeInfo;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
@RequestMapping("user")
public class ChangeInfoController implements ChangeInfo {

	@Override
	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changeInfo(@RequestParam String email, @RequestParam String password,
			@RequestParam String newPassword) {

		validarEmail(email);
		validarPassword(password, newPassword);

		// ACTUALIZAR CONTRASEÑA EN LA BASE DE DATOS, BUSCAR POR EMAIL

		return "{\"Resultado\": \"Datos actualizados correctamente\"}";
	}

	private void validarEmail(String email) {
		if (email.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_EMAIL);
	}

	private void validarPassword(String password, String newPassword) {
		if (password.trim().isEmpty() || newPassword.trim().isEmpty())
			throw ErrorFactory.getError(Errors.REQUIRED_PASSWORD);
		if (!password.equals(newPassword))
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}
}
