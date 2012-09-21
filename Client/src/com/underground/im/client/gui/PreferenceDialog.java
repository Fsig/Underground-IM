package com.underground.im.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.underground.im.client.Constants;
import com.underground.im.client.FileWriter;

/**
 * 
 * @author Troy
 *
 */
public class PreferenceDialog extends JDialog implements ChangeListener, ActionListener, MouseListener{
	private static final long serialVersionUID = -1817090973423261099L;
	
	private JCheckBox fontCheckBox = new JCheckBox("View Custom Fonts");
	private JCheckBox logCheckBox = new JCheckBox("Save Log Files");
	private JCheckBox trimCheckBox = new JCheckBox("Trim Chat Log");
	private JCheckBox soundCheckBox = new JCheckBox("Play Sounds");
	private JCheckBox showFileBox = new JCheckBox("Show File Transfer");
	private JCheckBox timeFormatBox = new JCheckBox("24 Hour Time");
	private JCheckBox playNudgeBox = new JCheckBox("Play Nudges");
	
	private JLabel showLabel = new JLabel("After ");
	private JLabel showLabel2 = new JLabel(" lines.");
	
	private JTextField lineField = new JTextField("1000");
	
	private JLabel logLabel = new JLabel("Log location:");
	private JLabel downloadLabel = new JLabel("Download location:");
	
	private JTextField logField = new JTextField();
	private JTextField downloadField = new JTextField();
	
	private JButton saveButton = new JButton("Save");
	private JButton cancelButton = new JButton("Cancel");
	
	private ArrayList<String> settings = new ArrayList<String>();
	
	private JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * Construct a new preferences GUI.
	 */
	public PreferenceDialog(){
		checkSetup();
		this.setIconImage(Constants.settingsIcon);
		this.setTitle("Preferences");
		this.setSize(353, 227);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		fontCheckBox.setBounds(10, 10, 140, 20);
		logCheckBox.setBounds(10, 35, 120, 20);
		showFileBox.setBounds(10, 60, 140, 20);
		timeFormatBox.setBounds(10, 85, 120, 20);
		
		trimCheckBox.setBounds(200, 10, 120, 20);
		showLabel.setBounds(200, 35, 40, 20);
		lineField.setBounds(240, 35, 40, 20);
		showLabel2.setBounds(285, 35, 40, 20);
		
		trimCheckBox.addChangeListener(this);
		
		soundCheckBox.setBounds(200, 60, 120, 20);
		playNudgeBox.setBounds(200, 85, 120, 20);
		
		showLabel.setEnabled(false);
		lineField.setEnabled(false);
		showLabel2.setEnabled(false);
		
		logLabel.setBounds(10, 110, 100, 20);
		downloadLabel.setBounds(10, 135, 120, 20);
		
		logField.setBounds(125, 110, 210, 20);
		downloadField.setBounds(125, 135, 210, 20);
		
		logField.addMouseListener(this);
		downloadField.addMouseListener(this);
		
		saveButton.setBounds(235, 165, 100, 25);
		cancelButton.setBounds(10, 165, 100, 25);
		
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		//File chooser
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Select Location");

		this.add(fontCheckBox);
		this.add(logCheckBox);
		this.add(showFileBox);
		this.add(timeFormatBox);
		this.add(trimCheckBox);
		this.add(soundCheckBox);
		this.add(playNudgeBox);
		this.add(showLabel);
		this.add(lineField);
		this.add(showLabel2);
		this.add(logLabel);
		this.add(downloadLabel);
		this.add(logField);
		this.add(downloadField);
		this.add(saveButton);
		this.add(cancelButton);
		
		readSettings();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource() == trimCheckBox){
			if(trimCheckBox.isSelected()){
				showLabel.setEnabled(true);
				lineField.setEnabled(true);
				showLabel2.setEnabled(true);
			}else{
				showLabel.setEnabled(false);
				lineField.setEnabled(false);
				showLabel2.setEnabled(false);
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == saveButton){
			save();
		}else if(e.getSource() == cancelButton){
			this.dispose();
		}
	}
	
