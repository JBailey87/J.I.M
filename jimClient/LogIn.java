package jimClient;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import commonUtils.Message;
import commonUtils.User;




/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class LogIn extends javax.swing.JFrame {

	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public JTextField userName;
	public JLabel userNameLabel;
	public JPasswordField password;
	public JButton signInButton;
	public JLabel passwordLabel;
	public JLabel image;
	public Socket connection;
	public static ObjectInputStream receiveObject;
	public static ObjectOutputStream sendObject;
	private Vector<User> contactList = null;
	private MainMenu mainMenu;
	public static User userDetails = null;
	private static String host;
	public static Vector<Conversation> conversations;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					host = InetAddress.getLocalHost().toString();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			LogIn inst = new LogIn();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				inst.setSize(400, 338);
			}
		});
	}
	
	public LogIn() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(230,251,255));
			this.setTitle("JIM - Log In");
			{
				this.userName = new JTextField();
				getContentPane().add(this.userName);
				this.userName.setBounds(142, 36, 77, 22);
			}
			{
				this.userNameLabel = new JLabel();
				getContentPane().add(this.userNameLabel);
				this.userNameLabel.setText("Username");
				this.userNameLabel.setBounds(64, 36, 69, 16);
			}
			{
				this.passwordLabel = new JLabel();
				getContentPane().add(this.passwordLabel);
				this.passwordLabel.setText("Password");
				this.passwordLabel.setBounds(64, 76, 66, 16);
			}
			{
				this.signInButton = new JButton();
				getContentPane().add(this.signInButton);
				this.signInButton.setText("Sign In");
				this.signInButton.setBounds(142, 119, 72, 27);
				this.signInButton.addActionListener(new signInListener());
			}
			{
				this.password = new JPasswordField();
				getContentPane().add(this.password);
				this.password.setBounds(142, 73, 77, 22);
			}
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public class signInListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			conversations = new Vector<Conversation>();
			connectToServer();
			setupStreams();
			sendLoginDetails();
			Thread t = new Thread(new IncomingReader());
			t.start();
		}
	}
	public void connectToServer(){
		try {
			this.connection = new Socket("127.0.0.1", 4444);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setupStreams(){
		try {
			this.receiveObject = new ObjectInputStream(this.connection.getInputStream());
			this.sendObject = new ObjectOutputStream(this.connection.getOutputStream());
			System.out.println("Streams setup");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendLoginDetails(){
		
		String[] loginDetails = new String[3];
		loginDetails[0] = this.userName.getText();
		loginDetails[1] = this.password.getText();
		loginDetails[2] = this.host;
		
		try {
			this.sendObject.writeObject(loginDetails);
			this.sendObject.flush();
			System.out.println("login details sent");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void acceptedUser(Message msg){
		Vector<User> temp = new Vector<User>();
		temp = msg.getMessage();
		this.userDetails = temp.get(0);
		System.out.println("new user set");
	}
	public void populateContactList(Message msg){
		this.contactList = new Vector<User>();
		this.contactList.addAll(msg.getMessage());
		System.out.println("new list populated");
		callMainMenu();
	}
	public void updateContactList(Message msg){
		Vector<User> temp = new Vector<User>();
		temp = msg.getMessage();
		this.contactList.add(temp.elementAt(0));
		System.out.println("new user added to current list");
		updateGUI();
	}
	public void errorFlag(){
		UnknownUser error = new UnknownUser();
		error.main();
		userName.setText("");
		password.setText("");
	}
	public void callMainMenu(){
		this.mainMenu = new MainMenu(this.userDetails, this.contactList);
		this.mainMenu.main(this.userDetails, this.contactList);
		this.hide();
	}
	public class IncomingReader implements Runnable{
		public void run() {
			Message msg;
			Vector<User> temp;
			try {
				
				while((msg = (Message) receiveObject.readObject()) != null){
					if(msg.getType().equalsIgnoreCase("ERROR")){
						System.out.println("error");
						errorFlag();
					}else if(msg.getType().equalsIgnoreCase("userDetails")){
						System.out.println("userdetails recieved");
						acceptedUser(msg);
					}else if(msg.getType().equalsIgnoreCase("contactList")){
						System.out.println("contactList recieved");
						populateContactList(msg);
					}else if(msg.getType().equalsIgnoreCase("newUser")){
						System.out.println("new user recieved");
						updateContactList(msg);
					}else if(msg.getType().equalsIgnoreCase("message")){
						System.out.println("message recieved");
						messageHandler(msg);
					}
				}
			} catch (IOException e) {
				System.out.println("error 1");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("error 2");
				e.printStackTrace();
			}
		}
	}
	public void updateGUI(){
		this.mainMenu.updateUsers(this.contactList);
		System.out.println("GUI update called");
	}
	public static void signOut() {
		System.out.println("signOut called");
		System.exit(0);
	}
	private void messageHandler(Message msg){
		boolean found = false;
		String msgDestination = msg.getSource();
		System.out.println("number of conversations " + this.conversations.size());
		if(this.conversations.size() != 0){
			for(Conversation c:this.conversations){
				Vector<User> temp = c.getParticitpants();
				for(User u:temp){
					System.out.println("perticipant = " + u.getNickName());
					if(u.getHostName().equalsIgnoreCase(msgDestination)){
						c.updateConversation(msg);
						found = true;
						System.out.println("window found");
					}
				}
			}
		}else{
			System.out.println("new window needed");
			String messageText = msg.getMessage().toString();
			System.out.println("message text " + messageText);
			for(User u:this.contactList){
				if(u.getHostName().equalsIgnoreCase(msg.getSource())){
					//Conversation convo = new Conversation(u);
					//convo.main(u);
					//convo.setTextRecieved(messageText);
					NewCoersationGUI convo = new NewCoersationGUI();
					convo.main(null);
					convo.addText(messageText);
					//LogIn.conversations.add(convo);
				}
			}
			
			
		}
	}
}
