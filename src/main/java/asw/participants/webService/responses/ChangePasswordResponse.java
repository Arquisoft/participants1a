package asw.participants.webService.responses;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ChangePasswordResponse")
public class ChangePasswordResponse {

	private String password;
	private String newPassword;
	private String repeatNewPassword;
	
	public ChangePasswordResponse() {}

	public ChangePasswordResponse(String password, String newPassword, String repeatNewPassword) {
		super();
		this.password = password;
		this.newPassword = newPassword;
		this.repeatNewPassword = repeatNewPassword;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	@XmlElement
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	@XmlElement
	public void setRepeatNewPassword(String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}

}
