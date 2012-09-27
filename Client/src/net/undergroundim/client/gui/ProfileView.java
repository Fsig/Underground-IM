package net.undergroundim.client.gui;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.undergroundim.client.Constants;
import net.undergroundim.client.Profile;


/**
 * 
 * @author Troy
 *
 */
public class ProfileView extends JDialog{
	private static final long serialVersionUID = 3541155790509935847L;
	
	private Profile profile;

	private JLabel usernameLabel = new JLabel("Username:");
	private JLabel screennameLabel = new JLabel("Screen Name:");
	private JLabel emailLabel = new JLabel("Email Address:");
	private JLabel ageLabel = new JLabel("Age:");
	private JLabel sexLabel = new JLabel("Sex:");
	private JLabel locationLabel = new JLabel("Location:");
	private JLabel aboutLabel = new JLabel("About:");
	private JLabel madeLabel = new JLabel("Made Date:");
	
	private JLabel username = new JLabel();
	private JLabel screenname = new JLabel();
	private JLabel email = new JLabel();
	private JLabel age = new JLabel();
	private JLabel sex = new JLabel();
	private JLabel location = new JLabel();
	private JTextArea about = new JTextArea();
	private JScrollPane aboutContainer = new JScrollPane();
	private JLabel made = new JLabel();
	
	/**
	 * Construct a new Profile view.
	 */
	public ProfileView(){
		this.setIconImage(Constants.profileIconMain);
		this.setTitle("Username's Profile");
		this.setSize(437, 297);
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
		madeLabel.setBounds(10, 240, 100, 20);
		
		username.setBounds(120, 10, 300, 20);
		screenname.setBounds(120, 35, 300, 20);
		email.setBounds(120, 60, 300, 20);
		age.setBounds(120, 85, 300, 20);
		sex.setBounds(120, 110, 300, 20);
		location.setBounds(120, 135, 300, 20);
		made.setBounds(120, 240, 300, 20);
		
		about.setBounds(0, 0, 300, 130);
		about.setFont(new Font("Dialog", Font.PLAIN, 12));	
		about.setEditable(false);
		about.setLineWrap(true);
		about.setWrapStyleWord(true);
		aboutContainer = new JScrollPane(about,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		aboutContainer.setBounds(120, 160, 300, 80);
		
		this.add(usernameLabel);
		this.add(screennameLabel);
		this.add(emailLabel);
		this.add(ageLabel);
		this.add(sexLabel);
		this.add(locationLabel);
		this.add(aboutLabel);
		this.add(madeLabel);
		this.add(username);
		this.add(screenname);
		this.add(email);
		this.add(age);
		this.add(sex);
		this.add(location);
		this.add(aboutContainer);
		this.add(made);
	}
	
	public static void main(String[] args){
		new ProfileView();
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
		
		this.setTitle(profile.getUsername() + "'s Profile");
		
		this.username.setText(profile.getUsername());
		this.screenname.setText(profile.getScreen_name());
		this.email.setText(profile.getEmail());
		
		if(profile.getAge() > 0)
			this.age.setText(profile.getAge() + "");
		else
			this.age.setText("N/A");
		
		if(profile.getSex() == 1)
			this.sex.setText("Male");
		else if(profile.getSex() == 2)
			this.sex.setText("Female");
		else
			this.sex.setText("N/A");
		
		this.location.setText(profile.getLocation());
		this.about.setText(profile.getAbout());
		this.made.setText(profile.getMade_date());
	}

}
