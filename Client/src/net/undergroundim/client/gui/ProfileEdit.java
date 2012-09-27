package net.undergroundim.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.undergroundim.client.Constants;
import net.undergroundim.client.networking.PacketHeaders;


/**
 * 
 * @author Troy
 *
 */
public class ProfileEdit extends JDialog implements ActionListener{
	private static final long serialVersionUID = 3541155790509935847L;

	private JLabel usernameLabel = new JLabel("Username:");
	private JLabel screennameLabel = new JLabel("Screen Name:");
	private JLabel emailLabel = new JLabel("Email Address:");
	private JLabel ageLabel = new JLabel("Age:");
	private JLabel sexLabel = new JLabel("Sex:");
	private JLabel locationLabel = new JLabel("Location:");
	private JLabel aboutLabel = new JLabel("About:");
	
	private String[] gender = new String[]{"N\\A","Male","Female"};
	
	private JTextField username = new JTextField();
	private JTextField screenname = new JTextField();
	private JTextField email = new JTextField();
	private JTextField age = new JTextField();
	private JComboBox<String> sex = new JComboBox<String>(gender);
	private JTextField location = new JTextField();
	private JTextArea about = new JTextArea();
	private JScrollPane aboutContainer = new JScrollPane();
	
	private JButton saveButton = new JButton("Save");
	private JButton cancelButton = new JButton("Cancel");
	
	/**
	 * Construct a new Profile view.
	 */
	public ProfileEdit(){
		this.setIconImage(Constants.profileIconMain);
		this.setTitle("Edit Profile");
		this.setSize(437, 313);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		usernameLabel.setBounds(10, 10, 100, 20);
		screennameLabel.setBounds(10, 35, 100, 20);
		emailLabel.setBounds(10, 60, 100, 20);
		ageLabel.setBounds(10, 85, 100, 20);
		sexLabel.setBounds(10, 110, 100, 20);
		locationLabel.setBounds(10, 135, 100, 20);
		aboutLabel.setBounds(10, 160, 100, 20);
		
		username.setBounds(120, 10, 300, 20);
		username.setEditable(false);
		screenname.setBounds(120, 35, 300, 20);
		email.setBounds(120, 60, 300, 20);
		age.setBounds(120, 85, 300, 20);
		sex.setBounds(120, 110, 300, 20);
		location.setBounds(120, 135, 300, 20);
		
		about.setBounds(0, 0, 300, 130);
		about.setFont(new Font("Dialog", Font.PLAIN, 12));	
		about.setLineWrap(true);
		about.setWrapStyleWord(true);
		aboutContainer = new JScrollPane(about,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		aboutContainer.setBounds(120, 160, 300, 80);

		cancelButton.setBounds(10, 250, 100, 25);
		saveButton.setBounds(320, 250, 100, 25);
		
		cancelButton.addActionListener(this);
		saveButton.addActionListener(this);
		
		this.add(usernameLabel);
		this.add(screennameLabel);
		this.add(emailLabel);
		this.add(ageLabel);
		this.add(sexLabel);
		this.add(locationLabel);
		this.add(aboutLabel);
		this.add(username);
		this.add(screenname);
		this.add(email);
		this.add(age);
		this.add(sex);
		this.add(location);
		this.add(aboutContainer);
		this.add(cancelButton);
		this.add(saveButton);
	}
	
	public void update(){
		username.setText(Constants.getUserProfile().getUsername());
		screenname.setText(Constants.getUserProfile().getScreen_name());
		email.setText(Constants.getUserProfile().getEmail());
		
		if(Constants.getUserProfile().getAge() == -1)
			age.setText("N\\A");
		else
			age.setText(Constants.getUserProfile().getAge() + "");
		
		if(Constants.getUserProfile().getSex() == -1)
			sex.setSelectedIndex(0);
		else
			sex.setSelectedIndex(Constants.getUserProfile().getSex());
		
		location.setText(Constants.getUserProfile().getLocation());
		about.setText(Constants.getUserProfile().getAbout());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton){
			update();
			this.dispose();
		}
		
		else if(e.getSource() == saveButton){
			Constants.getUserProfile().setScreen_name(screenname.getText());
			Constants.getUserProfile().setEmail(email.getText());
			
			if(!age.getText().contains("N\\A"))
				Constants.getUserProfile().setAge(Byte.valueOf(age.getText()));
			else
				Constants.getUserProfile().setAge((byte) -1);
			
			Constants.getUserProfile().setSex((byte) sex.getSelectedIndex());
			Constants.getUserProfile().setLocation(location.getText());
			Constants.getUserProfile().setAbout(about.getText());
			
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_PROFILE.getHeader() + "º" +
					Constants.getUserProfile().getScreen_name() + "º" +
					Constants.getUserProfile().getEmail() + "º" +
					Constants.getUserProfile().getAge() + "º" +
					Constants.getUserProfile().getSex() + "º" +
					Constants.getUserProfile().getLocation() + "º" +
					Constants.getUserProfile().getAbout());
			this.dispose();
		}
	}

}
