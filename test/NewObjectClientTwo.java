package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import commonUtils.User;

public class NewObjectClientTwo extends javax.swing.JFrame {

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				NewObjectClient inst = new NewObjectClient();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public NewObjectClientTwo() {
		super();
		initGUI();
		print();
	}
	
	private void print() {
		Socket connection;
		try {
			
			connection = new Socket("127.0.0.1", 4444);
			User jonuser = new User("jon", "bailey", "here", "there", "everywhere");
			ObjectOutputStream sendObject = new ObjectOutputStream(connection.getOutputStream());
			sendObject.writeObject(jonuser);
			System.out.println("user sent");
			
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			pack();
			setSize(400, 300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
