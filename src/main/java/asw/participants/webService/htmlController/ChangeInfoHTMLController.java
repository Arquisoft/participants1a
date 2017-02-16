package asw.participants.webService.htmlController;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import asw.participants.webService.responses.errors.ErrorResponse;

@Controller
public class ChangeInfoHTMLController {

	@RequestMapping(value = "/changeInfo", method = RequestMethod.POST)
	public String changeInfo(@RequestParam String email, Model model) {
		model.addAttribute("email", email);
		return "changeInfo";
	}

	@ExceptionHandler(ErrorResponse.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handleErrorResponseNotFound(ErrorResponse excep, Model model) {
		model.addAttribute("error", excep.getMessageJSONFormat());

		return "error";
	}
}
