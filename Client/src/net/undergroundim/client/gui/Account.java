package net.undergroundim.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;

import net.undergroundim.client.Constants;
import net.undergroundim.client.networking.PacketHeaders;


/**
 * 
 * @author Troy
 *
 */
public class Account extends JDialog implements ActionListener{
	private static final long serialVersionUID = 2563111246123391750L;
	
	private JPanel passwordPanel = new JPanel();
	private Border passwordBorder = BorderFactory.createTitledBorder("Change Password");
	
	private JLabel currentPasswordLabel = new JLabel("Current Password:");
	private JLabel newPasswordLabel = new JLabel("New Password:");
	private JLabel newPasswordLabelConfrim = new JLabel("Confirm Password:");
	
	private JPasswordField currentPassword = new JPasswordField();
	private JPasswordField newPassword = new JPasswordField();
	private JPasswordField newPasswordConfirm = new JPasswordField();
	
	private JButton saveButton = new JButton("Save");
	private JButton cancelButton = new JButton("Cancel");
	
	public Account(){
		this.setIconImage(Constants.accountIcon);
		this.setTitle("Account Settings");
		this.setSize(357, 170);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		passwordPanel.setLayout(null);
		passwordPanel.setBounds(5, 5, 340, 100);
		passwordPanel.setBorder(passwordBorder);
		
		currentPasswordLabel.setBounds(10, 20, 120, 20);
		newPasswordLabel.setBounds(10, 45, 120, 20);
		newPasswordLabelConfrim.setBounds(10, 70, 120, 20);
		
		currentPassword.setBounds(130, 20, 200, 20);
		newPassword.setBounds(130, 45, 200, 20);
		newPasswordConfirm.setBounds(130, 70, 200, 20);
		
		passwordPanel.add(currentPasswordLabel);
		passwordPanel.add(newPasswordLabel);
		passwordPanel.add(newPasswordLabelConfrim);
		passwordPanel.add(currentPassword);
		passwordPanel.add(newPassword);
		passwordPanel.add(newPasswordConfirm);
		
		cancelButton.setBounds(7, 110, 100, 25);
		saveButton.setBounds(243, 110, 100, 25);
		
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		this.add(passwordPanel);
		this.add(cancelButton);
		this.add(saveButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton){
			currentPassword.setText("");
			newPassword.setText("");
			newPasswordConfirm.setText("");
			this.dispose();
		}
		
		else if(e.getSource() == saveButton){
			if(Constants.convertPassword(newPassword.getPassword()).equals(Constants.convertPassword(newPasswordConfirm.getPassword()))){
				Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_PASSWORD.getHeader() + "º" +
						Constants.getHash(Constants.convertPassword(currentPassword.getPassword())) + "º" +
						Constants.getHash(Constants.convertPassword(newPassword.getPassword())));
				
				currentPassword.setText("");
				newPassword.setText("");
				newPasswordConfirm.setText("");
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(null,
						"Your passwords do not match.",
						"Password Error",
						JOptionPane.ERROR_MESSAGE);
				
				currentPassword.setText("");
				newPassword.setText("");
				newPasswordConfirm.setText("");
			}
		}
	}

}
