package asw.participants.webService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.participants.ChangeInfo;
import asw.participants.util.Assert;
import asw.participants.webService.responses.errors.ErrorResponse;

@RestController
//@RequestMapping("user")
public class ChangeInfoRESTController implements ChangeInfo {

	@Autowired
	private UpdateInfo updateInfo;

	@Override
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST, headers = {"Accept=application/json"}, produces = {
			"application/json" })
	public String changePassword(HttpSession session, @RequestParam String password, @RequestParam String newPassword,
			@RequestParam String repeatNewPassword) {
		camposNoVacios(password, newPassword, repeatNewPassword);
		Assert.isSamePassword(newPassword, repeatNewPassword);
		
		Participant p = (Participant) session.getAttribute("participant");
		Assert.isPasswordCorrect(password, p);

		updateInfo.updatePassword(p, password, newPassword);

		return "{\"Resultado\": \"Contraseña actualizada correctamente\"}";
	}
	
	@Override
	@RequestMapping(value = "/changeEmail", method = RequestMethod.POST, headers = {"Accept=application/json"}, produces = {
			"application/json" })
	public String changeEmail(HttpSession session, @RequestParam String email) {
		Assert.isEmailEmpty(email);
		Assert.isEmailValid(email);
		
		Participant p = (Participant) session.getAttribute("participant");
		updateInfo.updateEmail(p, email);
		
		return "{\"Resultado\": \"Email actualizado correctamente\"}";
	}

	private void camposNoVacios(String password, String newPassword, String repeatNewPassword) {
		Assert.isPasswordEmpty(password);
		Assert.isPasswordEmpty(newPassword);
		Assert.isPasswordEmpty(repeatNewPassword);
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponses(ErrorResponse error) {
		return error.getMessageJSONFormat();
	}

}
