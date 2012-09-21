package com.underground.im.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.underground.im.client.Constants;

/**
 * 
 * @author Troy
 * 
 *
 */
public class Login extends JFrame implements ActionListener, KeyListener{
	private static final long serialVersionUID = -6567994410373169391L;
	
	private JLabel serverLabel = new JLabel("Server IP/Port:");
	private JLabel passwordLabel = new JLabel("Server Password:");
	private JLabel usernameLabel = new JLabel("Username:");
	private JLabel userPasswordLabel = new JLabel("Password:");
	
	public JTextField serverField = new JTextField();
	public JTextField usernameField = new JTextField();
	public JPasswordField passwordField = new JPasswordField();
	private JPasswordField userPasswordField = new JPasswordField();
	
	private JButton loginButton = new JButton("Connect");
	private JButton registerButton = new JButton("Register");
	
	/**
	 * Construct a new login screen.
	 */
	public Login(){
		this.setIconImage(Constants.icon);
		this.setTitle("Underground IM");
		this.setSize(250, 172);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		
		serverLabel.setBounds(10, 10, 90, 20);
		passwordLabel.setBounds(10, 35, 110, 20);
		usernameLabel.setBounds(10, 60, 80, 20);
		userPasswordLabel.setBounds(10, 85, 80, 20);
		
		serverField.setBounds(120, 10, 115, 20);		
		passwordField.setBounds(120, 35, 115 ,20);
		usernameField.setBounds(120, 60, 115, 20);
		userPasswordField.setBounds(120, 85, 115, 20);
		
		loginButton.setBounds(125, 110, 110, 25);
		registerButton.setBounds(10, 110, 110, 25);
		
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		userPasswordField.addKeyListener(this);
		
		this.add(serverLabel);
		this.add(usernameLabel);
		this.add(passwordLabel);
		this.add(userPasswordLabel);
		this.add(serverField);
		this.add(usernameField);
		this.add(passwordField);
		this.add(userPasswordField);
		this.add(loginButton);
		this.add(registerButton);
		
		serverField.setText(Constants.getLastServer());
		usernameField.setText(Constants.getLastUsername());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!serverField.getText().contains(":")){
			JOptionPane.showMessageDialog(null,
					"You have entered a invaild server you must include the port.\n" +
					"Example: 127.0.0.1:5632",
					"Address Error",
					JOptionPane.ERROR_MESSAGE);
		}else{
			String[] ipaddy = serverField.getText().split(":");
			
			if(e.getSource() == loginButton){
				login();
			}
			
			else if(e.getSource() == registerButton){
				if(!Constants.convertPassword(userPasswordField.getPassword()).isEmpty()){
					Constants.getPacketManager().connect(ipaddy[0].trim(), 
							Constants.getHash(Constants.convertPassword(passwordField.getPassword())),
							usernameField.getText(),
							Constants.getHash(Constants.convertPassword(userPasswordField.getPassword())),
							Integer.parseInt(ipaddy[1].trim()),
							false);
				}else{
					JOptionPane.showMessageDialog(null,
						    "You must enter a password to register.",
							"Registration Failed",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	/**
	 * Login method.
	 */
	public void login(){
		String[] ipaddy = serverField.getText().split(":");
		
		if(!usernameField.getText().startsWith("Server")){
			Constants.getPacketManager().connect(ipaddy[0].trim(), 
					Constants.getHash(Constants.convertPassword(passwordField.getPassword())),
					usernameField.getText(),
					Constants.getHash(Constants.convertPassword(userPasswordField.getPassword())),
					Integer.parseInt(ipaddy[1].trim()),
					true);
		}else{
			JOptionPane.showMessageDialog(null,
					"This name is unavailable, please choose another name.",
					"Username Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Start the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args){	
		Constants.setEmotions();
		Constants.setLoginGUI(new Login());
		Constants.getLoginGUI().setVisible(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER:
			login();
			break;
		}
		
	}

	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

}