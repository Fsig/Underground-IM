package net.undergroundim.client.gui;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import net.undergroundim.client.Clipboard;
import net.undergroundim.client.Constants;
import net.undergroundim.client.networking.PacketHeaders;


/**
 * 
 * @author Troy
 *
 */
public class PopupMenu implements ActionListener, MouseListener {
	private JPopupMenu chatroom_clientMenu = new JPopupMenu();
	private JPopupMenu editMenu = new JPopupMenu();
	private JPopupMenu friendMenu = new JPopupMenu();
	private java.awt.PopupMenu trayMenu = new java.awt.PopupMenu();
	
	private JMenuItem pmMenu = new JMenuItem("Message");
	private JMenuItem blockMenu = new JMenuItem("Block");
	private JMenuItem cutMenu = new JMenuItem("Cut");
	private JMenuItem copyMenu = new JMenuItem("Copy");
	private JMenuItem pasteMenu = new JMenuItem("Paste");
	private JMenuItem deleteMenu = new JMenuItem("Delete");
	private JMenuItem deleteMenu2 = new JMenuItem("Delete");
	private JMenuItem selectMenu = new JMenuItem("Select All");
	private JMenuItem imageMenu = new JMenuItem("Insert IMG");
	private JMenuItem fileTransferMenu = new JMenuItem("File Transfer");
	private JMenuItem fileTransferMenu2 = new JMenuItem("File Transfer");
	private JMenuItem messageMenu = new JMenuItem("Message");
	private JMenuItem profileMenu = new JMenuItem("Profile");
	
	private MenuItem showMenu = new MenuItem("Show");
	private MenuItem mntmAddFriend = new MenuItem("Add Friend");
	private MenuItem mntmDisconnect = new MenuItem("Disconnect");
	private MenuItem exitMenu = new MenuItem("Exit");
	
	private Menu mntmStatus = new Menu("Online Status");
	private MenuItem mntmOnline = new MenuItem("Online");
	private MenuItem mntmAway = new MenuItem("Away");
	private MenuItem mntmDND = new MenuItem("Do Not Disturb");
	
	private ImageIcon blockIcon = new ImageIcon(PopupMenu.class.getResource("/icons/block-icon.png"));
	private ImageIcon pmIcon = new ImageIcon(PopupMenu.class.getResource("/icons/e_mail16.png"));
	private ImageIcon imageIcon = new ImageIcon(PopupMenu.class.getResource("/icons/image-icon.png"));
	private ImageIcon cutIcon = new ImageIcon(PopupMenu.class.getResource("/icons/cut.png"));
	private ImageIcon copyIcon = new ImageIcon(PopupMenu.class.getResource("/icons/page_copy.png"));
	private ImageIcon pasteIcon = new ImageIcon(PopupMenu.class.getResource("/icons/page_paste.png"));
	private ImageIcon selectIcon = new ImageIcon(PopupMenu.class.getResource("/icons/selection_select.png"));
	private ImageIcon deleteIcon = new ImageIcon(PopupMenu.class.getResource("/icons/delete.png"));
	private ImageIcon fileTransferIcon = new ImageIcon(PopupMenu.class.getResource("/icons/Transfer-Document-icon.png"));
	
	private PersonalMessage pm;
	private JList<String> clientList;
	private JTable friendList;
	
