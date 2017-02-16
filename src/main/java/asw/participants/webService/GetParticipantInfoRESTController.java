package asw.participants.webService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.GetParticipantInfo;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;
import asw.participants.util.Assert;
import asw.participants.webService.request.PeticionInfoREST;
import asw.participants.webService.responses.RespuestaInfoREST;

@RestController
public class GetParticipantInfoRESTController implements GetParticipantInfo {

	@Autowired
	private GetParticipant getParticipant;

	@Override
	@RequestMapping(value = "/user", method = RequestMethod.POST, headers = "Accept=application/json", produces = {
			"application/json" })
	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(@RequestBody(required = true) PeticionInfoREST peticion) {

		Participant participant = getParticipant.getParticipant(peticion.getLogin());

		// if(!utilidades.validarCorreo(email)){//Se lanza este error cuan
		// throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		// }

		Assert.isParticipantNull(participant);
//		if (participant == null) {
//			throw ErrorFactory.getError(Errors.USER_NOT_FOUND);
//		}
		
		Assert.isPasswordCorrect(peticion.getPassword(), participant);
		
		if (!peticion.getPassword().equals(participant.getPassword())) {
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}

		/*
		 * Añadimos la información al modelo, para que se muestre en la pagina
		 * html: datosParticipant
		 */

		return new ResponseEntity<RespuestaInfoREST>(new RespuestaInfoREST(participant), HttpStatus.OK);
	}

}
