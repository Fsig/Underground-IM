package net.undergroundim.client.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import net.undergroundim.client.AudioPlayer;
import net.undergroundim.client.Client;
import net.undergroundim.client.Constants;
import net.undergroundim.client.Profile;
import net.undergroundim.client.encryption.AESEncoder;
import net.undergroundim.client.encryption.Encoder;
import net.undergroundim.client.gui.FriendList;
import net.undergroundim.client.gui.PersonalMessage;


/**
 * 
 * @author Troy
 *
 */
public class PacketManager {
	private Thread listener;
	
	private BufferedReader in;
    public PrintWriter out;
    private Socket socket;
    private String fromServer;
    private String[] header;
    private String[] packet;
    private boolean loggedin, connected, cancelled;
    private AESEncoder encoder;
    private SecretKeySpec key;
	
	private int port;
	private InetAddress ipAddress;
	
	private String username;
	
	/**
	 * This will connect to the server.
	 * 
	 * @param ipAddress
	 * @param username
	 * @param port
	 * @return boolean
	 */
	public boolean connect(String ipAddress, String password, String username, String userPassword, int port, boolean connect){
		this.port = port;
		this.loggedin = false;
		
		try {
			this.ipAddress = InetAddress.getByName(ipAddress);
			socket = new Socket(this.ipAddress, this.port);
			
			 try{
				 this.socket.setTcpNoDelay(true);
				 this.socket.setKeepAlive(true);
				 this.socket.setSendBufferSize(20480);
				 this.socket.setReceiveBufferSize(20480);
			 }catch (SocketException e) {
				 e.printStackTrace();
			 }
			 
	        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        out = new PrintWriter(socket.getOutputStream(), true);
	        
	        //Login packet..
	        this.connected = true;
	        this.username = username;
	        
	        sendRawPacket(PacketHeaders.CONNECT.getHeader() + "º");
	        String raw = Encoder.decode(in.readLine());
	        this.key = new SecretKeySpec(Constants.stringToBytes(raw.replace("¢", "")),"AES");
	        this.encoder = new AESEncoder(key);
	        
	        if(connect)
	        	sendPacket(PacketHeaders.LOGIN.getHeader() + "º" + password + "º" + Constants.getVersion() + "º" + username + "º" + userPassword);
	        else
	        	sendPacket(PacketHeaders.REGISTER_USER.getHeader() + "º" + password + "º" + Constants.getVersion() + "º" + username + "º" + userPassword);
	        
	        //Start a new listener.
	        listener = new Thread(){
	        	@Override
				public void run(){
					listen();
				}
	        };
	        listener.start();
		}catch(UnknownHostException e1) {
			JOptionPane.showMessageDialog(null,
					   "Failed to connect to the server.\nPlease review the error below.\n\n" +
					   Constants.getStack(e1),
					   "Connection Error",
					   JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
			return false;
		}catch(IOException e) {
			JOptionPane.showMessageDialog(null,
					   "Failed to connect to the server.\n\nYou may have entered in the wrong server\ndetails or the sever may be offline.",
					   "Connection Error",
					   JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		return true;
	}
	
	/**
	 * Listen for all incoming packets.
	 * 
	 * This method will keep on listening for all packet's and
	 * will not move onto the next packet until the current
	 * packet is dealt with.
	 * 
	 * With this we should be able to accomplish broadcasting from
	 * the server to all client's player updates, movement updates
	 * and so forth.
	 * 
	 * These methods are still in test stages.
	 * 
	 */
	private void listen(){
		try{
			while(connected && (fromServer = in.readLine()) != null){
				/**
				 * If we are logged in the packet's will be encrypted.
				 * This will decrypt the packet before we process it.
				 */
				
				if(connected)
					fromServer = this.encoder.decrypt(fromServer);
				else
					fromServer = Encoder.decode(fromServer);

				if(fromServer != null && fromServer.contains("ª") && fromServer.endsWith("¢")){
					//Remove end character.
					fromServer = fromServer.replace("¢", ""); 
					
					//Get header
					header = fromServer.split("ª");
					
					//Get packet
					if(header[1].contains("º"))
						packet = header[1].split("º");
					
					 /**
			         * Switch through packet headers.
			         * 
			         * Server->Client
			         */
			        switch(Integer.parseInt(header[0])){
			        case 0: //Login
			        	/**
			        	 * This will set the user's ID, key 
			        	 * and also set the login flag to true.
			        	 * 
			        	 * Without this key the client can't interact
			        	 * with the server. The server will assume the
			        	 * client is sending junk / trying to cheat and 
			        	 * disconnect the socket.
			        	 */

			        	Constants.setUser(new Client(Integer.parseInt(packet[2]),
			        			username,
			        			packet[3],
			        			0,
			        			true,
			        			true));
			        	Constants.setUserProfile(new Profile(username,
			        			packet[3],
			        			packet[4],
			        			Byte.valueOf(packet[5]),
			        			Byte.valueOf(packet[6]),
			        			packet[7],
			        			packet[8],
			        			packet[9]));
			        	
				        this.key = new SecretKeySpec(Constants.stringToBytes(packet[1]),"AES");
				        this.encoder = new AESEncoder(key);
				        this.loggedin = true;
				        
				        Constants.setFriendList(new FriendList());
				        Constants.getFriendList().setVisible(true);
						Constants.getLoginGUI().dispose();
						Constants.getLoginGUI().passwordField.setText("");
				        
				    	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_HHmmss");
						Constants.setLogFileName(Constants.getLogFileLocation() + "\\" + username + "_" + sdf.format(new Date()) + ".log");
						
						Constants.getPreferencesGUI().save();
						
				        sendPacket(PacketHeaders.FRIEND_LIST.getHeader() + "º");
				        sendPacket(PacketHeaders.FRIEND_REQUESTS.getHeader() + "º");
			        	break;
			        case 1: //Login failed
			        	JOptionPane.showMessageDialog(null,
			        			"Failed to connect to the server, incorrect password",
								 "Connection Error",
								 JOptionPane.ERROR_MESSAGE);
			        	break;
			        case 2: //Connect
			        	break;
			        case 3: //Packet Error
			        	JOptionPane.showMessageDialog(null,
			        			"You have sent a packet that appears to be invaild,\n" +
								"and you have been disconnect from the Server.\n\n" +
								"Please report this error with the information below.\n\n" +
								"Packet: " + packet[1],
								"Packet Error From Client",
								   JOptionPane.ERROR_MESSAGE);
			        	
			        	//Constants.getChatGUI().dispose();
			        	Constants.getLoginGUI().setVisible(true);
			        	break;
			        case 4: //Key Change TIME!
			        	if(Constants.bytesToString(this.key.getEncoded()).equals(packet[1])){
			        		this.key = new SecretKeySpec(Constants.stringToBytes(packet[2]),"AES");
			        		this.encoder = new AESEncoder(key);
			        	}else{
			        		JOptionPane.showMessageDialog(null,
			        				"The Server has tried to give you a new Key\n" +
									"which appears to be invalid.\n\n" +
									"We recommend you disconnect from this Server\n" +
									"as someone may be trying to listen in.",
									"Packet Error Key",
									   JOptionPane.ERROR_MESSAGE);
			        	}
			        	break;
			        case 5: //Version error
			        	JOptionPane.showMessageDialog(null,
			        			"Your version is not compatible with the Server.\n\n" +
			        			"Update to Version: " + packet[0] + " or above to connect\n" +
								"to this Server.",
								"Version Error",
								   JOptionPane.ERROR_MESSAGE);
			        	break;
			        case 6: //Register User
			        	JOptionPane.showMessageDialog(null,
							    "You have successfuly register a user.\n\n" +
								"You can now login with the details you provided.",
								"Registration Successful",
							    JOptionPane.INFORMATION_MESSAGE);
			        	disconnect();
			        	break;
			        case 7://Register fail
			        	JOptionPane.showMessageDialog(null,
							    "Failed to register user, see error below.\n\n" +
								"Error: " + packet[1],
								"Registration Failed",
							    JOptionPane.ERROR_MESSAGE);
			        	disconnect();
			        	break;
			        case 8: //Get friend list
			        	if(header.length > 2){
				        	packet = header[2].split(",");
				        	
				        	for(int i = 0; i < packet.length; i++){
				        		if(packet[i].contains("º")){
						        	String[] split = packet[i].split("º");
						        	
						        	if(Integer.parseInt(split[0]) != Constants.getUser().getUser_id()){
						        		if(Constants.getFriend(Integer.parseInt(split[0])) != null){ //If we found a client update them.
						        			Client c = Constants.getFriend(Integer.parseInt(split[0]));
						        			c.setScreen_name(split[2]);
						        			c.setStatus(Integer.parseInt(split[3]));
						        			c.setOnline(Boolean.parseBoolean(split[4]));
						        		}else{ //Else add a new user in.
						        			Constants.addFriend(new Client(Integer.parseInt(split[0]),
						        					split[1],
						        					split[2],
						        					Integer.parseInt(split[3]),
						        					Boolean.parseBoolean(split[4]),
						        					false));
						        		}
						        		
						        		//If we get a message from this user or they come online again
							        	if(Constants.getPM(Integer.parseInt(split[0])) != null && !Constants.getPM(Integer.parseInt(split[0])).chatBox.isEnabled())
							        		Constants.getPM(Integer.parseInt(split[0])).userDC(false);
						        	}
				        		}
				        	}
				        	
				        	Constants.getFriendList().updateFriends();
			        	}
			        	break;
			        case 9: //Add friend
			        	if(header.length > 2){
				        	packet = header[2].split(",");
				        	
				        	for(int i = 0; i < packet.length; i++){
				        		if(packet[i].contains("º")){
						        	String[] split = packet[i].split("º");
						        	
						        	int response = JOptionPane.showConfirmDialog(null, 
						        			split[0] + " would like to be your friend.\n" +
						        					"Do you want to accept them?", 
						        			"Friend Request",
						        			JOptionPane.YES_NO_OPTION);
						        	
						        	if(response == JOptionPane.YES_OPTION)
						        		sendPacket(PacketHeaders.REQUEST_RESPONSE.getHeader() + "º" + true + "º" + split[0] + "º" + split[1]);
						        	else
						        		sendPacket(PacketHeaders.REQUEST_RESPONSE.getHeader() + "º" + false + "º" + split[0] + "º" + split[1]);
				        		}
				        	}
			        	}else if(packet.length > 1){
			        		JOptionPane.showMessageDialog(null,
									"The user " + packet[1] + " does not exist.\n" +
									"Check that you have splet the name correctly.",
									"Add Friend Error",
									JOptionPane.ERROR_MESSAGE);
			        	}
			        	break;
			        case 10: //Delete friend
			        	Constants.removeFriend(Integer.parseInt(packet[1]));
			        	Constants.getFriendList().updateFriends();
			        	break;
			        case 11: //Friend Request
			        	break;
			        case 12: //Disconnect friend
			        	if(Constants.getFriend(Integer.parseInt(packet[1])) != null){
			        		Constants.getFriend(Integer.parseInt(packet[1])).setOnline(false);
			        		Constants.getFriend(Integer.parseInt(packet[1])).setPlayedSound(false);
			        	}
			        	
			        	Constants.getFriendList().updateFriends();
			        	
			        	if(Constants.getPM(Integer.parseInt(packet[1])) != null)
			        		Constants.getPM(Integer.parseInt(packet[1])).userDC(true);
			        	break;
			        case 13: //Request Response
			        	break;
			        case 14: //Personal Message		
			        	if(!Boolean.valueOf(packet[6])){
				        	if(Constants.getPM(Integer.parseInt(packet[1])) == null){
				        		Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),false);
				        	}
				        	
				        	//Set window visible if applicable
				        	if(!Constants.getPM(Integer.parseInt(packet[1])).isVisible())
				        		Constants.getPM(Integer.parseInt(packet[1])).setVisible(true);
				        	
				        	//Send message
				        	Constants.getPM(Integer.parseInt(packet[1])).log(Constants.getFriend(Integer.parseInt(packet[1])).getScreen_name(), 
			        				packet[3], 
			        				packet[4],
			        				packet[5]);
				        	
				        	//Show message and play sound if applicable
			    			if(!Constants.getPM(Integer.parseInt(packet[1])).showing){
			    				Constants.getPM(Integer.parseInt(packet[1])).setTitle("(New Message) - " + Constants.getPM(Integer.parseInt(packet[1])).baseTitle);
			        			
			        			if(Constants.isPlaySounds())
			        				Constants.getAudioPlayer().play(AudioPlayer.MESSAGE);
			        		}
			        	}else{
				        	if(Constants.getPMGroup(Integer.parseInt(packet[1])) == null){
				        		Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),true);
				        	}
				        	
					        //Set window visible if applicable
					        if(!Constants.getPMGroup(Integer.parseInt(packet[1])).isVisible())
					        	Constants.getPMGroup(Integer.parseInt(packet[1])).setVisible(true);
					        	
					        //Send message
					        Constants.getPMGroup(Integer.parseInt(packet[1])).log(packet[2], 
				        			packet[3], 
				        			packet[4],
				        			packet[5]);
					        	
					        //Show message and play sound if applicable
				    		if(!Constants.getPMGroup(Integer.parseInt(packet[1])).showing){
				    			Constants.getPMGroup(Integer.parseInt(packet[1])).setTitle("(New Message) - " + Constants.getPMGroup(Integer.parseInt(packet[1])).baseTitle);
				        			
				        		if(Constants.isPlaySounds())
				        			Constants.getAudioPlayer().play(AudioPlayer.MESSAGE);
				        	}
			        	}
			        	break;
			        case 15: //View Profile
			        	Constants.getProfileView().setProfile(new Profile(packet[1],
			        			packet[2],
			        			packet[3],
			        			Byte.valueOf(packet[4]),
			        			Byte.valueOf(packet[5]),
			        			packet[6],
			        			packet[7],
			        			packet[8]));
			        	
			        	Constants.getProfileView().setVisible(true);
			        	break;
			        case 16: //Update Profile
			        	break;
			        case 17: //Update Password
			        	JOptionPane.showMessageDialog(null,
								"Your have entered the wrong password.",
								"Password Error",
								JOptionPane.ERROR_MESSAGE);
			        	break;
			        case 18: //Update Status
			        	// TODO Nothing? Except maybe a TOK packet?
			        	break;
			        case 19: //File Transfer
			        	if(Constants.getPM(Integer.parseInt(packet[1])) == null){
			        		Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),false);
			        	}
			        	
			        	Constants.getPM(Integer.parseInt(packet[1])).log("Server","Please accept or decline file transfer",null,null);
			        	
			        	if(Constants.isPlaySounds())
	        				Constants.getAudioPlayer().play(AudioPlayer.FILE);
			        	
			        	int response = JOptionPane.showConfirmDialog(null, 
			        			Constants.getFriend(Integer.parseInt(packet[1])).getUsername() + " would like to send you file(s).\n\n" +
			        					"File list:\n" +
			        					packet[3] +
			        					"\nWould you like to accept?", 
			        			"File Transfer",
			        			JOptionPane.YES_NO_OPTION);
			        	
			        	if(response == JOptionPane.YES_OPTION){
			        		cancelled = false;
			        		sendPacket(PacketHeaders.FILE_TRANSFER_RESPONSE.getHeader() + "º" + true + "º" + packet[1] + "º" + packet[2]);
			        	}else{
			        		cancelled = true;
			        		sendPacket(PacketHeaders.FILE_TRANSFER_RESPONSE.getHeader() + "º" + false + "º" + packet[1] + "º" + packet[2]);
			        	}
			        	break;
			        case 20: //File Transfer Response
			        	if(Boolean.parseBoolean(packet[3])){
			        		if(Constants.getPM(Integer.parseInt(packet[2])) == null){
			        			Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[2]))),false);
				        	}

			        		Constants.getPM(Integer.parseInt(packet[2])).fileTransfer.sendFiles();
			        	}else{
			        		JOptionPane.showMessageDialog(null,
			        				Constants.getFriend(Integer.parseInt(packet[2])).getUsername() + " has declined your file transfer.",
									"File Transfer Response",
									JOptionPane.INFORMATION_MESSAGE);
			        		
			        		Constants.getPM(Integer.parseInt(packet[2])).fileTransfer.sendFile.setEnabled(true);
			        	}
			        	break;
			        case 21: //File Start
			        	if(!cancelled){
				        	if(Constants.getPM(Integer.parseInt(packet[1])) == null){
			        			Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),false);
				        	}
				        	
				        	if(Constants.isShowFileTransfer())
				        		Constants.getPM(Integer.parseInt(packet[1])).fileTransfer.setVisible(true);
				        	
			        		Constants.getPM(Integer.parseInt(packet[1])).fileTransfer.fileStart(packet[3], Long.valueOf(packet[4]));
			        	}
			        	break;
			        case 22: //File Send
			        	if(!cancelled)
			        		Constants.getPM(Integer.parseInt(packet[1])).fileTransfer.fileSend(packet[3]);
			        	break;
			        case 23: //File End
			        	if(!cancelled)
			        		Constants.getPM(Integer.parseInt(packet[1])).fileTransfer.fileEnd();
			        	break;
			        case 24: //File Cancel
			        	if(!cancelled){
				        	cancelled = true;
				        	Constants.getPM(Integer.parseInt(packet[1])).fileTransfer.fileCancel();
			        	}
			        	break;
			        case 25: //Server Permissions
			        	JOptionPane.showMessageDialog(null,
								"You have tried to do a action that is not allowed by the server,\n" +
								"Please read the response from the server below.\n\n" +
								"Response: " + packet[1],
								"Server Permission Violation",
								JOptionPane.ERROR_MESSAGE);
			        	break;
			        case 26: //Nudge
			        	if(Constants.getPM(Integer.parseInt(packet[1])) == null){
		        			Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),false);
			        	}
			        	
			        	Constants.getPM(Integer.parseInt(packet[1])).setVisible(true);
			        	Constants.getPM(Integer.parseInt(packet[1])).receiveNudge();
			        	break;
			        case 27: //User typing
			        	if(Constants.getPM(Integer.parseInt(packet[1])) == null){
		        			Constants.addPmWindow(new PersonalMessage(Constants.getFriend(Integer.parseInt(packet[1]))),false);
			        	}
			        	
			        	Constants.getPM(Integer.parseInt(packet[1])).updateStatus(Boolean.valueOf(packet[3]));
			        	break;
			        case 28: //Session Closed
			        	disconnect();
						
			        	JOptionPane.showMessageDialog(null,
								"You have been disconnect because another session has been opened.",
								"Session Error",
								JOptionPane.ERROR_MESSAGE);
			        	break;
			        case 29: //Group Add
			        	String[] clients = header[2].split(",");
			        	String[] details;
			        	
			        	if(Constants.getPMGroup(Integer.valueOf(packet[1])) == null){
			        		Constants.addPmWindow(new PersonalMessage(new Client(Integer.valueOf(packet[1]),
			        				packet[2],
			        				packet[3],
			        				Integer.valueOf(packet[4]),
			        				true,
			        				true)),
			        				true);
			        	}
            			
            			for(String s : clients){
            				if(s != null){
            					details = s.split("º");
            					if(Integer.valueOf(details[0]) != Constants.getUser().getUser_id()){
            						if(Constants.getPM(Integer.valueOf(packet[1])) != null){
    	            					Constants.getPM(Integer.valueOf(packet[1])).addClient(new Client(Integer.valueOf(details[0]),
    	            							details[1],
    	            							details[2],
    	            							Integer.valueOf(details[3]),
    	            							true, 
    	            							true));
        			        		}else{
		            					Constants.getPMGroup(Integer.valueOf(packet[1])).addClient(new Client(Integer.valueOf(details[0]),
		            							details[1],
		            							details[2],
		            							Integer.valueOf(details[3]),
		            							true, 
		            							true));
        			        		}
            					}
            				}
            			}
            			
            			if(!Constants.getPMGroup(Integer.valueOf(packet[1])).isVisible())
			        		Constants.getPMGroup(Integer.valueOf(packet[1])).setVisible(true);
			        	break;
			        case 30: //Remove person from group
			        	Constants.getPMGroup(Integer.valueOf(packet[1])).log("Server",packet[2] + " has left the chat.",null,null);
			        	Constants.getPMGroup(Integer.valueOf(packet[1])).removeClient(Integer.valueOf(packet[1]));
			        	break;
			        default:
			        	break;
			        }
				}else{
					//Suspicious packet, better not process it.
					JOptionPane.showMessageDialog(null,
							"You have recived a packet that appears to be invaild,\n" +
							"we will not process this packet.\n\n" +
							"Please report this error with the information below.\n\n" +
							"Packet: " + fromServer,
							"Packet Error From Server",
							JOptionPane.ERROR_MESSAGE);
				}
				
		        /**
		         * Have a small sleep to let the CPU recover.
		         */
				Thread.sleep(100);
			}
		}catch(Exception e){
			if(connected){
				JOptionPane.showMessageDialog(null,
						"You have been disconnected from the Server.\n\n" +
						"Please read the error message below.\n\n" +
						Constants.getStack(e),
						"Packet Error From Server",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				
				for(PersonalMessage pm : Constants.getPmWindows())
					pm.dispose();
				
				Constants.getFriendList().dispose();
				Constants.getLoginGUI().setVisible(true);
				System.exit(-1);
			}
			
			while(!listener.isInterrupted())
				listener.interrupt();
			
			disconnect();
		}
	}
	
	/**
	 * Close the connection.
	 */
	public void disconnect(){
		try{	
			for(PersonalMessage pm : Constants.getPmWindows())
				pm.dispose();
			
			if(Constants.getFriendList() != null && Constants.getFriendList().isVisible())
				Constants.getFriendList().dispose();
			
			Constants.getLoginGUI().setVisible(true);
			
			connected = false;
			out.close();
			in.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Send a packet to the server.
	 * 
	 * @param packet
	 */
	public void sendRawPacket(String packet){
		if(packet != null){
			out.println(Encoder.encode(packet + "¢"));
			out.flush();
		}
	}
	
	/**
	 * Send a packet to the server.
	 * 
	 * @param packet
	 */
	public void sendPacket(String packet){
		if(packet != null){
			out.println(encoder.encrypt(packet + "¢"));
			out.flush();
		}
	}

	public boolean isLoggedin() {
		return loggedin;
	}

	public void setLoggedin(boolean loggedin) {
		this.loggedin = loggedin;
	}
	
}