package commonUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class User implements Serializable{
	private String nickName;
	private String givenName;
	private String Location;
	private String status;
	private String department;
	private String hostName;
	
	public User(String name, String nickName, String location, String status,
			String department) {
		this.givenName = name;
		this.nickName = nickName;
		this.Location = location;
		this.status = status;
		this.department = department;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public String getLocation() {
		return Location;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public String getDepartment(){
		return this.department;
	}
	public void setDepartment(String department){
		this.department = department;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostName() {
		return hostName;
	}
}
