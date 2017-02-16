package asw.dbManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import asw.dbManagement.model.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
	
	public Participant findByEmail(String email);
	
}
