package jimClient;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import commonUtils.User;

import sun.awt.HorizBagLayout;

public class NewGUI {
	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private JTextArea incoming;
	private JTextField outGoing;
	public ArrayList<String> treeItems;
	public JTree tree;
	public JPanel mainPanel;
	private DefaultListModel model;
	private DefaultMutableTreeNode root;
	
	public static void main(String[] args){
		NewGUI gui = new NewGUI();
		gui.initgo();
	}
	private void initgo() {
		
		treeItems = new ArrayList<String>();
		treeItems.add("first");
		treeItems.add("second");
		JFrame frame = new JFrame("JIM - Java Instant Messenger");
		frame.setBackground(new java.awt.Color(230,251,255));
		mainPanel = new JPanel();
		this.incoming = new JTextArea(15,30);
		this.incoming.setLineWrap(true);
		this.incoming.setWrapStyleWord(true);
		this.incoming.setEditable(false);
		
		JScrollPane qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.outGoing = new JTextField(20);
		JButton sendButton = new JButton("Send");
		sendButton.addActionListener(new sendButtonListener());
		mainPanel.add(qScroller);
		mainPanel.add(outGoing);
		mainPanel.add(sendButton);
		
		this.model = new DefaultListModel();
		model.addElement("test");
		JList list = new JList(model);
		
		mainPanel.add(list);
		
		
		
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(350,400);
		frame.setVisible(true);
	}
	public class sendButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			incoming.append(outGoing.getText() + "\n");
			treeItems.add("h");
			model.addElement("more!");
			
		}
	}
}
