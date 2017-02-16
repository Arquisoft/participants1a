package asw.participants;

import org.springframework.http.ResponseEntity;

import asw.participants.impl.request.PeticionInfoREST;
import asw.participants.impl.request.RespuestaInfoREST;

public interface GetParticipantInfo {

	


	public ResponseEntity<RespuestaInfoREST> getPOSTpetition(PeticionInfoREST peticion);
		






}