	/**
	 * Construct a new Popup menu.
	 * 
	 * @param chatPane
	 * @param clientList
	 */
	public PopupMenu(PersonalMessage pm, JList<String> clientList, JTable friendList){
		this.pm = pm;
		this.clientList = clientList;
		this.friendList = friendList;
		
		//Chat Room Client Menu
		if(clientList != null){
			blockMenu.setIcon(blockIcon);
			pmMenu.setIcon(pmIcon);
	
			chatroom_clientMenu.add(blockMenu);
			chatroom_clientMenu.add(pmMenu);
			
			blockMenu.addActionListener(this);
			pmMenu.addActionListener(this);
			
			clientList.addMouseListener(this);
		}
		
		//Edit Menu
		if(pm != null){
			imageMenu.setIcon(imageIcon);
			cutMenu.setIcon(cutIcon);
			copyMenu.setIcon(copyIcon);
			pasteMenu.setIcon(pasteIcon);
			deleteMenu.setIcon(deleteIcon);
			selectMenu.setIcon(selectIcon);
			fileTransferMenu.setIcon(fileTransferIcon);
	
			editMenu.add(cutMenu);
			editMenu.add(copyMenu);
			editMenu.add(pasteMenu);
			editMenu.add(deleteMenu);
			editMenu.addSeparator();
			editMenu.add(selectMenu);
			editMenu.addSeparator();
			editMenu.add(imageMenu);
			editMenu.add(fileTransferMenu);
	
			imageMenu.addActionListener(this);
			cutMenu.addActionListener(this);
			copyMenu.addActionListener(this);
			pasteMenu.addActionListener(this);
			deleteMenu.addActionListener(this);
			selectMenu.addActionListener(this);
			fileTransferMenu.addActionListener(this);
			
			pm.chatBox.addMouseListener(this);
		}
		
		//Friend Menu
		if(friendList != null){
			messageMenu.setIcon(pmIcon);
			profileMenu.setIcon(Constants.profileIcon);
			fileTransferMenu2.setIcon(fileTransferIcon);
			deleteMenu2.setIcon(blockIcon);
	
			friendMenu.add(messageMenu);
			friendMenu.add(profileMenu);
			friendMenu.add(fileTransferMenu2);
			friendMenu.addSeparator();
			friendMenu.add(deleteMenu2);
			
			messageMenu.addActionListener(this);
			profileMenu.addActionListener(this);
			fileTransferMenu2.addActionListener(this);
			deleteMenu2.addActionListener(this);
			
			friendList.addMouseListener(this);
			
			mntmStatus.add(mntmOnline);
			mntmStatus.add(mntmAway);
			mntmStatus.add(mntmDND);
			trayMenu.add(showMenu);
			trayMenu.addSeparator();
			trayMenu.add(mntmStatus);
			trayMenu.add(mntmAddFriend);
			trayMenu.addSeparator();
			trayMenu.add(mntmDisconnect);
			trayMenu.add(exitMenu);
			
			showMenu.addActionListener(this);
			exitMenu.addActionListener(this);
			mntmOnline.addActionListener(this);
			mntmAway.addActionListener(this);
			mntmDND.addActionListener(this);
			mntmAddFriend.addActionListener(this);
			mntmDisconnect.addActionListener(this);
			
			trayMenu.addActionListener(this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == blockMenu){
			if(blockMenu.getText() == "Block"){
				//Constants.getClient(clientList.getSelectedValue()).setBlocked(true);
			}else{
				//Constants.getClient(clientList.getSelectedValue()).setBlocked(false);
			}
		}
		
		else if(e.getSource() == pmMenu){
			pm.chatBox.setText(pm.chatBox.getText() + "/w " + clientList.getSelectedValue() + " ");
			pm.chatBox.requestFocus();
		}
		
		else if(e.getSource() == cutMenu){
			if(pm.chatBox.getSelectedText() != null){
				String cutText = pm.chatBox.getSelectedText();
				Clipboard.setClipboardContents(cutText);
				pm.chatBox.setText(pm.chatBox.getText().replace(cutText, ""));
			}
		}
		
		else if(e.getSource() == copyMenu){
			if(pm.chatBox.getSelectedText() != null){
				Clipboard.setClipboardContents(pm.chatBox.getSelectedText());
			}
		}
		
		else if(e.getSource() == pasteMenu){
			pm.chatBox.setText(pm.chatBox.getText() + Clipboard.getClipboardContents());
		}
		
		else if(e.getSource() == selectMenu){
			pm.chatBox.selectAll();
		}
		
		else if(e.getSource() == deleteMenu){
			if(!pm.chatBox.getText().isEmpty())
				pm.chatBox.setText(pm.chatBox.getText().replace(pm.chatBox.getSelectedText(), ""));
		}
		
		else if(e.getSource() == imageMenu){
			pm.chatBox.setText(pm.chatBox.getText() + "<img src=\"ImageURL\">");
			pm.chatBox.requestFocus();
			int start = pm.chatBox.getText().indexOf("ImageURL");
			pm.chatBox.setCaretPosition(start);
			pm.chatBox.setSelectionEnd(start + 8);
		}
		
		else if(e.getSource() == messageMenu){
			int row = friendList.getSelectedRow();
			
			if(Constants.getFriend(friendList.getValueAt(row, 1).toString()).isOnline()){
				if(Constants.getPM(friendList.getValueAt(row, 1).toString()) == null)
					Constants.addPmWindow(new PersonalMessage(Constants.getFriend(friendList.getValueAt(row, 1).toString())));

				Constants.getPM(Constants.getFriend(friendList.getValueAt(row, 1).toString()).getUser_id()).setVisible(true);
			}
		}
		
		else if(e.getSource() == deleteMenu2){
			int row = friendList.getSelectedRow();
			
			int response = JOptionPane.showConfirmDialog(null, 
        			"Would you really like to remove " + friendList.getValueAt(row, 1).toString() + " from your friends?", 
        			"Delete Friend",
        			JOptionPane.YES_NO_OPTION);
        	
        	if(response == JOptionPane.YES_OPTION){
        		Constants.getPacketManager().sendPacket(PacketHeaders.FRIEND_DELETE.getHeader() + "º" + 
    					Constants.getUser().getUser_id() + "º" + 
    					Constants.getFriend(friendList.getValueAt(row, 1).toString()).getUser_id());
        	}	
		}
		
		else if(e.getSource() == profileMenu){
			int row = friendList.getSelectedRow();
			
			Constants.getPacketManager().sendPacket(PacketHeaders.VIEW_PROFILE.getHeader() + "º" +
					Constants.getFriend(friendList.getValueAt(row, 1).toString()).getUser_id());
		}
		
		else if(e.getSource() == fileTransferMenu){
			pm.fileTransfer.setVisible(true);
		}
		
		else if(e.getSource() == fileTransferMenu2){
			int row = friendList.getSelectedRow();
			
			if(Constants.getFriend(friendList.getValueAt(row, 1).toString()).isOnline()){
				if(Constants.getPM(friendList.getValueAt(row, 1).toString()) == null)
					Constants.addPmWindow(new PersonalMessage(Constants.getFriend(friendList.getValueAt(row, 1).toString())));
			
				Constants.getPM(Constants.getFriend(friendList.getValueAt(row, 1).toString()).getUser_id()).fileTransfer.setVisible(true);
			}
		}
		
		else if(e.getSource() == showMenu){
			Constants.getFriendList().setVisible(true);
		}
		
		else if(e.getSource() == exitMenu){
			System.exit(0);
		}
		
		else if(e.getSource() == mntmDisconnect){
			for(PersonalMessage pm : Constants.getPmWindows())
				pm.dispose();
			
			Constants.getPacketManager().disconnect();
			Constants.getFriendList().dispose();
			Constants.getLoginGUI().setVisible(true);
		}
		
		else if(e.getSource() == mntmOnline){
			Constants.getFriendList().setTitle("Underground IM");
			Constants.getUser().setStatus(0);
			Constants.getFriendList().menuBar.mntmStatus.setIcon(Constants.onlineIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					0);
		}
		
		else if(e.getSource() == mntmAway){
			Constants.getFriendList().setTitle("Underground IM (Away)");
			Constants.getUser().setStatus(1);
			Constants.getFriendList().menuBar.mntmStatus.setIcon(Constants.awayIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					1);
		}
		
		else if(e.getSource() == mntmDND){
			Constants.getFriendList().setTitle("Underground IM (DND)");
			Constants.getUser().setStatus(2);
			Constants.getFriendList().menuBar.mntmStatus.setIcon(Constants.dndIcon);
			Constants.getPacketManager().sendPacket(PacketHeaders.UPDATE_STATUS.getHeader() + "º" +
					2);
		}
		
		else if(e.getSource() == mntmAddFriend){
			String username = JOptionPane.showInputDialog("Enter username:"); 

			if(username != null){
				Constants.getPacketManager().sendPacket(PacketHeaders.FRIEND_ADD.getHeader() + "º" + username);
			}
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == clientList && e.isPopupTrigger()){
			clientList.setSelectedIndex(getRow(e.getPoint()));
			blockMenu.setIcon(blockIcon);
			blockMenu.setText("Block");
			chatroom_clientMenu.show(e.getComponent(), e.getX(), e.getY());
		}
		
		else if(pm != null && e.getSource() == pm.chatBox && e.isPopupTrigger()){
			editMenu.show(e.getComponent(), e.getX(), e.getY());
		}
		
		else if(e.getSource() == friendList && e.isPopupTrigger()){
			int row = getFriendRow(e.getPoint());
			ListSelectionModel model = friendList.getSelectionModel();
			model.setSelectionInterval(row,row);
			
			friendMenu.show(e.getComponent(), e.getX(), e.getY());
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == friendList && e.getClickCount() == 2) {
			int row = getFriendRow(e.getPoint());
			
			if(Constants.getFriend(friendList.getValueAt(row, 1).toString()).isOnline()){
				if(Constants.getPM(friendList.getValueAt(row, 1).toString()) == null)
					Constants.addPmWindow(new PersonalMessage(Constants.getFriend(friendList.getValueAt(row, 1).toString())));
				
				Constants.getPM(Constants.getFriend(friendList.getValueAt(row, 1).toString()).getUser_id()).setVisible(true);
			}
		}  
	}
	
	/**
	 * Get the row at the point.
	 * 
	 * @param point
	 * @return Integer
	 */
	private int getFriendRow(Point point){
		return friendList.rowAtPoint(point);
	}
	
	/**
	 * Get the row at the point.
	 * 
	 * @param point
	 * @return Integer
	 */
	private int getRow(Point point){
		return clientList.locationToIndex(point);
	}
	
	public JPopupMenu getChatRoomClientMenu(){
		return chatroom_clientMenu;
	}

	public JPopupMenu getEditMenu() {
		return editMenu;
	}

	public JPopupMenu getFriendMenu() {
		return friendMenu;
	}
	
	public java.awt.PopupMenu getTrayMenu(){
		return trayMenu;
	}
	
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}

}
