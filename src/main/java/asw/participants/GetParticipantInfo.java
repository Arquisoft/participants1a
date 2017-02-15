package asw.participants;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import asw.participants.impl.Rest.PeticionInfoREST;
import asw.participants.impl.Rest.RespuestaInfoREST;

public interface GetParticipantInfo {

	


	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);
		






}
