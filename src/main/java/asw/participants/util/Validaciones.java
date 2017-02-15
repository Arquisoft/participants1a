package asw.participants.util;

import java.util.Date;

public class Validaciones {
	///////////////////////////////////////////////////////////////
	///															//										
	///		Clase creada para realizar funciones que no        //
	///		tengan que ver con la lógica de los controladores //
	///                                                      //
	//////////////////////////////////////////////////////////
	
	
	
	
	
	public boolean validarCorreo(String mail){
		String[] mailSplit = mail.split("@");
		if(mailSplit.length!=2 || mailSplit[0].length()==0){
			return false;
		}
		mailSplit = mail.split(".");
		if(mailSplit.length!=2 || mailSplit[0].length()==0 || mailSplit[1].length()==0){
			return false;
		}
		
		return true;
	}
	
	public int averiguarEdad(Date fecha){
		
		
		
		return 0;
		
	}
	
	
	

}
