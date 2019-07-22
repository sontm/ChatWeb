package sansanvn.web.chatweb.dto;

public class User {
	private int id;
	private String username;
	private String password;
	
	public User (int pid, String pname, String pwd) {
		id = pid;
		username = pname;
		password = pwd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
