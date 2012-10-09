package net.undergroundim.client.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import net.undergroundim.client.Client;
import net.undergroundim.client.Constants;
import net.undergroundim.client.ImageRenderer;
import net.undergroundim.client.networking.PacketHeaders;

/**
 * 
 * @author Troy
 *
 */
public class AddFriendDialog extends JDialog implements ActionListener, WindowFocusListener{
	private static final long serialVersionUID = 9018855409823926066L;
	
	private PersonalMessage pm;

	public JTable friendList;
	private JScrollPane friendListContainer;
	private DefaultTableModel friendListData;
	
	private JButton addFriendButton = new JButton("Add Friend(s)");
	private String clientSend = "";
	private String clientList = "";
	
	/**
	 * Construct a new add friend dialog.
	 */
	public AddFriendDialog(){
		this.setTitle("Online Friends");
		this.setSize(250, 300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowFocusListener(this);
		
		//Friend List
		friendListData = new DefaultTableModel(new Object[0][0],new String[]{"Status","Friends"}){
			private static final long serialVersionUID = -3666163903937562582L;

			@Override
			public boolean isCellEditable(int a, int b){
				return false;
			}
		};
				
		friendList = new JTable(friendListData);
		friendList.setBounds(0, 0, getWidth() -5, getHeight() -60);
		friendList.setFont(new Font("Arial", Font.PLAIN, 12));
		friendList.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
				
		friendListContainer = new JScrollPane(friendList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		friendListContainer.setBounds(0, 0, getWidth() -5, getHeight() -68);
		
		addFriendButton.setBounds(5, 237, 234, 30);
		addFriendButton.addActionListener(this);
				
		//Add components
		this.add(friendListContainer);
		this.add(addFriendButton);
	}
	
	/**
	 * Update the target window.
	 * 
	 * @param pm
	 */
	public void setTarget(PersonalMessage pm){
		this.pm = pm;
	}
	
	/**
	 * Method to update the friend's list.
	 */
	public void updateFriends(){
		//Clear rows
		int count = friendListData.getRowCount();

		for(int i = 0; i < count; i++)		
			friendListData.removeRow(0);
		
		//Add rows
		for(Client c : Constants.getFriends()){
			if(c.isOnline()){
				boolean b = false;
				
				for(Client client : pm.clients)
					if(client.getUser_id() == c.getUser_id())
						b = true;
				
				if(!b)
					friendListData.addRow(new Object[]{Constants.onlineIcon,c.getUsername()});
			}
		}

		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addFriendButton){
			for(int i : friendList.getSelectedRows()){
				pm.addClient(Constants.getFriend(friendList.getValueAt(i, 1).toString()));
			}
			
			clientSend = "";
			clientList = "";
			for(Client c : pm.clients){
				clientList += c.getUser_id() + ",";
				clientSend += c.getUser_id() + "º" + c.getUsername() + "º" + c.getScreen_name() + "º" + c.getStatus() + ",";
			}
			
			Constants.getPacketManager().sendPacket(PacketHeaders.GROUP_ADD.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" + 
					Constants.getUser().getUsername() + "º" + 
					Constants.getUser().getScreen_name() + "º" +
					Constants.getUser().getStatus() + "ª" +
					clientSend + "ª" +
					clientList);
			this.dispose();
		}
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		this.dispose();
	}
	
	public void windowGainedFocus(WindowEvent e) {}

}
