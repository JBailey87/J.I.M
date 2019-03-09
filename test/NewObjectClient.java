package test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import commonUtils.User;

public class NewObjectClient extends javax.swing.JFrame {

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
	public NewObjectClient() {
		super();
		initGUI();
		print();
	}
	
	private static ObjectInputStream recieveObjects;
	private void print() {
		Socket connection;
		try {
			
			connection = new Socket("127.0.0.1", 4444);
			ObjectOutputStream sendObject = new ObjectOutputStream(connection.getOutputStream());
			NewObjectClient.recieveObjects = new ObjectInputStream(connection.getInputStream());
			System.out.println("streams created at client");
			sendObject.writeObject(new User("ginge", "jon", "here", "there", "everywhere"));
			System.out.println("user sent");
			
			Vector<User> msg = (Vector<User>)recieveObjects.readObject();
			System.out.println("vector size " + msg.size());
			
			
			
			Thread msgListener = new Thread(new msgListener());
			msgListener.start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
	
	public class msgListener implements Runnable{
		@Override
		public void run() {
			
			try {
				Vector<User> msg = null;
				while((msg = (Vector<User>)recieveObjects.readObject()) != null){
					System.out.println("not null");
					System.out.println("msg at client " + msg.size());
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}	
	}
}
