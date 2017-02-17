package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import asw.Application;
import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.webService.request.PeticionInfoREST;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest({ "server.port=0" })
public class MainControllerTest {

	@Value("${local.server.port}")
	private int port;

	private URL base;
	private RestTemplate template;
	
	@Autowired 
	private GetParticipant getParticipant;

	@Before
	public void setUp() throws Exception {
		this.base = new URL("http://localhost:" + port + "/");
		template = new TestRestTemplate();
	}
	
	@Test
	public void domainModelTest(){
		Participant participant1 = getParticipant.getParticipant("paco@hotmail.com");
		Participant participant2 = getParticipant.getParticipant("pac@hotmail.com");
		Participant participant3 =  getParticipant.getParticipant("paco@hotmail.com");
		//participant2 = null
		assertFalse(participant1.equals(participant2));
		//Objeto que no es de la misma clase
		assertFalse(participant1.equals(4));
		//Son iguales
		participant1.equals(participant3);
		
		assertEquals(participant1.toString(), "Participant [nombre=" + participant1.getNombre() + ", apellidos=" + participant1.getApellidos() 
		+ ", fechaNacimiento=" + participant1.getFechaNacimiento()+ ", email=" + participant1.getEmail() + ", DNI=" + participant1.getDNI() 
		+ ", direccion=" + participant1.getDireccion() + ", nacionalidad=" + participant1.getNacionalidad()	+ "]");
	}

	@Test
	public void participantExistAndCorrectPasssword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";

		response = template.postForEntity(userURI, new PeticionInfoREST("paco@hotmail.com", "123456"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"firstName\":\"Paco\",\"lastName\":\"Gómez\",\"edad\":46,\"email\":\"paco@hotmail.com\",\"id\":\"12345678A\"}"));

		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@gmail.com", "123456"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"firstName\":\"Pepe\",\"lastName\":\"Fernández\",\"edad\":41,\"email\":\"pepe@gmail.com\",\"id\":\"87654321B\"}"));

		response = template.postForEntity(userURI, new PeticionInfoREST("carmen@yahoo.com", "123456"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"firstName\":\"Carmen\",\"lastName\":\"López\",\"edad\":46,\"email\":\"carmen@yahoo.com\",\"id\":\"11223344C\"}"));

		response = template.postForEntity(userURI, new PeticionInfoREST("isabel@gmail.com", "123456"), String.class);
		assertThat(response.getBody(), equalTo(
				"{\"firstName\":\"Isabel\",\"lastName\":\"Rodríguez\",\"edad\":41,\"email\":\"isabel@gmail.com\",\"id\":\"22334455D\"}"));
	}

	@Test
	public void participantDoNotExist() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("ofelia@hotmail.com", "ajksdkje"),
				String.class);
		assertThat(response.getBody(), equalTo(userNotFound));

		response = template.postForEntity(userURI, new PeticionInfoREST("martin@hotmail.com", "shcxhqw"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
	}

	@Test
	public void incorrectPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String incorrectPassword = "{\"reason\": \"Password Incorrect\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("paco@hotmail.com", "12356"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@gmail.com", "12346"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("carmen@yahoo.com", "13456"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("isabel@gmail.com", "23456"), String.class);
		assertThat(response.getBody(), equalTo(incorrectPassword));
	}

	@Test
	public void emptyEmail() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyEmail = "{\"reason\": \"User email is required\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "1223"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("", "iewgs"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));

		response = template.postForEntity(userURI, new PeticionInfoREST("   ", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}

	@Test
	public void invalidEmail() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";
		response = template.postForEntity(userURI, new PeticionInfoREST("ajsjc", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("jxjsjd@", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("chdgetc@chhsy", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));

		response = template.postForEntity(userURI, new PeticionInfoREST("sjhwuwsc", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
	}

	@Test
	public void emptyPassword() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/user";
		String emptyPassword = "{\"reason\": \"User password is required\"}";

		response = template.postForEntity(userURI, new PeticionInfoREST("paco@hotmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("pepe@gmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("carmen@yahoo.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));

		response = template.postForEntity(userURI, new PeticionInfoREST("isabel@gmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyPassword));
	}

//	@Test
//	public void emailRequiredChange() {
//		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
//		String userURI = base.toString() + "/user/changeInfo";
//		String emptyEmail = "{\"reason\": \"User email is required\"}";
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("","", ""), String.class);
//		assertThat(response.getBody(), equalTo(emptyEmail));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("","sswde", "1223"), String.class);
//		assertThat(response.getBody(), equalTo(emptyEmail));
//
//		response = template.postForEntity(userURI,new PeticionCambiarPassword("","", "cshiwcs"), String.class);
//		assertThat(response.getBody(), equalTo(emptyEmail));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("   ","cheuc", "   "), String.class);
//		assertThat(response.getBody(), equalTo(emptyEmail));
//	}

//	@Test
//	public void emptyPasswordChange() {
//		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
//		String userURI = base.toString() + "/user";
//		String emptyPassword = "{\"reason\": \"User password is required\"}";
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("paco@hotmail.com", "",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("pepe@gmail.com", "",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("carmen@yahoo.com", "",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("isabel@gmail.com", "",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//	}
	
//	@Test
//	public void emptyNewPasswordChange() {
//		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
//		String userURI = base.toString() + "/user";
//		String emptyPassword = "{\"reason\": \"User password is required\"}";
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("paco@hotmail.com", "123456",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("pepe@gmail.com", "123456",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("carmen@yahoo.com", "123456",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//
//		response = template.postForEntity(userURI, new PeticionCambiarPassword("isabel@gmail.com", "123456",""), String.class);
//		assertThat(response.getBody(), equalTo(emptyPassword));
//	}
	
	@Test
	public void testHtmlController() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/";

		response = template.getForEntity(userURI, String.class);
		assertThat(response.getBody().replace(" ", "").replace("\n", "").replace("\t", ""),
				equalTo(new String("<!DOCTYPEHTML><html><head><metacharset=\"UTF-8\"/>"
						+ "<title>Login</title></head><body><h1>Login</h1><formmethod=\"POST\"action=\"login\">"
						+ "<labelfor=\"email\">Usuario:</label><inputtype=\"text\"id=\"email\"name=\"email\"/>"
						+ "<br/><labelfor=\"password\">Contraseña:</label><inputtype=\"password\"id=\"password\"name="
						+ "\"password\"/><br/><buttontype=\"submit\"id=\"login\">Entrar</button></form></body></html>")
								.replace(" ", "")));
	}

	//para hacer el request y cambiar la informacion.
	public class PeticionCambiarPassword {
		private String email;
		private String password;
		private String newPassword;

		public PeticionCambiarPassword(String email, String password, String newPassword) {
			this.email = email;
			this.password = password;
			this.setNewPassword(newPassword);
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
	}
}