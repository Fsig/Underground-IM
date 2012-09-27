package net.undergroundim.client.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import net.undergroundim.client.AudioPlayer;
import net.undergroundim.client.Client;
import net.undergroundim.client.Constants;
import net.undergroundim.client.networking.PacketHeaders;


/**
 * 
 * @author Troy
 *
 */
public class FileTransfer extends JFrame implements ActionListener, KeyListener, MouseListener, ComponentListener{
	private static final long serialVersionUID = -7022069724226966364L;
	
	public Client client;
	private JPopupMenu menu = new JPopupMenu();
	private JMenuItem deleteMenu = new JMenuItem("Delete");
	private ImageIcon deleteIcon = new ImageIcon(PopupMenu.class.getResource("/icons/delete.png"));
	
	private JList<String> transferList = new JList<String>();
	private JScrollPane transferContainer;
	private Border transferBorder = BorderFactory.createTitledBorder("Files To Transfer");
	
	private JList<String> transferLog = new JList<String>();
	private JScrollPane transferLogContainer;
	private Border transferLogBorder = BorderFactory.createTitledBorder("Transfer Log");
	
	private JLabel statusLabel = new JLabel("Status:");
	private JLabel statusLabel2 = new JLabel("Select file(s) to transfer.");
	private JProgressBar progressBar = new JProgressBar();
	
	public JButton pickFile = new JButton("Pick File(s)");
	public JButton sendFile = new JButton("Send File(s)");
	
	private JFileChooser fileChooser = new JFileChooser();
	
	private ArrayList<File> transferFiles = new ArrayList<File>();
	private Vector<String> fileNames = new Vector<String>();
	private Vector<String> transLog = new Vector<String>();
	
	public boolean transfer, host, cancelled = false;
	
	private FileOutputStream fileOut;
	private String fileName;
	private long fileLength, startTime, endTime;
	private long total;
	private byte[] bytes;

