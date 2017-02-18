package test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import asw.participants.webService.request.PeticionChangeEmailREST;
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
	public void domainModelEqualsTest() {
		Participant participant1 = getParticipant.getParticipant("paco@hotmail.com");
		Participant participant2 = getParticipant.getParticipant("pac@hotmail.com");
		Participant participant3 = getParticipant.getParticipant("paco@hotmail.com");
		Participant participant4 = getParticipant.getParticipant("pepe@gmail.com");
		assertFalse(participant1.equals(participant2));
		assertFalse(participant1.equals(4));
		assertTrue(participant1.equals(participant3));
		assertTrue(participant1.equals(participant1));
		assertFalse(participant1.equals(participant4));
	}

	@Test
	public void domainModelToString() {
		Participant participant1 = getParticipant.getParticipant("paco@hotmail.com");
		assertEquals(participant1.toString(),
				"Participant [nombre=" + participant1.getNombre() + ", apellidos=" + participant1.getApellidos()
						+ ", fechaNacimiento=" + participant1.getFechaNacimiento() + ", email="
						+ participant1.getEmail() + ", DNI=" + participant1.getDNI() + ", direccion="
						+ participant1.getDireccion() + ", nacionalidad=" + participant1.getNacionalidad() + "]");
	}

	@Test
	public void domainModelHashCodeTest() {
		Participant participant1 = getParticipant.getParticipant("paco@hotmail.com");
		Participant participant3 = getParticipant.getParticipant("paco@hotmail.com");
		assertEquals(participant1.hashCode(), participant3.hashCode());
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

	@Test
	public void emailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("	", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("", "shfhs"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}
	
	@Test
	public void newEmailRequiredChange() {
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String emptyEmail = "{\"reason\": \"User email is required\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", ""), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "   "), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "	"), String.class);
		assertThat(response.getBody(), equalTo(emptyEmail));
	}
	
	@Test
	public void invalidEmailChange(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco", ""), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@", "   "), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail", "	"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));		
	}
	
	@Test
	public void newInvalidEmailChange(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String wrongEmailStyle = "{\"reason\": \"Wrong mail style\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "xhhuwi"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "fhgythf@"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "fhfyg@hotmail"), String.class);
		assertThat(response.getBody(), equalTo(wrongEmailStyle));		
	}
	
	@Test
	public void emailChangeUserNotFound(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String userNotFound = "{\"reason\": \"User not found\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pao@hotmail.com", "pac@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pee@gmail.com", "pepe@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pa@hotmail.com", "fhfyg@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(userNotFound));		
	}
	
	@Test
	public void sameEmailErrorChange(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String sameEmail = "{\"reason\": \"Same email\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "paco@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pepe@gmail.com", "pepe@gmail.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("carmen@yahoo.com", "carmen@yahoo.com"), String.class);
		assertThat(response.getBody(), equalTo(sameEmail));		
	}
	
	@Test
	public void emailChangeCorrect(){
		ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
		String userURI = base.toString() + "/changeEmail";
		String correctChange = "{\"Resultado\": \"Email actualizado correctamente\"}";

		response = template.postForEntity(userURI, new PeticionChangeEmailREST("paco@hotmail.com", "pac@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("pepe@gmail.com", "pepe@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));
		
		response = template.postForEntity(userURI, new PeticionChangeEmailREST("carmen@yahoo.com", "fhfyg@hotmail.com"), String.class);
		assertThat(response.getBody(), equalTo(correctChange));	
	}
	
	

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
}