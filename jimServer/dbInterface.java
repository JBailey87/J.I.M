package jimServer;
import java.sql.*;

import commonUtils.User;

public class dbInterface {
	public Connection con = null;
	private User newUser = null;
	
  public void connect() {
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance();
      this.con = DriverManager.getConnection("jdbc:mysql:///instant_messenger", "root", "bv$r36w");

      if(!this.con.isClosed()){
        System.out.println("Successfully connected to MySQL server...");
      }

    } catch(Exception e) {
      System.err.println("Exception: " + e.getMessage());
    } 
  }
  
  public boolean cheackId(String[] loginDetails){
	  try {
	        // Create a result set containing the user details from users
	        Statement stmt = this.con.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + loginDetails[0] + "' AND password = '" + loginDetails[1] + "'");
	        while(rs.next()){
	        	this.newUser = new User(rs.getString("name"), rs.getString("nickName"), rs.getString("location"), rs.getString("status"), rs.getString("department"));
	        	this.newUser.setHostName(loginDetails[1] + "@" + loginDetails[2]);
	        	return true;
	        }
	    } catch (SQLException e) {
	    	System.out.println("error given");
	    	return false;
	    }
	    return false;
  }
  public User getUser(){
	return this.newUser;
  }
}