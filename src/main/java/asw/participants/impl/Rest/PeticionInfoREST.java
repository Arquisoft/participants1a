package asw.participants.impl.Rest;

public class PeticionInfoREST {
	
	public void setLogin(String login) {
		this.login = login;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	private String login;
	private String password;
	
	
	public PeticionInfoREST(){
		
	}


	public String getLogin() {
		return login;
	}


	public String getPassword() {
		return password;
	}
	
	

}
