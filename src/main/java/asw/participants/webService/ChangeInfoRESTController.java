package asw.participants.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.participants.ChangeInfo;
import asw.participants.util.Assert;
import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;
import asw.participants.webService.responses.RespuestaChangeInfoXML;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
public class ChangeInfoRESTController implements ChangeInfo {

	@Autowired
	private GetParticipant getParticipant;
	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public Object changePassword(@RequestHeader(value = "Accept") String accept,
			@RequestBody(required=true) PeticionChangePasswordREST datos) {
		String email = datos.getEmail();
		String password = datos.getPassword();
		String newPassword = datos.getNewPassword();
		
		Assert.isPasswordEmpty(password);
		Assert.isPasswordEmpty(newPassword);
		Assert.isEmailEmpty(email);

		Participant p = getParticipant.getParticipant(email);
		Assert.isParticipantNull(p);
		Assert.isPasswordCorrect(password, p);

		updateInfo.updatePassword(p, password, newPassword);

		return generatePasswordResponse(accept, email);
	}

	@Override
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST, headers = {
			"Accept=application/json", "Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public Object changeEmail(@RequestHeader(value = "Accept") String accept,
			@RequestBody(required=true) PeticionChangeEmailREST datos) {
		String email = datos.getEmail();
		String nuevoEmail = datos.getEmailNuevo();
		
		Assert.isEmailEmpty(email);
		Assert.isEmailEmpty(nuevoEmail);
		Assert.isEmailValid(email);
		Assert.isEmailValid(nuevoEmail);
		
		Participant p = getParticipant.getParticipant(email);
		Assert.isParticipantNull(p);
		updateInfo.updateEmail(p, email);
		
		return generateEmailResponse(accept, nuevoEmail);
	}
	
	private Object generateEmailResponse(String accept, String emailNuevo) {
		if(accept.contains("application/xml"))
			return new RespuestaChangeInfoXML(emailNuevo, "Email actualizado correctamente");
		else
			return "{\"Resultado\": \"Email actualizado correctamente\"}";
	}
	
	private Object generatePasswordResponse(String accept, String email) {
		if(accept.contains("application/xml"))
			return new RespuestaChangeInfoXML(email, "Contraseña actualizada correctamente");
		else
			return "{\"Resultado\": \"Contraseña actualizada correctamente\"}";
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageStringFormat();
	}

}
