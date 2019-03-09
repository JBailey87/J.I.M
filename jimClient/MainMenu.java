package jimClient;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

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
public class MainMenu extends javax.swing.JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -309927124081731879L;
	private JLabel userName;
	private JLabel icon;
	private JButton signOut;
	private JTree userList;
	private JComboBox currentStatus;
	private static Vector<User> currentUsers;
	public static User userDetails;
	private boolean detailsRequest;
	private String selectedUser = null;

	{
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void main(final User userDetails, Vector<User> vector) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainMenu inst = new MainMenu(userDetails, currentUsers);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	public MainMenu(User userDetails, Vector<User> vector) {
		super();
		this.userDetails = userDetails;
		initGUI(userDetails, vector);
	}
	
	private void initGUI(User userDetails, Vector<User> vector) {
		try {
			MainMenu.currentUsers = vector;
			
				setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				getContentPane().setBackground(new java.awt.Color(230,251,255));
				getContentPane().setLayout(null);
				{
					userName = new JLabel();
					getContentPane().add(userName);
					userName.setBounds(40, 20, 109, 24);
					userName.setText("hello");
					userName.setFont(new java.awt.Font("Tahoma",1,16));
					userName.setHorizontalTextPosition(SwingConstants.RIGHT);
					userName.setHorizontalAlignment(SwingConstants.RIGHT);
					userName.setText(userDetails.getNickName());
				}
				{
					ComboBoxModel currentStatusModel = 
						new DefaultComboBoxModel(
								new String[] { "Online", "Offline", "Away", "Meeting", "Busy" });
					currentStatus = new JComboBox();
					getContentPane().add(currentStatus);
					currentStatus.setModel(currentStatusModel);
					currentStatus.setBounds(161, 24, 108, 20);
				}
				{
					ImageIcon image = new ImageIcon("jim.gif");
					icon = new JLabel(image);
					getContentPane().add(icon);
					icon.setBounds(10, 11, 70, 67);
				}
				{
					DefaultMutableTreeNode root = new DefaultMutableTreeNode("contacts");
					for(User u : MainMenu.currentUsers){
						DefaultMutableTreeNode child = new DefaultMutableTreeNode(u.getNickName());
						root.add(child);
					}
					userList = new JTree(root);
					userList.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
					getContentPane().add(userList);
					userList.setBounds(65, 89, 150, 215);
					userList.addTreeSelectionListener(new SelectionListener());
					userList.addMouseListener(new MouseListener(userList));
					
				}
				{
					signOut = new JButton();
					getContentPane().add(signOut);
					signOut.setText("Sign Out");
					signOut.setBounds(95, 315, 73, 23);
					signOut.addActionListener(new SignOut());
				}
				pack();
				this.setSize(295, 385);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updateUsers(Vector<User> newCurrentUsers){
		repaint();
		update(getGraphics());
		System.out.println("update completed before main");
		this.main(userDetails, newCurrentUsers);
		System.out.println("update completed");
	}
	class MouseListener extends MouseAdapter{
		private JTree contactList;
		
		public MouseListener (JTree contacts){
			this.contactList = contacts;
		}
		public void mouseClicked(MouseEvent e){
			detailsRequest = true;
			int selRow = this.contactList.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = this.contactList.getPathForLocation(e.getX(), e.getY());
			if(selRow > 0) {
				if(e.getClickCount() == 2) {
					System.out.println("conversation request with " + selectedUser);
					detailsRequest = false;
					startConversation();
				}else if(e.getClickCount() == 1 && detailsRequest){
					System.out.println("details request for " + selectedUser);
					detailsRequest = false;
				}
			}
		}
	}
	class SelectionListener implements TreeSelectionListener{
		public void valueChanged(TreeSelectionEvent se) {
			JTree tree = (JTree) se.getSource();
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
			    .getLastSelectedPathComponent();
			String selectedNodeName = selectedNode.toString();
			if (selectedNode.isLeaf()) {
				selectedUser = selectedNodeName;
			}
		}
		
	}
	class SignOut implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				Vector<User> temp = new Vector<User>();
				temp.add(userDetails);
	 			Message msg = new Message("signOut", temp);
	 			LogIn.sendObject.writeObject(msg);
	 			LogIn.sendObject.flush();
	 			LogIn.signOut();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void startConversation(){
		for(int i=0; i<this.currentUsers.size(); i++){
			if(this.currentUsers.get(i).getNickName().equalsIgnoreCase(this.selectedUser)){
				Conversation convo = new Conversation(this.currentUsers.get(i));
				convo.main(this.currentUsers.get(i));
				LogIn.conversations.add(convo);
				//NewCoersationGUI convo = new NewCoersationGUI();
				//convo.main(null);
				//LogIn.conversations.add(convo);
			}
		}
		
		
	}
}
