package jimServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import commonUtils.*;


public class Server {
	private dbInterface databaseConnection;
	private ServerSocket serverSocket;
	private Socket connection;
	private ObjectOutputStream sendObjects = null;
	private ObjectInputStream recieveObjects = null;
	private static Vector<User> contactList;
	private static Vector<Stream> streamList;
	//private users newUser;
	
	public static void main(String[] args) {
		Server newServer = new Server();
		newServer.startUp();
	}
	private void startUp(){
		System.out.println("Server Starting Up");
		Server.contactList = new Vector<User>();
		Server.streamList = new Vector<Stream>();
		databaseConnect();
		setupPortListener();
		runServer();
	}
	private void databaseConnect(){
		//connects to the database
		try{
			this.databaseConnection = new dbInterface();
			this.databaseConnection.connect();
			System.out.println("Successfully connected to database");
		}catch(Exception e){
			System.out.println("could not conenct to database");
		}
	}
	private void setupPortListener(){
		try {
			this.serverSocket = new ServerSocket(4444);
			System.out.println("Socket setup");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setupStreams(){
		try {
			//sets up the streams for the connection
			this.sendObjects = new ObjectOutputStream(this.connection.getOutputStream());
			this.recieveObjects = new ObjectInputStream(this.connection.getInputStream());
			System.out.println("streams setup");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void runServer(){
		try {
			while(true){
				this.connection = this.serverSocket.accept();
				setupStreams();
				String[] loginDetails = (String[]) recieveObjects.readObject();
				
				if(databaseConnection.cheackId(loginDetails)){
					User newUser = this.databaseConnection.getUser(); //creates a new global users object and populates it with the users details returned from the database
					
					sendUserDetails(newUser);
					Server.contactList.add(newUser);
					sendNewUserContactList();
					updateCurrentUsers(newUser);
					addNewStream(newUser);
					
					
					Thread listener = new Thread(new ClientHandler());
					listener.start();
				}else{
					System.out.println("Invalid users");
					invalidUser();
				}
				for(User u:Server.contactList){
					System.out.println("users from server list" + u.getNickName());
				}
				System.out.println("server running correctly");
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}
	private void invalidUser() {
		Vector<String> temp = new Vector<String>();
		Message msg = new Message("ERROR", temp);
		try {
			this.sendObjects.writeObject(msg);
			this.sendObjects.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	private void addNewStream(User newUser) {
		Stream newStream = new  Stream(newUser, this.sendObjects, this.recieveObjects);
		this.streamList.add(newStream);
		
	}
	private void updateCurrentUsers(User newUser) {
		Vector<User> msgContent = new Vector<User>();
		msgContent.add(newUser);
		Message msg = new Message("newUser", msgContent);
		
		for(Stream s : this.streamList){
			try {
				s.getWrite().writeObject(msg);
				s.getWrite().flush();
			} catch (IOException e) {
				System.out.println("error sending newuser to contacts");
				e.printStackTrace();
			}
			
		}
		
	}
	private void sendNewUserContactList() {
		Message msg = new Message("contactList", Server.contactList);
		try{
			this.sendObjects.writeObject(msg);
			this.sendObjects.flush();
		}catch(Exception e){
			System.out.println("error sending contact list to new user");
		}
		
	}
	private void sendUserDetails(User newUser){
		try {
			Vector<User> msgContent = new Vector<User>();
			msgContent.add(newUser);
			Message msg = new Message("userDetails", msgContent);
			this.sendObjects.writeObject(msg);
			this.sendObjects.flush();
			System.out.println("user detials sent");
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error sending new user its details");
		}
	}
	private void userSignOut(Vector<User> user){
		User toBeRemoved = user.elementAt(0);
		
		for(int i=0; i<Server.contactList.size(); i++){
			if(Server.contactList.get(i).getHostName().equalsIgnoreCase(toBeRemoved.getHostName())){
				Server.contactList.remove(i);
			}
		}
		for(int i=0; i<Server.streamList.size(); i++){
			if(Server.streamList.get(i).getUser().getHostName().equalsIgnoreCase(toBeRemoved.getHostName())){
				Server.streamList.remove(i);
			}
		}
	}
	private void messageHandler(Message msg){
		System.out.println("message handler");
		String destinationHost = msg.getDestination();
		System.out.println("destination = " + destinationHost);
		try{	
			for(Stream s:this.streamList){
				if(s.getUser().getHostName().equalsIgnoreCase(destinationHost)){
					System.out.println("destination host found");
					s.getWrite().writeObject(msg);
					s.getWrite().flush();
				}
			}
		}catch(Exception e){
			System.out.println("error sending message from server to destination");
		}
	}
	public class ClientHandler implements Runnable
	{		
		public void run()
		{
			Message msg;
			try {
				while((msg = (Message) recieveObjects.readObject()) != null){
					if(msg.getType().equalsIgnoreCase("signOut")){
						userSignOut(msg.getMessage());
					}else if(msg.getType().equalsIgnoreCase("message")){
						messageHandler(msg);
					}
				}
			} catch (IOException e) {
				System.out.println("error 1");
			} catch (ClassNotFoundException e) {
				System.out.println("error 2");
			}
			
		}
		
	}
}
