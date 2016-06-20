package gov.va.vba;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserDAO {
	
	private int User_ID;
	private int Role_ID;
	private String User_Name;
	private String User_Pswd;
	private String User_Stas;
	
	public int getUserId() {
		return User_ID;
	}
	
	public void setUserId(int id) {
		this.User_ID = id;
	}
	
	public int getRoleId() {
		return Role_ID;
	}
	
	public void setRoleId(int id) {
		this.Role_ID = id;
	}
	
	public String getUserName() {
		return User_Name;
	}
	
	public void setUserName(String name) {
		this.User_Name = name;
	}
	
	public String getUserPW() {
		return User_Pswd;
	}
	
	public void setUserPW(String pswd) {
		this.User_Pswd = pswd;
	}
	
	public String getUserStatus() {
		return User_Stas;
	}
	
	public void setUserStatus(String status) {
		this.User_Stas = status;
	}
}