package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;

import commonUtils.User;

class InstantMessengerServer{
	public ArrayList<PrintWriter> clientOutputs;
	public ArrayList<User> users;
	public PrintWriter send;
	public InputStreamReader recieve;
	public User newUser;
	
	public static void main(String[] args){
		InstantMessengerServer server = new InstantMessengerServer();
		server.serverStart();
	}
	
	public void serverStart(){
		try {
			dbInterface databaseConnection = new dbInterface();
			databaseConnection.connect();
			
			this.clientOutputs = new ArrayList<PrintWriter>();
			this.users = new ArrayList<User>();
			ServerSocket ServerSocket = new ServerSocket(4444);
			System.out.println("Server started");
			
			while(true){
				Socket clientServerSocket = ServerSocket.accept();
				
				//sets up send and receive print streams between server and client.
				this.send = new PrintWriter(clientServerSocket.getOutputStream());
				this.recieve = new InputStreamReader(clientServerSocket.getInputStream());
				BufferedReader recieveReader = new BufferedReader(this.recieve);
				
				String usernameAndPassword = recieveReader.readLine();
				
				if(databaseConnection.cheackId(usernameAndPassword) == false){
					System.out.println("wasnt found in database");
				}else{
					System.out.println("found");
					this.newUser = databaseConnection.getUser();
					this.users.add(databaseConnection.getUser());
					
					//sends users details to client
					this.send.println(this.newUser.getNickName());
					this.send.flush();
					this.send.println(this.newUser.getStatus());
					this.send.flush();
					
					//creates an object output stream.
					//ObjectOutputStream objectSender = new ObjectOutputStream(clientServerSocket.getOutputStream());
					//objectSender.writeObject(this.newUser);
					//objectSender.flush();
					//objectSender.close();
					//System.out.println("objects sent");
					
					
					System.out.println("network established");
				}
				this.clientOutputs.add(this.send);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}