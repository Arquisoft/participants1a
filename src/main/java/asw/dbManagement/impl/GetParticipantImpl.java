package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.dbManagement.repository.ParticipantRepository;

public class GetParticipantImpl implements GetParticipant {
	
	private ParticipantRepository repository;
	
	@Autowired
	public GetParticipantImpl(ParticipantRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public Participant getParticipant(String email) {
		
		return this.repository.findByEmail(email);
	}

}
