package asw.participants.webService.responses;

import asw.dbManagement.model.Participant;
import asw.participants.util.Utilidades;

public class RespuestaInfoREST {	
	
	private String firstName;
	private String lastName;
	private int edad;
	private String ID;
	private String email;
	
	public RespuestaInfoREST(){
		
	}
	
	public RespuestaInfoREST(Participant participant){
		setFirstName(participant.getNombre());
		setLastName(participant.getApellidos());
		setEdad(Utilidades.getEdad(participant.getFechaNacimiento()));
		setID(participant.getDNI());
		setEmail(participant.getEmail());
//		this.firstName = participant.getNombre();
//		this.lastName = participant.getApellidos();
//		this.edad = Utilidades.getEdad(participant.getFechaNacimiento());
//		this.ID = participant.getDNI();
//		this.email = participant.getEmail();
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