	/**
	 * Save the settings.
	 */
	public void save(){
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Font Enabled;		" + String.valueOf(fontCheckBox.isSelected()), false, false);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Save Log Files;		" + String.valueOf(logCheckBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim Log;		" + String.valueOf(trimCheckBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim After;		" + lineField.getText(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Sounds;		" + String.valueOf(soundCheckBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Nudges;		" + String.valueOf(playNudgeBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Show File Transfer;	" + String.valueOf(showFileBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "24 Hour Time;		" + String.valueOf(timeFormatBox.isSelected()), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Log Location;		" + logField.getText(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Download Location;	" + downloadField.getText(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Server;		" + Constants.getLoginGUI().serverField.getText(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Username;		" + Constants.getLoginGUI().usernameField.getText(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Start;	" + Constants.getFontDialog().getFontString(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font End;		" + Constants.getFontDialog().getFontStringEnd(), true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Face;		" + Constants.getFontDialog().lastFontFace, true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Size;		" + Constants.getFontDialog().lastFontSize, true, true);
		FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Modifiers;	" + Constants.getFontDialog().lastFontModifiers, true, true);
		update();
		this.dispose();
	}
	
	/**
	 * Update constants
	 */
	public void update(){
		Constants.setFontEnabled(fontCheckBox.isSelected());
		Constants.setSaveLogFiles(logCheckBox.isSelected());
		Constants.setTrimChatLog(trimCheckBox.isSelected());
		Constants.setLineCount(Integer.parseInt(lineField.getText()));
		Constants.setPlaySounds(soundCheckBox.isSelected());
		Constants.setPlayNudges(playNudgeBox.isSelected());
		Constants.setShowFileTransfer(showFileBox.isSelected());
		Constants.setTimeFormat(timeFormatBox.isSelected());
		Constants.setLineCount(Integer.parseInt(lineField.getText()));
		Constants.setLogFileLocation(logField.getText());
		Constants.setDownloadFileLocation(downloadField.getText());
	}
	
	/**
	 * Read in the settings.
	 */
	public void readSettings(){
		try{
			BufferedReader in = new BufferedReader(new FileReader(Constants.getPrefFile().getAbsolutePath()));
			
			 String str;
			 while((str = in.readLine()) != null) {
				 settings.add(str);
			 }
			 
			 in.close();
			 in = null;
			 
			 fontCheckBox.setSelected(Boolean.valueOf(settings.get(0).split(";")[1].replaceAll("\t", "")));
			 logCheckBox.setSelected(Boolean.valueOf(settings.get(1).split(";")[1].replaceAll("\t", "")));
			 trimCheckBox.setSelected(Boolean.valueOf(settings.get(2).split(";")[1].replaceAll("\t", "")));
			 lineField.setText(settings.get(3).split(";")[1].replaceAll("\t", ""));
			 soundCheckBox.setSelected(Boolean.valueOf(settings.get(4).split(";")[1].replaceAll("\t", "")));
			 playNudgeBox.setSelected(Boolean.valueOf(settings.get(5).split(";")[1].replaceAll("\t", "")));
			 showFileBox.setSelected(Boolean.valueOf(settings.get(6).split(";")[1].replaceAll("\t", "")));
			 timeFormatBox.setSelected(Boolean.valueOf(settings.get(7).split(";")[1].replaceAll("\t", "")));
			 
			 logField.setText(settings.get(8).split(";")[1].replaceAll("\t", ""));
			 downloadField.setText(settings.get(9).split(";")[1].replaceAll("\t", ""));
			 
			 if(trimCheckBox.isSelected()){
				 showLabel.setEnabled(true);
				 lineField.setEnabled(true);
				 showLabel2.setEnabled(true);
			 }else{
				 showLabel.setEnabled(false);
				 lineField.setEnabled(false);
				 showLabel2.setEnabled(false);
			 }
			 
			 update();
			 Constants.setLastServer(settings.get(10).split(";")[1].replaceAll("\t", ""));
			 Constants.setLastUsername(settings.get(11).split(";")[1].replaceAll("\t", ""));
			 
			 if(!settings.get(12).split(";")[1].replaceAll("\t", "").isEmpty())
				 Constants.getFontDialog().setFontString(settings.get(12).split(";")[1].replaceAll("\t", ""));
			 
			 if(!settings.get(13).split(";")[1].replaceAll("\t", "").isEmpty())
				 Constants.getFontDialog().setFontStringEnd(settings.get(13).split(";")[1].replaceAll("\t", ""));
			 
			 Constants.getFontDialog().lastFontFace = settings.get(14).split(";")[1].replaceAll("\t", "");
			 Constants.getFontDialog().lastFontSize = Integer.parseInt(settings.get(15).split(";")[1].replaceAll("\t", ""));
			 Constants.getFontDialog().lastFontModifiers = Integer.parseInt(settings.get(16).split(";")[1].replaceAll("\t", "")); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This will check the setup and fix
	 * any error's it finds.
	 */
	public static void checkSetup(){
		String[] dirs = new String[]{"Underground IM", "Underground IM\\Logs", "Underground IM\\Downloads"};
		
		try {
			for(String s : dirs){
				File f = new File(System.getProperty("user.home") + "\\" + s);
				if(!f.exists())
					f.mkdir();
			}
			
			if(!Constants.getPrefFile().exists()){
				Constants.getPrefFile().createNewFile();
	
			
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Font Enabled;		true", false, false);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Save Log Files;		false", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim Log;		true", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Trim After;		1000", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Sounds;		true", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Play Nudges;		true", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Show File Transfer;	true" , true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "24 Hour Time;		false" , true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Log Location;		" + Constants.getUserHome() + "\\Logs", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Download Location;	" + Constants.getUserHome() + "\\Downloads", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Server;		", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Username;		", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Start;	", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font End;		", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Face;		Dialog", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Size;		3", true, true);
				FileWriter.writeToFile(Constants.getPrefFile().getAbsolutePath(), "Last Font Modifiers;	0", true, true);
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == logField){
			int option = fileChooser.showOpenDialog(this);
			
			if(option == JFileChooser.APPROVE_OPTION) {
				logField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
		
		else if(e.getSource() == downloadField){
			int option = fileChooser.showOpenDialog(this);
			
			if(option == JFileChooser.APPROVE_OPTION) {
				downloadField.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		}
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

}