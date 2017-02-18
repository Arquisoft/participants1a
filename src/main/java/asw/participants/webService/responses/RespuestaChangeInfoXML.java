package asw.participants.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChangeEmailResponse")
public class RespuestaChangeInfoXML {

	private String email;
	private String message;

	public RespuestaChangeInfoXML() {
	}

	public RespuestaChangeInfoXML(String email, String message) {
		super();
		this.email = email;
		this.message = message;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement
	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	@XmlElement
	public void setMessage(String message) {
		this.message = message;
	}

}
