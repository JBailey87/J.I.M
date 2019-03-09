package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import javax.swing.*;

class InstantMessengerClientConversation{
	public JTextArea incoming;
	public JTextField outgoing;
	public PrintWriter writer;
	public BufferedReader reader;
	public JFrame frame;
	public JPanel mainWindow;
	
	public static void main(String[] args){
		InstantMessengerClientConversation client = new InstantMessengerClientConversation();
		client.run();
	}
	
	public void run(){
		GUIsetup();
		networkConnect();
		
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		this.frame.getContentPane().add(BorderLayout.CENTER, this.mainWindow);
		this.frame.setSize(400,500);
		this.frame.setVisible(true);
	}
	
	public void GUIsetup(){
		this.frame = new JFrame("JIM! - Java Instant Messenger");
		this.mainWindow = new JPanel();
		this.incoming = new JTextArea(15,50);
		this.incoming.setLineWrap(true);
		this.incoming.setWrapStyleWord(true);
		this.incoming.setEditable(false);
		JScrollPane scroller = new JScrollPane(this.incoming);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		this.outgoing = new JTextField(20);
		JButton send = new JButton("Send");
		send.addActionListener(new sendListener());
		
		mainWindow.add(this.incoming);
		mainWindow.add(this.outgoing);
		mainWindow.add(send);
	}
	
	public void networkConnect(){
		try {
			Socket chatSocket = new Socket("127.0.0.1", 4444);
			InputStreamReader stream = new InputStreamReader(chatSocket.getInputStream());
			
			//read messages
			this.reader = new BufferedReader(stream);
			
			//send messages
			this.writer = new PrintWriter(chatSocket.getOutputStream());
			System.out.println("network established");
			
		} catch (UnknownHostException e) {
			System.out.println("network connection error1");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("network connection error2");
			e.printStackTrace();
		}
	}
	public class sendListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try{
				writer.println(outgoing.getText());
				writer.flush();
			}catch(Exception e){
				System.out.println("error with listener");
			}
			outgoing.setText("");
			outgoing.requestFocus();
		}

	}
	
	public class IncomingReader implements Runnable {
		public void run() {
			String message;
			try{
				while((message = reader.readLine()) != null){
					System.out.println("read " + message);
					incoming.append(message + "\n");
					incoming.equals("help");
					System.out.println("end of msg");
				}
			}catch(Exception e){
				System.out.println("error with reader");
			}
		}
	}
}