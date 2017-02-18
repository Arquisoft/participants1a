package asw.participants.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChangeEmailResponse")
public class ChangeEmailResponse {

	private String email;
	private String emailNuevo;

	public ChangeEmailResponse() {
	}

	public ChangeEmailResponse(String email, String emailNuevo) {
		super();
		this.email = email;
		this.emailNuevo = emailNuevo;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailNuevo() {
		return emailNuevo;
	}

	@XmlElement
	public void setEmailNuevo(String emailNuevo) {
		this.emailNuevo = emailNuevo;
	}

}
