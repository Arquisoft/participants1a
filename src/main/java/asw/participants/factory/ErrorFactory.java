package asw.participants.factory;

import asw.participants.webService.responses.errors.ErrorResponse;
import asw.participants.webService.responses.errors.IncorrectPasswordResponse;
import asw.participants.webService.responses.errors.RequiredEmailErrorResponse;
import asw.participants.webService.responses.errors.RequiredPasswordErrorResponse;
import asw.participants.webService.responses.errors.UnknownErrorResponse;
import asw.participants.webService.responses.errors.UserNotFoundResponse;
import asw.participants.webService.responses.errors.WrongEmailStyle;

//Creacion de los distintos tipos de error.
public class ErrorFactory {

	public static enum Errors {
		INCORRECT_PASSWORD,
		REQUIRED_EMAIL,
		REQUIRED_PASSWORD,
		USER_NOT_FOUND,
		WRONG_EMAIL_STYLE
	}

	// Generar Constructor privado no queremos que se pueda tener varias
	// instancias de la clase.
	private ErrorFactory() {
	}

	public static ErrorResponse getError(Errors error) {
		switch (error) {
		case INCORRECT_PASSWORD:
			return new IncorrectPasswordResponse();
		case REQUIRED_EMAIL:
			return new RequiredEmailErrorResponse();
		case REQUIRED_PASSWORD:
			return new RequiredPasswordErrorResponse();
		case USER_NOT_FOUND:
			return new UserNotFoundResponse();
		case WRONG_EMAIL_STYLE:
			return new WrongEmailStyle();
		default:// en caso de no conocer el error.
			return new UnknownErrorResponse();
		}
	}

}
