package asw.dbManagement.impl;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;

public class GetParticipantImpl implements GetParticipant {

	@Override
	public Participant getParticipant() {
//		return Jpa.getManager().createNamedQuery("Participant.find", Participant.class)
//				.getSingleResult();
		return null;
	}

}
