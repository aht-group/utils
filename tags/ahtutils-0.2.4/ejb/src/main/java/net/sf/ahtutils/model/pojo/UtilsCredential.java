package net.sf.ahtutils.model.pojo;

public class UtilsCredential
{
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Fields<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	private String username;
	private String password;
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Class<<<<<<<<<<<<<<<<<<<<<<<<<<<
	
	public UtilsCredential(String username, String password)
	{
		this.username=username;
		this.password=password;
	}	
		
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Getters and Setters<<<<<<<<<<<<<<<<<
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	// >>>>>>>>>>>>>>>>>>>>>>>>>>Methods<<<<<<<<<<<<<<<<<<<<<<<<<<<	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("username: ").append(username);
		sb.append(" ");
		sb.append("password: ").append(password);
		return sb.toString();
	}
}