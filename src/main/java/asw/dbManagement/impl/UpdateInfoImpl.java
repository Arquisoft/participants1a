package asw.dbManagement.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;
import asw.dbManagement.repository.ParticipantRepository;

@Service
public class UpdateInfoImpl implements UpdateInfo {

	private ParticipantRepository repository;
	
	@Autowired
	public UpdateInfoImpl(ParticipantRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public void updateInfo(Participant participant, String password, String newPassword) {
		
		if (password != null && newPassword != null && !(password.equals(newPassword))
				&& participant.getPassword().equals(password)) {
			participant.setPassword(newPassword);
			this.repository.save(participant);
		}
		
	}

}
