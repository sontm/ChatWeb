package sansanvn.web.chatweb;

public class AppConstants {
	public static int ROLE_USER = 0;
	public static int ROLE_ADMIN = 1;
	
	public static String GetRoleNameOf(int roleCode) {
		if (roleCode == ROLE_ADMIN) {
			return "ROLE_ADMIN";
		}
		return "ROLE_USER";
	}
}
