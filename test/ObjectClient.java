package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import commonUtils.User;


public class ObjectClient {
	public static void main(String[] args) {
		ObjectClient client = new ObjectClient();
		client.run();
	}
	public void run(){
		try {
			Socket connection = new Socket("127.0.0.1", 4444);
			ObjectOutputStream sendObject = new ObjectOutputStream(connection.getOutputStream());
			sendObject.writeObject(new User("ginge", "neil", "here", "there", "everywhere"));
			
			ObjectInputStream objectIn = new ObjectInputStream(connection.getInputStream());
			User revieved = (User) objectIn.readObject();
			
			System.out.println("object gotten");
			System.out.println("name? " + revieved.getGivenName());
			
		} catch (UnknownHostException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
