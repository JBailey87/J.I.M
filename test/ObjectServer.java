package test;

import java.io.*;
import java.net.*;
import java.util.Vector;

import commonUtils.User;


public class ObjectServer {

	private static Vector<User> contactList;
	private static ServerSocket serverSocket;
	private static Vector<ObjectOutputStream> sendClientStreams;
	
	public static void main(String[] args) {
		ObjectServer tester = new ObjectServer();
		tester.startUp();
	}
	
	public void startUp(){
		System.out.println("Server Starting Up");
		ObjectServer.contactList = new Vector<User>();
		ObjectServer.sendClientStreams = new Vector<ObjectOutputStream>();
		setupPortListener();
		run();
	}
	public void setupPortListener(){
		try {
			this.serverSocket = new ServerSocket(4444);
			System.out.println("Socket setup");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void run(){
		
		try {
			while(true){
				Socket socket = serverSocket.accept();
				System.out.println("conected");
				
				ObjectInputStream recieveObjects = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream sendObjects = new ObjectOutputStream(socket.getOutputStream());
				
				User test = (User)recieveObjects.readObject();
				
				addUser(test);
				
				System.out.println("number of contacts in list" + ObjectServer.contactList.size());
				ObjectServer.sendClientStreams.add(sendObjects);
				
				writeToClients();
				
				Thread newThread = new Thread(new ClientHandler(socket));
				newThread.start();
				
				System.out.println("got a connection");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private synchronized void addUser(User test) {
		ObjectServer.contactList.add(test);
		
	}

	private synchronized void writeToClients() {
		for(ObjectOutputStream os : ObjectServer.sendClientStreams){
			try {
				os.writeObject(ObjectServer.contactList);
				os.flush();
				System.out.println("new user  sent to client ");
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		
	}
	public class ClientHandler implements Runnable{
		BufferedReader reader;
		Socket sock;
		
		public ClientHandler(Socket clientSocket){
			try {
				sock = clientSocket;
				InputStreamReader isReader;
				isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		public void run(){
			String msg;
			try {
				while((msg = reader.readLine()) != null){
					System.out.println("got this from client " + msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
