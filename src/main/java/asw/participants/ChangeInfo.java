package asw.participants;

import asw.participants.webService.request.PeticionChangeEmailREST;
import asw.participants.webService.request.PeticionChangePasswordREST;

public interface ChangeInfo {
	public Object changePassword(String accept, PeticionChangePasswordREST datos);

	public Object changeEmail(String accept, PeticionChangeEmailREST datos);
}
