package jimClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.EventListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import org.w3c.dom.UserDataHandler;

import com.sun.org.apache.xerces.internal.impl.RevalidationHandler;

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
public class Conversation extends javax.swing.JFrame {
	private JTextField sendText;
	private static JTextArea conversation;
	private JButton send;
	private JButton sendFile;
	private JTextArea involvedUsers;
	private Vector<User> participants;

	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(final User user) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Conversation inst = new Conversation(user);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public Conversation(User user) {
		super();
		this.participants = new Vector<User>();
		this.participants.removeAll(participants);
		this.participants.add(user);
		this.participants.add(MainMenu.userDetails);
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setBackground(new java.awt.Color(230,251,255));
			getContentPane().setLayout(null);
			this.setResizable(false);
			this.setTitle("JIM - Conversation");
			this.setDefaultLookAndFeelDecorated(true);
			{
				sendText = new JTextField();
				getContentPane().add(sendText);
				sendText.setBounds(23, 204, 283, 48);
			}
			{
				conversation = new JTextArea();
				getContentPane().add(conversation);
				conversation.setBounds(23, 23, 283, 169);
				conversation.setForeground(new java.awt.Color(0,0,0));
				conversation.setEditable(false);
				conversation.setLineWrap(true);
				conversation.setWrapStyleWord(true);
				conversation.addPropertyChangeListener(new listener());
			}
			{
				send = new JButton();
				getContentPane().add(send);
				send.setText("Send");
				send.setBounds(342, 203, 80, 25);
				send.addActionListener(new SendListener());
			}
			{
				involvedUsers = new JTextArea();
				
				involvedUsers.setBounds(342, 23, 80, 169);
				involvedUsers.setEditable(false);
				involvedUsers.setForeground(new java.awt.Color(0,0,0));
				for(User u:this.participants){
					involvedUsers.append(u.getNickName() + "\n");
				}
				getContentPane().add(involvedUsers);
			}
			{
				sendFile = new JButton();
				getContentPane().add(sendFile);
				sendFile.setText("Send a File");
				sendFile.setBounds(331, 239, 101, 26);
			}
			pack();
			this.setSize(452, 310);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateConversation(Message msg){
		Vector temp = new Vector();
		temp = msg.getMessage();
		conversation.append((String) temp.get(0) + "\n");
		System.out.println("new message for conversastion recieved " + temp.get(0));
	}
	public Vector<User> getParticitpants(){
		return participants;
	}
	class SendListener implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			try {
				System.out.println("to send " + sendText.getText());
				for(User u:participants){	
					System.out.println("participants = " + u.getNickName());
					Vector<String> msgContent = new Vector<String>();
					msgContent.add(sendText.getText());
					Message toSend = new Message("message", msgContent);
					toSend.setDestination(u.getHostName());
					toSend.setSource(LogIn.userDetails.getHostName());
					LogIn.sendObject.writeObject(toSend);
					LogIn.sendObject.flush();
				}
				sendText.setText("");
				conversation.append(sendText.getText());
			} catch (IOException e) {
				System.out.println("error sending message");
				e.printStackTrace();
			}
		}
	}
	public static void setTextRecieved(String msg){
		conversation.append(msg);
		System.out.println(msg);
	}
	class listener implements PropertyChangeListener{

		@Override
		public void propertyChange(PropertyChangeEvent arg0) {
			repaint();
			update(getGraphics());
			getContentPane().update(getGraphics());
			System.out.println("property changed");
		}
		
	}
}
