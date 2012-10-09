package net.undergroundim.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

import net.undergroundim.client.AudioPlayer;
import net.undergroundim.client.BrowserLaunch;
import net.undergroundim.client.Client;
import net.undergroundim.client.Constants;
import net.undergroundim.client.Emoticon;
import net.undergroundim.client.FileWriter;
import net.undergroundim.client.Timer;
import net.undergroundim.client.networking.PacketHeaders;


/**
 * 
 * @author Troy
 *
 */
public class PersonalMessage extends JFrame implements ComponentListener, KeyListener, FocusListener, HyperlinkListener,  WindowFocusListener, 
	DropTargetListener, DragSourceListener, DragGestureListener, ActionListener, WindowListener {
	private static final long serialVersionUID = -7212578453796141050L;
	
	public ArrayList<Client> clients = new ArrayList<Client>();
	private Vector<String> clientsList = new Vector<String>();
	public FileTransfer fileTransfer;
	public String baseTitle;
	public JTextPane log = new JTextPane();
	public JTextArea chatBox = new JTextArea();
	public JList<String> clientList = new JList<String>();
	private JScrollPane logContainer;
	private JScrollPane chatBoxContainer;
	private JScrollPane clientContainer;
	
	private JButton fontButton = new JButton();
	private JButton emoticonButton = new JButton();
	private JButton nudgeButton = new JButton();
	private JButton addButton = new JButton();
	private JButton transferButton = new JButton();
	
	private JLabel statusLabel = new JLabel(); 
	
	private HTMLEditorKit kit = new HTMLEditorKit();
    private HTMLDocument doc = new HTMLDocument();
    private StyleSheet styleSheet = kit.getStyleSheet();
    
	private boolean shiftDown = false;
	public boolean showing = true;
	public boolean groupChat = false;
	
	private String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private String URL_PATTERN_2 = "([^\"|\\'])\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private Pattern p;
	private Matcher regexMatcher;
	
	public PopupMenu popupMenu;
	private boolean dc, typing = false;
	
	private DropTarget dropTarget = new DropTarget (this, this);
    private DragSource dragSource = DragSource.getDefaultDragSource();
    
    private Timer nudgeTimer = new Timer(10000);
    
    private String clientSend;

	/**
	 * Construct a new PM window.
	 */
	public PersonalMessage(Client client){
		this.clients.add(client);
		this.setIconImage(Constants.mailIcon);
		baseTitle = client.getScreen_name() + " | " + client.getUsername();
		this.setTitle(baseTitle + " - PM");
		this.setSize(600, 450);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setJMenuBar(new MenuBar().getMenuBar());
		
		this.setMinimumSize(new Dimension(400,300));
		this.setPreferredSize(new Dimension(700, 500));
		this.addComponentListener(this);
		this.addFocusListener(this);
		this.addWindowFocusListener(this);
		this.addWindowListener(this);
		
		//Logs
		log.setBounds(0, 0, 585, 300);
		log.setEditable(false);
		log.setFont(new Font("Arial", Font.PLAIN, 12));

		styleSheet.addRule("a:link {color:blue}");
				
		log.setEditorKit(kit);
		log.setDocument(doc);
		log.addHyperlinkListener(this);
		log.setAutoscrolls(false);

		logContainer = new JScrollPane(log,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		logContainer.setBounds(0, 0, 585, 300);
		logContainer.setAutoscrolls(false);
		logContainer.setViewportView(log);
		
				
		//Chat box
		chatBox.setBounds(0, 0, 300, 130);
		chatBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		chatBox.addKeyListener(this);
		chatBox.setLineWrap(true);
		chatBox.setWrapStyleWord(true);
		chatBox.setToolTipText("Enter to submit, Shift+Enter for new line");
		chatBox.setDropTarget(dropTarget);
		
		dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
		
		chatBoxContainer = new JScrollPane(chatBox,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatBoxContainer.setBounds(0, 300, 450, 90);

		//Buttons
		fontButton.setBounds(0, 279, 50, 20);
		fontButton.setIcon(Constants.fontIcon2);
		
		emoticonButton.setBounds(51, 279, 25, 20);
		emoticonButton.setIcon(Constants.emoticonIcon);
		
		nudgeButton.setBounds(77, 279, 30, 20);
		nudgeButton.setIcon(Constants.nudgeIcon);
		
		addButton.setBounds(getWidth() -57, 279, 20, 20);
		addButton.setIcon(Constants.addIcon);
		addButton.setToolTipText("Add Buddy to conversation");
		
		transferButton.setBounds(getWidth() -36, 279, 20, 20);
		transferButton.setIcon(Constants.transferIcon);
		transferButton.setToolTipText("File Transfer");
		
		fontButton.addActionListener(this);
		emoticonButton.addActionListener(this);
		nudgeButton.addActionListener(this);
		addButton.addActionListener(this);
		transferButton.addActionListener(this);
		
		//Status label
		statusLabel.setBounds(115, 279, 200, 20);
		
		//Client list
		clientList.setBounds(450, 0, 135, getHeight() - 60);
		clientContainer = new JScrollPane(clientList,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		clientContainer.setBounds(450, 0, 135, getHeight() - 60);
		
		this.add(logContainer);
		this.add(chatBoxContainer);
		this.add(fontButton);
		this.add(emoticonButton);
		this.add(nudgeButton);
		this.add(addButton);
		this.add(transferButton);
		this.add(statusLabel);
		
		chatBox.addFocusListener(this);
		chatBox.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"");
		
		//Set chat box font.
		chatBox.setFont(new Font(Constants.getFontDialog().lastFontFace,
				Constants.getFontDialog().lastFontModifiers,
				Constants.convertSize(Constants.getFontDialog().lastFontSize)));
		
		chatBox.setForeground(Constants.getFontDialog().colourPanel.getBackground());
		
		popupMenu = new PopupMenu(this,null);
		fileTransfer = new FileTransfer(clients.get(0));
	}
	
	/**
	 * Log the message with date and time stamp
	 * Now also saves to a file if logging from a script.
	 * 
	 * @param msg
	 */
	public void log(String username, String msg, String string, String stringEnd){	
		//If the font is null, set it to the default.
		if(string == null || stringEnd == null){
			string = "<font face='Dialog' size='3' color='gray'>";
			stringEnd = "</font>";
		}else if(!Constants.isFontEnabled()){
			string = "<font face='Dialog' size='3' color='#333333'>";
			stringEnd = "</font>";
		}
			
		//Trim the log if applicable.
		if(Constants.isTrimChatLog()){
			String[] count = log.getText().split("\n");
				
			if(count.length > Constants.getLineCount())
				log.setText("");
		}
			
		//Append to styled doc.
		try{			
			for(Emoticon e : Constants.getEmotions()){
				msg = msg.replaceAll(e.getKey(), e.getValue());
			}

			//Compile the search pattern
			if(msg.startsWith("http") || msg.startsWith("www") || msg.startsWith("ftp"))
				p = Pattern.compile(URL_PATTERN);
			else
				p = Pattern.compile(URL_PATTERN_2);
				
			regexMatcher = p.matcher(msg);
				
			//Make URL's into clickable links
			while(regexMatcher.find()){
				msg = msg.replace(regexMatcher.group(), 
								"<font color=\"blue\"><a href=\"" + 
								regexMatcher.group() + 
								"\">" + regexMatcher.group() + 
								"</a></font>");
			}
				
			//Insert the msg
			kit.insertHTML(doc, doc.getLength(), "<font color='gray' face='Dialog' size='3pt'><b>" + 
					Constants.getDate() + " - " + 
					username + ": </b></font>" +
					string +
					msg.replace("\n", "<br>") +
					stringEnd, 0, 0, null);
				
			//Set the caret position
			log.setCaretPosition(doc.getLength());
		}catch(Exception e){e.printStackTrace();}
		
		//Save log if applicable.
		if(Constants.isSaveLogFiles())
			FileWriter.writeToFile(Constants.getLogFileName(), Constants.getDate() + " - " + username + ": " + msg, true, true);
	}
	
	/**
	 * The method to send the message.
	 */
	public void sendMessage(){
		chatBox.setEnabled(false);
		String text = chatBox.getText();
		
		if(text.isEmpty()){
			JOptionPane.showMessageDialog(null,
				    "You must enter in some text before trying to send a message.",
				    "Message Error",
				    JOptionPane.ERROR_MESSAGE);
		}else{
			log(Constants.getUser().getScreen_name(),
					text,
					Constants.getFontDialog().getFontString(),
					Constants.getFontDialog().getFontStringEnd());
				
			clientSend = "";
			for(Client client : clients){
				if(clients.size() > 1)
					clientSend += client.getUser_id() + ",";
				else
					clientSend += client.getUser_id();
			}
			
			Constants.getPacketManager().sendPacket(PacketHeaders.PERSONAL_MESSAGE.getHeader() + "º" + 
					Constants.getUser().getUser_id() + "º" +
					Constants.getUser().getScreen_name() + "º" +
					clientSend + "º" +
					text.replaceAll("ª|º|¢", "") + "º" +
					Constants.getFontDialog().getFontString() + "º" +
					Constants.getFontDialog().getFontStringEnd() + "º" +
					groupChat);
			
			
			chatBox.setText("");
			
			if(clients.size() < 2)
			Constants.getPacketManager().sendPacket(PacketHeaders.USER_WRITING.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					clients.get(0).getUser_id() + "º" +
					false);
		}
		
		chatBox.setEnabled(true);
		chatBox.requestFocus();
	}
	
	/**
	 * User has DC'd, disable everything.
	 * 
	 * @param dc
	 */
	public void userDC(boolean dc){
		this.dc = dc;
		
		if(dc){
			this.setTitle("(Offline) - " + baseTitle);
			chatBox.setEnabled(false);
			fontButton.setEnabled(false);
			emoticonButton.setEnabled(false);
			nudgeButton.setEnabled(false);
			transferButton.setEnabled(false);
			fileTransfer.pickFile.setEnabled(false);
			fileTransfer.sendFile.setEnabled(false);
			addButton.setEnabled(false);
			chatBox.setText("User is offline.");
			updateStatus(false);
		}else{
			this.setTitle(baseTitle + " - PM");
			chatBox.setEnabled(true);
			fontButton.setEnabled(true);
			emoticonButton.setEnabled(true);
			nudgeButton.setEnabled(true);
			transferButton.setEnabled(true);
			fileTransfer.pickFile.setEnabled(true);
			fileTransfer.sendFile.setEnabled(true);
			addButton.setEnabled(true);
			chatBox.setText("");
		}
	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED && e.getURL() != null) {
			BrowserLaunch.openURL(e.getURL().toString());
		}
	}

	@Override
	public void focusGained(FocusEvent e) {
		if(!showing){
			showing = true;
			
			if(this.getTitle() != baseTitle + " - PM" && !dc){
				this.setTitle(baseTitle + " - PM");
			}
			
			log.setCaretPosition(doc.getLength());
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		showing = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_ENTER:
			if(shiftDown){
				chatBox.setText(chatBox.getText() + "\n");
			}else{
				sendMessage();
			}
			break;
		case KeyEvent.VK_SHIFT:
			shiftDown = true;
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_SHIFT:
			shiftDown = false;
			break;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(!chatBox.getText().isEmpty() && !typing && clients.size() < 2){
			typing = true;
			
			Constants.getPacketManager().sendPacket(PacketHeaders.USER_WRITING.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					clients.get(0).getUser_id() + "º" +
					true);
		}else if(chatBox.getText().isEmpty() && typing && clients.size() < 2){
			typing = false;
			
			Constants.getPacketManager().sendPacket(PacketHeaders.USER_WRITING.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					clients.get(0).getUser_id() + "º" +
					false);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if(!groupChat){
			log.setSize(getWidth() -15, getHeight() -171);
			logContainer.setSize(getWidth() -15, getHeight() -171);
		}else{
			log.setSize(getWidth() -150, getHeight() -171);
			logContainer.setSize(getWidth() -150, getHeight() -171);
			
			clientContainer.setBounds(getWidth() -150, 0, 135, getHeight() - 171);
		}
			
		chatBox.setSize(getWidth() -15, chatBox.getHeight());
		chatBoxContainer.setBounds(0, logContainer.getHeight() +21, getWidth() -15, chatBoxContainer.getHeight());
			
		fontButton.setBounds(0, getHeight() -171, 50, 20);
		emoticonButton.setBounds(51, getHeight() -171, 25, 20);
		nudgeButton.setBounds(77, getHeight() -171, 30, 20);
		addButton.setBounds(getWidth() -57, getHeight() -171, 20, 20);
		transferButton.setBounds(getWidth() -36, getHeight() -171, 20, 20);
		statusLabel.setBounds(115, getHeight() -171, statusLabel.getWidth(), statusLabel.getHeight());
			
		repaint();
			
		log.setCaretPosition(doc.getLength());
	}

	@Override
	public void componentShown(ComponentEvent e) {
		chatBox.requestFocus();
		log.setCaretPosition(doc.getLength());
	}
	
	@Override
	public void windowGainedFocus(WindowEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				logContainer.getVerticalScrollBar().setValue(logContainer.getVerticalScrollBar().getMaximum());
				chatBox.requestFocus();
			}
		});
	}
	
	@Override
	public void drop(DropTargetDropEvent e) {
		 try{
             Transferable tr = e.getTransferable();
             
             if(tr.isDataFlavorSupported (DataFlavor.javaFileListFlavor)){
                 e.acceptDrop (DnDConstants.ACTION_COPY);
                 List<?> fileList = (List<?>)tr.getTransferData(DataFlavor.javaFileListFlavor);
                 fileTransfer.addFiles((File[])fileList.toArray());
                 fileTransfer.fileTransfer();
             }
             
         }catch(Exception es){
        	 es.printStackTrace();
         }
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent e) {
		e.acceptDrag(DnDConstants.ACTION_COPY_OR_MOVE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == fontButton){
			Constants.getFontDialog().setVisible(true);
		}
		
		else if(e.getSource() == emoticonButton){
			Constants.getEmoticonDialog().setTarget(this);
			Constants.getEmoticonDialog().setLocation(getX() + 58, getY() + logContainer.getHeight() - 112);
			Constants.getEmoticonDialog().setVisible(true);
		}
		
		else if(e.getSource() == nudgeButton && clients.size() < 2){
			if(nudgeTimer.isUp()){
				log("Server","You just sent a Nudge!",null,null);
				
				Constants.getPacketManager().sendPacket(PacketHeaders.NUDGE.getHeader() + "º" +
						Constants.getUser().getUser_id() + "º" +
						clients.get(0).getUser_id());
				
				nudgeTimer.reset();
				
				if(Constants.isPlaySounds())
		    		Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
			}else{
				log("Server","You can only send one nudge every 10 seconds!",null,null);
			}
		}
		
		else if(e.getSource() == addButton){
			Constants.getAddFriendDialog().setTarget(this);
			Constants.getAddFriendDialog().updateFriends();
			Constants.getAddFriendDialog().setVisible(true);
		}
		
		else if(e.getSource() == transferButton){
			fileTransfer.setVisible(true);
		}
	}
	
	/**
	 * Receive a nudge
	 */
	public void receiveNudge(){
		log("Server", clients.get(0).getScreen_name() + " sent you a Nudge!",null,null);
		
		if(Constants.isPlaySounds())
    		Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
	}
	
	/**
	 * Update the status text
	 */
	public void updateStatus(boolean typing){
		if(typing){
			statusLabel.setText(clients.get(0).getScreen_name() + " is writing a message...");
		}else{
			statusLabel.setText("");
		}
	}
	
	/**
	 * This will adjust the window to be group
	 * chat enabled.
	 */
	public void setGroupChat(){
		log.setSize(getWidth() -150, getHeight() -171);
		logContainer.setSize(getWidth() -150, getHeight() -171);
		
		chatBox.setSize(getWidth() -15, chatBox.getHeight());
		chatBoxContainer.setBounds(0, logContainer.getHeight() +21, getWidth() -15, chatBoxContainer.getHeight());
		
		fontButton.setBounds(0, getHeight() -171, 50, 20);
		emoticonButton.setBounds(51, getHeight() -171, 25, 20);
		nudgeButton.setBounds(77, getHeight() -171, 30, 20);
		addButton.setBounds(getWidth() -57, getHeight() -171, 20, 20);
		transferButton.setBounds(getWidth() -36, getHeight() -171, 20, 20);
		statusLabel.setBounds(115, getHeight() -171, statusLabel.getWidth(), statusLabel.getHeight());
		
		clientContainer.setBounds(getWidth() -150, 0, 135, getHeight() - 171);
		
		//Disable certain features
		transferButton.setEnabled(false);
		nudgeButton.setEnabled(false);
		
		this.add(clientContainer);
	}
	
	/**
	 * Update the client list.
	 */
	public void updateClientList(){
		clientsList.removeAllElements();
		
		for(Client c : clients){
			clientsList.add(c.getScreen_name());
		}
		
		clientList.setListData(clientsList);
		clientList.repaint();
	}
	
	/**
	 * Method to add clients to the chat.
	 * 
	 * @param client
	 */
	public void addClient(Client client){
		boolean add = true;
		for(Client c : clients)
			if(c.getUser_id() == client.getUser_id())
				add = false;
		
		if(add)
			clients.add(client);
		
		if(clients.size() > 1 && !groupChat){
			groupChat = true;
			setGroupChat();
		}
		
		updateClientList();
	}
	
	/**
	 * Remove clients.
	 * 
	 * @param client
	 */
	public void removeClient(int user_id){
		Client toRemove = null;
		
		for(Client c : clients)
			if(c.getUser_id() == user_id)
				toRemove = c;
		
		clients.remove(toRemove);
		toRemove = null;
		
		if(clients.size() < 2 && groupChat){
			groupChat = false;
			this.remove(clientContainer);
			log.setSize(getWidth() -15, getHeight() -171);
			logContainer.setSize(getWidth() -15, getHeight() -171);
			//Disable certain features
			transferButton.setEnabled(true);
			nudgeButton.setEnabled(true);
		}
		
		updateClientList();
		this.repaint();
	}
	
	/**
	 * Close the group chat.
	 */
	@Override
	public void windowClosing(WindowEvent e) {
		clientSend = "";
		
		if(groupChat){
			for(Client c : clients){
				clientSend += c.getUser_id() + ",";
			}
			
			Constants.getPacketManager().sendPacket(PacketHeaders.GROUP_REMOVE.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					Constants.getUser().getScreen_name() + "º" +
					clientSend);
			
			Constants.removePMWindow(this);
		}
	}
	
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
	public void windowLostFocus(WindowEvent e) {}
	public void dragExit(DropTargetEvent arg0) {}
	public void dragOver(DropTargetDragEvent arg0) {}
	public void dropActionChanged(DropTargetDragEvent arg0) {}
	public void dragGestureRecognized(DragGestureEvent arg0) {}
	public void dragDropEnd(DragSourceDropEvent arg0) {}
	public void dragEnter(DragSourceDragEvent e) {}
	public void dragExit(DragSourceEvent arg0) {}
	public void dragOver(DragSourceDragEvent arg0) {}
	public void dropActionChanged(DragSourceDragEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

}
