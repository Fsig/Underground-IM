package net.undergroundim.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.undergroundim.client.BrowserLaunch;
import net.undergroundim.client.Constants;
import net.undergroundim.client.networking.PacketHeaders;


public class MenuBar implements ActionListener{
	private JMenuBar menuBar = new JMenuBar();
	
	private JMenu mnFile = new JMenu("File");
	private JMenu mnFormat = new JMenu("Settings");
	private JMenu mnHelp = new JMenu("Help");
	public JMenu mntmStatus = new JMenu("Online Status");
	
	private JMenuItem mntmOnline = new JMenuItem("Online");
	private JMenuItem mntmAddFriend = new JMenuItem("Add Friend");
	private JMenuItem mntmCheckRequests = new JMenuItem("Check F/R");
	private JMenuItem mntmAway = new JMenuItem("Away");
	private JMenuItem mntmDND = new JMenuItem("Do Not Disturb");
	private JMenuItem mntmProfile = new JMenuItem("Profile");
	private JMenuItem mntmAccount = new JMenuItem("Account");
	private JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
	private JMenuItem mntmExit = new JMenuItem("Exit");
	private JMenuItem mntmPreferences = new JMenuItem("Preferences");
	public JMenuItem mntmFont = new JMenuItem("Format Font");
	private JMenuItem mntmAbout = new JMenuItem("About");
	private JMenuItem mntmWebsite = new JMenuItem("Website");
	
	private ImageIcon addIcon = new ImageIcon(MenuBar.class.getResource("/icons/add.png"));
	private ImageIcon settingsIcon = new ImageIcon(MenuBar.class.getResource("/icons/gnome_preferences_system.png"));
	private ImageIcon buttonFontIcon = new ImageIcon(MenuBar.class.getResource("/icons/font-x-generic-icon.png"));
	private ImageIcon refreshIcon = new ImageIcon(MenuBar.class.getResource("/icons/arrow_refresh.png"));
	private ImageIcon accountIcon = new ImageIcon(MenuBar.class.getResource("/icons/vcard.png"));
	private ImageIcon disconnectIcon = new ImageIcon(MenuBar.class.getResource("/icons/disconnect-icon.png"));
	private ImageIcon buttonCancelIcon = new ImageIcon(MenuBar.class.getResource("/icons/button_cancel.png"));
	private ImageIcon helpIcon = new ImageIcon(MenuBar.class.getResource("/icons/help-book.png"));
	private ImageIcon globe = new ImageIcon(MenuBar.class.getResource("/icons/internet_alt.png"));
	
