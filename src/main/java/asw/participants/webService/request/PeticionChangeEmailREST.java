package asw.participants.webService.request;

public class PeticionChangeEmailREST {

	private String email;
	private String emailNuevo;
	
	public PeticionChangeEmailREST() {

	}

	public PeticionChangeEmailREST(String email, String emailNuevo) {
		super();
		this.email = email;
		this.emailNuevo = emailNuevo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailNuevo() {
		return emailNuevo;
	}

	public void setEmailNuevo(String emailNuevo) {
		this.emailNuevo = emailNuevo;
	}

}
