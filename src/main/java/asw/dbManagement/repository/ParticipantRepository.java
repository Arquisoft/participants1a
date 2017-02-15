package asw.dbManagement.repository;

import org.springframework.data.repository.CrudRepository;

import asw.dbManagement.model.Participant;

public interface ParticipantRepository extends CrudRepository<Participant, Long> {
	
	public Participant findByEmail(String email);
	
}
