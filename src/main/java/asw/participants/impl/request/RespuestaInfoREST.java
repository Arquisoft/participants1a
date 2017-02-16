package asw.participants.impl.request;

import asw.dbManagement.model.Participant;
import asw.participants.util.Utilidades;

public class RespuestaInfoREST {
	
	/*
	 * 
	 * private String nombre;
	private String apellidos;
	private String password;
	private Date fechaNacimiento;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String DNI;
	private String direccion;
	private String nacionalidad;
	
	 * 
	 * 
	 */
	
	
	private String firstName;
	private String lastName;
	private int edad;
	private String ID;
	private String email;
	
	public RespuestaInfoREST(){
		
	}
	
	public RespuestaInfoREST(Participant participant){
		this.firstName = participant.getNombre();
		this.lastName = participant.getApellidos();
		this.edad = Utilidades.getEdad(participant.getFechaNacimiento());
		this.ID = participant.getDNI();
		this.email = participant.getEmail();
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
