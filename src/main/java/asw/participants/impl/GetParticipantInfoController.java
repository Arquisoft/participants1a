package asw.participants.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import asw.dbManagement.GetParticipant;
import asw.dbManagement.model.Participant;
import asw.participants.GetParticipantInfo;
import asw.participants.factory.ErrorFactory;
import asw.participants.factory.ErrorFactory.Errors;
import asw.participants.util.Validaciones;

@Controller
public class GetParticipantInfoController implements GetParticipantInfo{

	
	@Autowired
	private Validaciones validaciones;
	
	@Autowired
	private GetParticipant getParticipant;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String inicalicerLogin(Model model){
		return "login";
	}
	

	
	
	@Override
	@RequestMapping(value ="/login", method = RequestMethod.POST)
	public String getLogin(@RequestParam String mail,@RequestParam String password,Model model) {
		
		//@Clean
		
		Participant participant = getParticipant.getParticipant(mail);
		
		
		if(!validaciones.validarCorreo(mail)){
			throw ErrorFactory.getError(Errors.WRONG_EMAIL_STYLE);
		}
		if(!password.equals(participant.getPassword())){
			throw ErrorFactory.getError(Errors.INCORRECT_PASSWORD);
		}
		
		model.addAttribute("",participant.getNombre());
		
		
		
		
		
		return "participantInfo";
		
				
		
	}
	
	
	
	

}