	/**
	 * Initialize the menu.
	 */
	public MenuBar(){
		//Menu
		menuBar.add(mnFile);
		menuBar.add(mnFormat);
		menuBar.add(mnHelp);
		
		//File
		mnFile.add(mntmStatus);
		mnFile.addSeparator();
		mnFile.add(mntmAddFriend);
		mnFile.add(mntmCheckRequests);
		mnFile.addSeparator();
		mnFile.add(mntmProfile);
		mnFile.add(mntmAccount);
		mnFile.addSeparator();
		mnFile.add(mntmDisconnect);
		mnFile.add(mntmExit);
		
		//Format
		mnFormat.add(mntmPreferences);
		mnFormat.add(mntmFont);
		
		//Help
		mnHelp.add(mntmAbout);
		mnHelp.add(mntmWebsite);
		
		//Status Menu
		mntmStatus.add(mntmOnline);
		mntmStatus.add(mntmAway);
		mntmStatus.add(mntmDND);
		
		//Icons
		mntmStatus.setIcon(Constants.onlineIcon);
		mntmAddFriend.setIcon(addIcon);
		mntmCheckRequests.setIcon(refreshIcon);
		mntmProfile.setIcon(Constants.profileIcon);
		mntmAccount.setIcon(accountIcon);
		mntmDisconnect.setIcon(disconnectIcon);
		mntmExit.setIcon(buttonCancelIcon);
		mntmPreferences.setIcon(settingsIcon);
		mntmFont.setIcon(buttonFontIcon);
		mntmAbout.setIcon(helpIcon);
		mntmWebsite.setIcon(globe);
		mntmOnline.setIcon(Constants.onlineIcon);
		mntmAway.setIcon(Constants.awayIcon);
		mntmDND.setIcon(Constants.dndIcon);
		
		//Tool Tips
		mntmAddFriend.setToolTipText("Add a friend");
		mntmCheckRequests.setToolTipText("Check friend requests");
		mntmProfile.setToolTipText("Update your profile");
		mntmAccount.setToolTipText("Update your account settings");
		mntmDisconnect.setToolTipText("Disconnect from this server");
		mntmExit.setToolTipText("Exit the program");
		
		//Action Listeners
		mntmOnline.addActionListener(this);
		mntmAway.addActionListener(this);
		mntmDND.addActionListener(this);
		mntmAddFriend.addActionListener(this);
		mntmCheckRequests.addActionListener(this);
		mntmProfile.addActionListener(this);
		mntmAccount.addActionListener(this);
		mntmDisconnect.addActionListener(this);
		mntmExit.addActionListener(this);
		mntmPreferences.addActionListener(this);
		mntmFont.addActionListener(this);
		mntmAbout.addActionListener(this);
		mntmWebsite.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mntmOnline){
			Constants.getFriendList().setTitle("Underground IM");
			Constants.getUser().setStatus(0);
			mntmStatus.setIcon(Constants.onlineIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					0);
		}
		
		else if(e.getSource() == mntmAway){
			Constants.getFriendList().setTitle("Underground IM (Away)");
			Constants.getUser().setStatus(1);
			mntmStatus.setIcon(Constants.awayIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					1);
		}
		
		else if(e.getSource() == mntmDND){
			Constants.getFriendList().setTitle("Underground IM (DND)");
			Constants.getUser().setStatus(2);
			mntmStatus.setIcon(Constants.dndIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					2);
		}
		
		else if(e.getSource() == mntmAddFriend){
			String username = JOptionPane.showInputDialog("Enter username:"); 

			if(username != null){
				Constants.getPacketManager().sendPacket(PacketHeaders.FRIEND_ADD.getHeader() + "º" + username);
			}
		}
		
		else if(e.getSource() == mntmCheckRequests){
			Constants.getPacketManager().sendPacket(PacketHeaders.FRIEND_REQUESTS.getHeader() + "º");
		}
		
		else if(e.getSource() == mntmProfile){
			Constants.getProfileEdit().update();
			Constants.getProfileEdit().setVisible(true);
		}
		
		else if(e.getSource() == mntmAccount){
			Constants.getAccount().setVisible(true);
		}
		
		else if(e.getSource() == mntmDisconnect){
			for(PersonalMessage pm : Constants.getPmWindows())
				pm.dispose();
			
			Constants.getPacketManager().disconnect();
			Constants.getFriendList().dispose();
			Constants.getLoginGUI().setVisible(true);
		}
		
		else if(e.getSource() == mntmExit){
			System.exit(0);
		}
		
		else if(e.getSource() == mntmFont){
			Constants.getFontDialog().setVisible(true);
		}
		
		else if(e.getSource() == mntmPreferences){
			Constants.getPreferencesGUI().setVisible(true);
		}
		
		else if(e.getSource() == mntmAbout){
			JOptionPane.showMessageDialog(null,"Underground IM, the free Java Instant Messaging solution. Both a client and a \n" +
					"server are available. Underground IM is open source software distributed\n" +
					"free of charge under the terms of the GNU General Public License.\n\n" +
					"Version: " + Constants.getVersion() + "\n" +
					"Build Date: " + Constants.getBuildDate() + "\n" +
					"Created by: Fsig\n\n" +
					"Website: \n" + 
					"http://undergroundim.net/");
		}
		
		else if(e.getSource() == mntmWebsite){
			BrowserLaunch.openURL("http://undergroundim.net/");
		}
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}

}
