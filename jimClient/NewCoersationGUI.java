package jimClient;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import commonUtils.Message;

public class NewCoersationGUI {
	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NewCoersationGUI chat = new NewCoersationGUI();
		chat.go();
	}

	private static JTextArea incoming;
	private JTextField outgoing;

	private void go() {
		JFrame frame = new JFrame("JIM - Conversation");
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new java.awt.Color(230,251,255));
		this.incoming = new JTextArea(15, 30);
		this.incoming.setLineWrap(true);
		this.incoming.setWrapStyleWord(true);
		this.incoming.setEditable(false);

		
		JScrollPane qScroller = new JScrollPane(this.incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.outgoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new SendButtonListener());
		
		mainPanel.add(qScroller);
		mainPanel.add(outgoing);
		mainPanel.add(sendButton);
		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(400, 500);
		frame.setVisible(true);
	}
	public class SendButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent ev) {
			incoming.append(outgoing.getText());
			outgoing.setText("");
			outgoing.requestFocus();
			
		}
	}
	public static void addText(String messageText){
		incoming.append(messageText);
	}
}
