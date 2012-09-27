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
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	DropTargetListener, DragSourceListener, DragGestureListener, ActionListener {
	private static final long serialVersionUID = -7212578453796141050L;
	
	public Client client;
	public FileTransfer fileTransfer;
	public String baseTitle;
	public JTextPane log = new JTextPane();
	public JTextArea chatBox = new JTextArea();
	private JScrollPane logContainer;
	private JScrollPane chatBoxContainer;
	
	private JButton fontButton = new JButton();
	private JButton emoticonButton = new JButton();
	private JButton nudgeButton = new JButton();
	private JButton transferButton = new JButton();
	
	private JLabel statusLabel = new JLabel(); 
	
	private HTMLEditorKit kit = new HTMLEditorKit();
    private HTMLDocument doc = new HTMLDocument();
    private StyleSheet styleSheet = kit.getStyleSheet();
    
	private boolean shiftDown = false;
	public boolean showing = true;
	
	private String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private String URL_PATTERN_2 = "([^\"|\\'])\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	private Pattern p;
	private Matcher regexMatcher;
	
	public PopupMenu popupMenu;
	private boolean dc, typing = false;
	
	private DropTarget dropTarget = new DropTarget (this, this);
    private DragSource dragSource = DragSource.getDefaultDragSource();
    
    private ImageIcon fontIcon = new ImageIcon(MenuBar.class.getResource("/icons/font.png"));
    private ImageIcon emoticonIcon = new ImageIcon(MenuBar.class.getResource("/icons/regular_smile.png"));
    private ImageIcon nudgeIcon = new ImageIcon(MenuBar.class.getResource("/icons/nudge.png"));
    private ImageIcon transferIcon = new ImageIcon(MenuBar.class.getResource("/icons/Transfer.png"));
    
    private Timer nudgeTimer = new Timer(10000);

	/**
	 * Construct a new PM window.
	 */
	public PersonalMessage(Client client){
		this.client = client;
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
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatBoxContainer.setBounds(0, 300, 450, 90);

		//Buttons
		fontButton.setBounds(0, 279, 50, 20);
		fontButton.setIcon(fontIcon);
		
		emoticonButton.setBounds(51, 279, 25, 20);
		emoticonButton.setIcon(emoticonIcon);
		
		nudgeButton.setBounds(77, 279, 30, 20);
		nudgeButton.setIcon(nudgeIcon);
		
		transferButton.setBounds(getWidth() -36, 279, 20, 20);
		transferButton.setIcon(transferIcon);
		
		fontButton.addActionListener(this);
		emoticonButton.addActionListener(this);
		nudgeButton.addActionListener(this);
		transferButton.addActionListener(this);
		
		//Status label
		statusLabel.setBounds(115, 279, 200, 20);
		
		this.add(logContainer);
		this.add(chatBoxContainer);
		this.add(fontButton);
		this.add(emoticonButton);
		this.add(nudgeButton);
		this.add(transferButton);
		this.add(statusLabel);
		
		chatBox.addFocusListener(this);
		chatBox.getInputMap().put(KeyStroke.getKeyStroke("ENTER"),"");
		
		//Set chat box font.
		chatBox.setFont(new Font(Constants.getFontDialog().lastFontFace,
				Constants.getFontDialog().lastFontModifiers,
				Constants.convertSize(Constants.getFontDialog().lastFontSize)));
		
		chatBox.setForeground(Constants.getFontDialog().colourPanel.getBackground());
		
		popupMenu = new PopupMenu(this,null,null);
		fileTransfer = new FileTransfer(this.client);
	}
	
	/**
	 * Log the message with date and time stamp
	 * Now also saves to a file if logging from a script.
	 * 
	 * @param msg
	 */
	public void log(String username, String msg, String string, String stringEnd){	
		//If the font is null, set it to the default.
		if(string == null | stringEnd == null){
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
				
			Constants.getPacketManager().sendPacket(PacketHeaders.PERSONAL_MESSAGE.getHeader() + "º" + 
					Constants.getUser().getUser_id() + "º" +
					client.getUser_id() + "º" +
					text.replaceAll("ª|º|¢", "") + "º" +
					Constants.getFontDialog().getFontString() + "º" +
					Constants.getFontDialog().getFontStringEnd());
			
			chatBox.setText("");
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
		if(!chatBox.getText().isEmpty() && !typing){
			typing = true;
			
			Constants.getPacketManager().sendPacket(PacketHeaders.USER_WRITING.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					client.getUser_id() + "º" +
					true);
		}else if(chatBox.getText().isEmpty() && typing){
			typing = false;
			
			Constants.getPacketManager().sendPacket(PacketHeaders.USER_WRITING.getHeader() + "º" +
					Constants.getUser().getUser_id() + "º" +
					client.getUser_id() + "º" +
					false);
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		log.setSize(getWidth() -15, getHeight() -171);
		logContainer.setSize(getWidth() -15, getHeight() -171);
		
		chatBox.setSize(getWidth() -15, chatBox.getHeight());
		chatBoxContainer.setBounds(0, logContainer.getHeight() +21, getWidth() -15, chatBoxContainer.getHeight());
		
		fontButton.setBounds(0, getHeight() -171, 50, 20);
		emoticonButton.setBounds(51, getHeight() -171, 25, 20);
		nudgeButton.setBounds(77, getHeight() -171, 30, 20);
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
		
		else if(e.getSource() == nudgeButton){
			if(nudgeTimer.isUp()){
				log("Server","You just sent a Nudge!",null,null);
				
				Constants.getPacketManager().sendPacket(PacketHeaders.NUDGE.getHeader() + "º" +
						Constants.getUser().getUser_id() + "º" +
						client.getUser_id());
				
				nudgeTimer.reset();
				
				if(Constants.isPlaySounds())
		    		Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
			}else{
				log("Server","You can only send one nudge every 10 seconds!",null,null);
			}
		}
		
		else if(e.getSource() == transferButton){
			fileTransfer.setVisible(true);
		}
	}
	
	/**
	 * Receive a nudge
	 */
	public void receiveNudge(){
		log("Server", client.getScreen_name() + " sent you a Nudge!",null,null);
		
		if(Constants.isPlaySounds())
    		Constants.getAudioPlayer().play(AudioPlayer.NUDGE);
	}
	
	/**
	 * Update the status text
	 */
	public void updateStatus(boolean typing){
		if(typing){
			statusLabel.setText(client.getScreen_name() + " is writing a message...");
		}else{
			statusLabel.setText("");
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

}
