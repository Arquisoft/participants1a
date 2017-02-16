package asw;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import asw.dbManagement.model.Participant;
import asw.dbManagement.repository.ParticipantRepository;

@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class Application {	
	
	
	

	@Bean
	public CommandLineRunner initDB(ParticipantRepository repository) throws ParseException
	{
		// Formato para la fecha
		DateFormat formatter1 = new SimpleDateFormat("mm/DD/yyyy");
		
		return (args) -> {
			// Obtención de fecha
			Date fecha = (Date)formatter1.parse("08/16/1970");
			// Inserción en la base de datos
			repository.save(new Participant("Paco", "Gómez", "123456", fecha, "paco@hotmail.com", "12345678A", 
								"Calle Uría", "Española"));
			
			// Obtención de fecha
			Date fecha2 = (Date)formatter1.parse("02/23/1975");
			// Inserción en la base de datos
			repository.save(new Participant("Pepe", "Fernández", "123456", fecha2, "pepe@gmail.com", "87654321B", 
								"Calle Principal", "Española"));
			
			// Obtención de fecha
			Date fecha3 = (Date)formatter1.parse("08/16/1970");
			// Inserción en la base de datos
			repository.save(new Participant("Carmen", "López", "123456", fecha3, "carmen@yahoo.com", "11223344C", 
								"Calle Calvo Sotelo", "Española"));
			
			// Obtención de fecha
			Date fecha4 = (Date)formatter1.parse("02/23/1975");
			// Inserción en la base de datos
			repository.save(new Participant("Isabel", "Rodríguez", "123456", fecha4, "isabel@gmail.com", "22334455D", 
								"Avenida Galicia", "Española"));

			
		};
	}
	
	
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
   
}