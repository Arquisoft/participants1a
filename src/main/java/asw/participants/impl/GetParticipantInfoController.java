package asw.participants.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asw.participants.GetParticipantInfo;
import asw.participants.factory.ErrorFactory;
import asw.participants.util.Validaciones;

@Controller
public class GetParticipantInfoController implements GetParticipantInfo{

	
	@Autowired
	private Validaciones validaciones;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model){
		return "login";
	}
	

	
	
	@Override
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public String getLogin(@RequestParam String mail,@RequestParam String password,Model model) {
		
		//@Complete implementar error mail con formato incorrecto
		//if(!validaciones.validarCorreo(mail)){
		//	throw ErrorFactory.getError()
		//}
		
		
		
		
		/*
		 * 
		 * @Complete
		 * 
		 * Comprobación de que el participante existe en la base de datos
		 * 
		 * 
		 * GetParticipant(mail,password);
		 * if(//usuario no encontrado){
		 * 
		 * throw ErrorFactory.getError(Errors.USER_NOT_FOUND)
		 * }else{
		 *  implemtnacion
		 * }
		 */
		
		/*
		 * 
		 * model.addAttribute("nombre",participant.getNombre);
		 * (seguir con el restor de atributos)
		 * 
		 * 
		 * 
		 */
		
		
		
		return "participantInfo";
		
				
		
	}
	
	
	
	

}
