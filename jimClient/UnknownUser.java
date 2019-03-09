package jimClient;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;


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
public class UnknownUser extends javax.swing.JFrame {
	private JButton Exit;
	private JLabel ErrorMsg;

	{
		//Set Look & Feel
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				UnknownUser inst = new UnknownUser();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public UnknownUser() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(230,251,255));
			this.setTitle("JIM - Incorrect Login");
			{
				Exit = new JButton();
				getContentPane().add(Exit);
				Exit.setText("Close");
				Exit.setBounds(157, 111, 59, 23);
				Exit.addActionListener(new buttonClick());
			}
			{
				ErrorMsg = new JLabel();
				getContentPane().add(ErrorMsg);
				ErrorMsg.setText("Username and/or Password are incorrect");
				ErrorMsg.setBounds(10, 63, 373, 14);
				ErrorMsg.setHorizontalAlignment(SwingConstants.CENTER);
				ErrorMsg.setHorizontalTextPosition(SwingConstants.CENTER);
			}
			pack();
			this.setSize(399, 206);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}public class buttonClick implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}

}
