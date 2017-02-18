package asw.participants.webService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.participants.ChangeInfo;
import asw.participants.util.Assert;
import asw.participants.webService.responses.ChangeEmailResponse;
import asw.participants.webService.responses.ChangePasswordResponse;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
// @RequestMapping("user")
public class ChangeInfoRESTController implements ChangeInfo {

	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = { "Accept=application/json",
			"Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public Object changePassword(HttpSession session, @RequestHeader(value = "Accept") String accept,
			@RequestParam String password, @RequestParam String newPassword, @RequestParam String repeatNewPassword) {
		Assert.isPasswordEmpty(password);
		Assert.isPasswordEmpty(newPassword);
		Assert.isPasswordEmpty(repeatNewPassword);
		Assert.isSamePassword(newPassword, repeatNewPassword);

		// Participant que se ha logeado antes
		Participant p = (Participant) session.getAttribute("participant");
		Assert.isPasswordCorrect(password, p);

		// Actualizo sus datos
		updateInfo.updatePassword(p, password, newPassword);

		return generatePasswordResponse(accept, password, newPassword, repeatNewPassword);
	}

	@Override
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST, headers = {
			"Accept=application/json", "Accept=application/xml" }, produces = { "application/json", "text/xml" })
	public Object changeEmail(HttpSession session, @RequestHeader(value = "Accept") String accept,
			@RequestParam String email) {
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);

		// Participant que se ha logeado antes
		Participant p = (Participant) session.getAttribute("participant");

		String antiguoEmail = p.getEmail();
		Assert.isSameEmail(email, antiguoEmail);
		
		// Actualizo sus datos
		updateInfo.updateEmail(p, email);
		
		return generateEmailResponse(accept, antiguoEmail, email);
	}
	
	private Object generatePasswordResponse(String accept, String password, String newPassword, String repeatNewPassword) {
		if(accept.contains("application/xml"))
			return new ChangePasswordResponse(password, newPassword, repeatNewPassword);
		else
			return "{\"Resultado\": \"Contraseña actualizada correctamente\"}";
	}
	
	private Object generateEmailResponse(String accept, String emailNuevo, String email) {
		if(accept.contains("application/xml"))
			return new ChangeEmailResponse(emailNuevo, email);
		else
			return "{\"Resultado\": \"Email actualizado correctamente\"}";
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		//PROVISIONAL, el navegador pide xml
		// Si la cabecera HTTP "Accept" cambia deberia de devolver o XML o JSON
		return "<reason>"+error.getMessageStringFormat()+"</reason>";
	}

}
