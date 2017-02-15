package asw.participants.webService.responses.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Wrong mail style")
public class WrongEmailStyle extends ErrorResponse{

	@Override
	public String getMessageJSONFormat() {
		// TODO Auto-generated method stub
		return "{\"reason\": \"Wrong mail style\"}";
	}

	@Override
	public String getMessageStringFormat() {
		
		 return "Wrong mail style";
	}

}
