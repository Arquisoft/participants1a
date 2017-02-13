package asw.dbManagement.impl;

import asw.dbManagement.UpdateInfo;
import asw.dbManagement.model.Participant;

public class UpdateInfoImpl implements UpdateInfo {

	@Override
	public void updateInfo() {
				
		GetParticipantImpl gpi = new GetParticipantImpl();
		Participant p = gpi.getParticipant();
		
		// Datos del participante a cambiar
//		p.setNombre(nombre);
//		p.setApellidos(apellidos);
//		p.setEdad(edad);
//		p.setID(id);
//		p.setEmail(email);
		
//		as.updateParticipant( p );			// o directamente Jpa.getManager().merge(participant);
		
	}

}