	/**
	 * Construct a new File Transfer.
	 */
	public FileTransfer(Client client){
		this.client = client;
		this.setIconImage(Constants.fileTransferIcon);
		this.setTitle(client.getUsername() + " - File Transfer");
		this.setSize(500, 390);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addComponentListener(this);
		this.setMinimumSize(new Dimension(500, 250));
		
		//Menu
		deleteMenu.setIcon(deleteIcon);
		menu.add(deleteMenu);
		deleteMenu.addActionListener(this);
		
		//Transfer List
		transferList.setBounds(0, 0, 150, 150);
		transferList.setBorder(transferBorder);
		transferList.addKeyListener(this);
		transferList.addMouseListener(this);
		
		transferContainer = new JScrollPane(transferList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		transferContainer.setBounds(5, 5, 235, 250);
		
		//Transfer Log
		transferLog.setBounds(0, 0, 150, 150);
		transferLog.setBorder(transferLogBorder);
		
		transferLogContainer = new JScrollPane(transferLog,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		transferLogContainer.setBounds(245, 5, 235, 250);
		
		//Status
		statusLabel.setBounds(5, 260, 60, 20);
		statusLabel2.setBounds(50, 260, 470, 20);
		statusLabel2.setFont(new Font("Dialog", 0, 12));
		progressBar.setBounds(5, 285, 473, 25);
		progressBar.setStringPainted(true);
		
		//Add buttons
		pickFile.setBounds(5, 320, 235, 25);
		sendFile.setBounds(245, 320, 235, 25);
		
		pickFile.addActionListener(this);
		sendFile.addActionListener(this);
		
		//File chooser
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setDialogTitle("Select File(s)");

		this.add(transferContainer);
		this.add(transferLogContainer);
		this.add(statusLabel);
		this.add(statusLabel2);
		this.add(progressBar);
		this.add(pickFile);
		this.add(sendFile);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == pickFile){
			int option = fileChooser.showOpenDialog(this);
			
			if(option == JFileChooser.APPROVE_OPTION) {
				addFiles(fileChooser.getSelectedFiles());
	        }
		}
		
		else if(e.getSource() == sendFile){
			if(!transfer){
				fileTransfer();
			}else{
				transfer = false;
				
				Constants.getPacketManager().sendPacket(PacketHeaders.FILE_CANCEL.getHeader() + "º" +
						Constants.getUser().getUser_id() + "º" +
						client.getUser_id());
			}
		}
		
		else if(e.getSource() == deleteMenu){
			removeFiles();
		}
	}
	
	/**
	 * Add files to the transfer list.
	 * 
	 * @param files
	 */
	public void addFiles(File[] files){
		for(File f : files){
			transferFiles.add(f);
			fileNames.add(f.getName());
		}
		
		transferList.setListData(fileNames);
		transferList.repaint();
	}
	
	/**
	 * Attempt to start a file transfer.
	 */
	public void fileTransfer(){
		sendFile.setEnabled(false);
		String fileList = "";
		
		for(File f : transferFiles){
			fileList += f.getName() + " - " + Constants.getMod(f.length()) + "\n";
		}
		
		Constants.getPacketManager().sendPacket(PacketHeaders.FILE_TRANSFER.getHeader() + "º" +
				Constants.getUser().getUser_id() + "º" +
				client.getUser_id() + "º" +
				fileList);
		
		Constants.getPM(client.getUser_id()).log("Server","Waiting for user to accept or decline file transfer",null,null);
	}
	
	/**
	 * Remove files from the transfer list.
	 */
	public void removeFiles(){
		if(transferList.getSelectedValue() != null){
			List<String> files = transferList.getSelectedValuesList();
			for(String s : files){
				fileNames.remove(s);
				removeFile(s.split("-")[0].trim());
			}
			
			transferList.setListData(fileNames);
			transferList.repaint();
		}
	}
	
	/**
	 * Removed the selected file from the list.
	 * 
	 * @param name
	 */
	public void removeFile(String name){
		for(File f : transferFiles){
			if(f.getName().equals(name)){
				transferFiles.remove(f);
				break;
			}	
		}
	}
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_DELETE:
			removeFiles();
			break;
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == transferList && e.isPopupTrigger()){
			menu.show(e.getComponent(), e.getX(), e.getY());
		}
	}
	
	/**
	 * Send the files.
	 */
	public void sendFiles(){
		transfer = true;
		host = true;
		cancelled = false;
		sendFile.setEnabled(true);
		pickFile.setEnabled(false);
		sendFile.setText("Cancel");
		
		 Thread thread = new Thread() {
		 @Override
		 public void run(){
			 ArrayList<String> filesToRemove = new ArrayList<String>();
			 for(File f : transferFiles){
				 if(transfer){
					 try{
						 fileName = f.getName();
						 fileLength = f.length();
						 statusLabel2.setText("Uploading " + f.getName());
						 transLog.add("Uploading " + f.getName() + " (" + Constants.getMod(f.length()) + ")");
						 transferLog.setListData(transLog);
						 transferLog.repaint();
						 Constants.getPM(client.getUser_id()).log("Server","Uploading " + f.getName() + " (" + Constants.getMod(f.length()) + ")",null,null);
						 progressBar.setValue(0);
	
						 FileInputStream in = null;
						 try {
							 in = new FileInputStream(f.getAbsoluteFile());
						 }catch (FileNotFoundException e1) {
							 JOptionPane.showMessageDialog(null,
									   "Please read the below error message:\n\n" +
									   Constants.getStack(e1),
									   "Transfer Error",
									   JOptionPane.ERROR_MESSAGE);
						 }
						 
						 Constants.getPacketManager().sendPacket(PacketHeaders.FILE_START.getHeader() + "º" +
								 Constants.getUser().getUser_id() + "º" +
								 client.getUser_id() + "º" +
								 f.getName() + "º" +
								 f.length());
						
						 long starttime, endtime;
						  
						 int read = 0;
						 long total = 0;
						 byte[] tmp = new byte[1024 * 10];
						 int fileLength = (int)f.length();
						 String fileL = Constants.getMod(fileLength);
						
						 starttime = System.currentTimeMillis();
						 try {
							while((read = in.read(tmp)) != -1 && transfer){
								total += read;
								
								Constants.getPacketManager().sendPacket(PacketHeaders.FILE_SEND.getHeader() + "º" +
										Constants.getUser().getUser_id() + "º" +
										client.getUser_id() + "º" +
										Constants.bytesToString(tmp));
								
								progressBar.setValue((int)(total*100/fileLength));
								
								long elapsedTime = System.currentTimeMillis() - starttime;
								statusLabel2.setText("Uploading: " + f.getName() + " - " + Constants.getMod(total) + " / " + fileL + " - " + Constants.getMod((long) (1000f * total / elapsedTime)) + "s");
							}
							
							if(!transfer){
								progressBar.setValue(0);
								transLog.add("Cancelled " + f.getName());
								transferLog.setListData(transLog);
								transferLog.repaint();
								Constants.getPM(client.getUser_id()).log("Server","Cancelled " + f.getName(),null,null);
								statusLabel2.setText("Cancelled " + f.getName());
							}
						 }catch(IOException e){
							 JOptionPane.showMessageDialog(null,
									   "Please read the below error message:\n\n" +
									   Constants.getStack(e),
									   "Transfer Error",
									   JOptionPane.ERROR_MESSAGE);
						 }
						 
						 in.close();
						 if(transfer){
							 endtime = (System.currentTimeMillis() - starttime) / 1000;
							 statusLabel2.setText("Complete");
							 progressBar.setValue(0);
							 
							 transLog.add("Uploaded " + f.getName() + " (" + Constants.getMod(f.length()) + ")" + " in " + endtime + " seconds");
							 Constants.getPM(client.getUser_id()).log("Server","Uploaded " + f.getName() + " (" + Constants.getMod(f.length()) + ")" + " in " + endtime + " seconds",null,null);
							 transferLog.setListData(transLog);
							 transferLog.repaint();
							 
							 Constants.getPacketManager().sendPacket(PacketHeaders.FILE_END.getHeader() + "º" +
									 Constants.getUser().getUser_id() + "º" +
									 client.getUser_id());
							 
							 filesToRemove.add(f.getName());
							 
							 if(Constants.isPlaySounds())
									Constants.getAudioPlayer().play(AudioPlayer.ALERT);
						 }
					 }catch(Exception es){
						 JOptionPane.showMessageDialog(null,
								   "Please read the below error message:\n\n" +
								   Constants.getStack(es),
								   "Transfer Error",
								   JOptionPane.ERROR_MESSAGE);
						 transfer = false;
						 pickFile.setEnabled(true);
						 sendFile.setText("Send File(s)");
					 }
				 }
			 }
			 
			 //Remove Files
			 for(String s : filesToRemove){
				 fileNames.remove(s);
				 removeFile(s);
			 }
			 
			 transferList.setListData(fileNames);
			 transferList.repaint();
				
			 transferLog.setListData(transLog);
			 transferLog.repaint();
			 
			 transfer = false;
			 host = false;
			 pickFile.setEnabled(true);
			 sendFile.setText("Send File(s)");
		 }
	 };
		
	 thread.start();
	 thread = null;
	}
	
	/**
	 * Init the file output stream.
	 * 
	 * @param fileName
	 */
	public void fileStart(String fileName, long fileLength){
		try {
			if(!transfer){
				total = 0;
				progressBar.setValue(0);
				
				transfer = true;
				cancelled = false;
				pickFile.setEnabled(false);
				sendFile.setText("Cancel");
				
				this.fileName = fileName;
				this.fileLength = fileLength;
				this.fileOut = new FileOutputStream(Constants.getDownloadFileLocation() + "\\" + fileName);
				
				transLog.add("Downloading " + fileName + " (" + Constants.getMod(fileLength) + ")");
				transferLog.setListData(transLog);
				transferLog.repaint();
				Constants.getPM(client.getUser_id()).log("Server","Downloading " + fileName + " (" + Constants.getMod(fileLength) + ")",null,null);
				statusLabel2.setText("Downloading " + fileName);
				
				startTime = System.currentTimeMillis();
			}
		}catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					   "Please read the below error message:\n\n" +
					   Constants.getStack(e),
					   "Transfer Error",
					   JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Write the file part.
	 * 
	 * @param filePart
	 */
	public void fileSend(String filePart){
		try {
			if(transfer){
				bytes = Constants.stringToBytes(filePart);
				total += bytes.length;

				fileOut.write(bytes);

				progressBar.setValue((int)(total*100/fileLength));
				long elapsedTime = System.currentTimeMillis() - startTime;
				statusLabel2.setText("Downloading: " + fileName + " - " + Constants.getMod(total) + " / " + Constants.getMod(fileLength) + " - " + Constants.getMod((long) (1000f * total / elapsedTime)) + "s");
			}else{
				fileCancel();
			}
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					   "Please read the below error message:\n\n" +
					   Constants.getStack(e),
					   "Transfer Error",
					   JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Close the output stream.
	 */
	public void fileEnd(){
		try {
			fileOut.close();
			endTime = (System.currentTimeMillis() - startTime) / 1000;
			
			transfer = false;
			pickFile.setEnabled(true);
			sendFile.setText("Send File(s)");
			
			statusLabel2.setText("Complete");
			progressBar.setValue(0);
			transLog.add("Downloaded " + fileName + " (" + Constants.getMod(fileLength) + ")" + " in " + endTime + " seconds");
			transferLog.setListData(transLog);
			transferLog.repaint();
			Constants.getPM(client.getUser_id()).log("Server","Downloaded <font color=\"blue\"><a href=\"file://" + Constants.getDownloadFileLocation() + "\\" + fileName +"\">" + fileName + " (" + Constants.getMod(fileLength) + ")</a></font>" + " in " + endTime + " seconds",null,null);
			
			if(Constants.isPlaySounds())
				Constants.getAudioPlayer().play(AudioPlayer.ALERT);
		}catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					   "Please read the below error message:\n\n" +
					   Constants.getStack(e),
					   "Transfer Error",
					   JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Cancel the file transfer
	 */
	public void fileCancel(){
		if(!cancelled){
			endTime = (System.currentTimeMillis() - startTime) / 1000;
			
			transfer = false;
			cancelled = true;
			
			try {
				if(fileOut != null)
					fileOut.close();
			}catch(IOException e) {
				JOptionPane.showMessageDialog(null,
						   "Please read the below error message:\n\n" +
						   Constants.getStack(e),
						   "Transfer Error",
						   JOptionPane.ERROR_MESSAGE);
			}
			
			total = 0;
			progressBar.setValue(0);
			
			pickFile.setEnabled(true);
			sendFile.setText("Send File(s)");
			
			if(!host){
				transLog.add("Cancelled " + fileName);
				transferLog.setListData(transLog);
				transferLog.repaint();
				Constants.getPM(client.getUser_id()).log("Server",fileName + " cancelled",null,null);
				statusLabel2.setText("Cancelled " + fileName);
			}
			
			JOptionPane.showMessageDialog(null,
					   "The file transfer of " + fileName + " has been cancelled.",
					   "Transfer Cancelled",
					   JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		transferContainer.setSize((getWidth() /2) -15, getHeight() -140);
		transferLogContainer.setBounds(transferContainer.getWidth() +10, 5, (getWidth() /2) -15, getHeight() -140);
		
		statusLabel.setBounds(5, transferLogContainer.getHeight() +10, 60, 20);
		statusLabel2.setBounds(50, transferLogContainer.getHeight() +10, (getWidth() -30), 20);
		progressBar.setBounds(5, transferLogContainer.getHeight() +35, (getWidth() -27), 25);
		
		pickFile.setBounds(5, transferLogContainer.getHeight() +70, 235, 25);
		sendFile.setBounds((getWidth() -255), transferLogContainer.getHeight() +70, 235, 25);
		
		repaint();
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}

}