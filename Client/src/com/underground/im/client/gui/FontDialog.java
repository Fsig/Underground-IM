package com.underground.im.client.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.underground.im.client.Constants;


/**
 * 
 * This class allow's you to select a font and font colour.
 * 
 * @author Troy
 *
 */
public class FontDialog extends JDialog implements ListSelectionListener, MouseListener, ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JLabel fontLabel = new JLabel("Font:");
	private JLabel fontStyleLabel = new JLabel("Font Style:");
	private JLabel fontSizeLabel = new JLabel("Font Size:");
	private JLabel fontColourLabel = new JLabel("Font Colour:");
	
	private JList<String> fontBox = new JList<String>();
	private JList<String> fontStyleBox = new JList<String>(new String[]{"Regular","Bold","Italic","Bold Italic"});
	private JList<String> fontSizeBox = new JList<String>(new String[]{"1","2","3","4","5","6","7"});
	
	private JScrollPane fontContainer;
	private JScrollPane fontStyleContainer;
	private JScrollPane fontSizeContainer;
	
	private JPanel previewPanel = new JPanel();
	public JPanel colourPanel = new JPanel();
	private TitledBorder previewBorder = new TitledBorder("Preview Font:");
	private TitledBorder colourBorder = new TitledBorder("");
	private String previewText = "This is how your font will look.";
	private JLabel previewLabel = new JLabel("This is how your font will look.");
	
	private JButton confirm = new JButton("Save");
	private JButton cancel = new JButton("Cancel");
	
	private Font beforeFont;
	private Color beforeColour;
	
    private String fontString = "<html><font face='Dialog' size='3' color='#333333'>";
    private String fontStringEnd = "</font></html>";
    public String lastFontFace = "Dialog";
    public int lastFontSize = 3;
    public int lastFontModifiers = 0;

	/**
	 * Construct a new Font GUI.
	 */
	public FontDialog(){
		this.setIconImage(Constants.fontIcon);
		this.setTitle("Font");
		this.setSize(358, 423);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		
		fontLabel.setBounds(10, 5, 80, 20);
		fontStyleLabel.setBounds(170, 5, 80, 20);
		fontSizeLabel.setBounds(280, 5, 80, 20);
		fontColourLabel.setBounds(10, 280, 80, 20);
		
		fontBox = new JList<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		fontBox.addListSelectionListener(this);
		fontContainer = new JScrollPane(fontBox,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fontContainer.setBounds(10, 25, 150, 150);
		
		fontStyleBox.addListSelectionListener(this);
		fontStyleContainer = new JScrollPane(fontStyleBox,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fontStyleContainer.setBounds(170, 25, 100, 150);
		
		fontSizeBox.addListSelectionListener(this);
		fontSizeContainer = new JScrollPane(fontSizeBox,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fontSizeContainer.setBounds(280, 25, 60, 150);
		
		previewPanel.setBounds(10, 180, 330, 100);
		previewPanel.setBorder(previewBorder);
		previewLabel.setFont(new Font("Dialog",0,12));
		previewLabel.setText(fontString + previewText + fontStringEnd);

		colourPanel.setBounds(10, 300, 330, 50);
		colourPanel.setBorder(colourBorder);
		colourPanel.setBackground(previewLabel.getForeground());
		colourPanel.setToolTipText("Click to select font colour.");
		colourPanel.addMouseListener(this);
		
		confirm.setBounds(240, 360, 100, 25);
		cancel.setBounds(10, 360, 100, 25);
		
		confirm.addActionListener(this);
		cancel.addActionListener(this);
		
		beforeFont = previewLabel.getFont();
		beforeColour = previewLabel.getForeground();
		
		this.add(fontLabel);
		this.add(fontStyleLabel);
		this.add(fontSizeLabel);
		this.add(fontContainer);
		this.add(fontStyleContainer);
		this.add(fontSizeContainer);
		this.add(previewPanel);
		previewPanel.add(previewLabel);
		this.add(fontColourLabel);
		this.add(colourPanel);
		this.add(confirm);
		this.add(cancel);
	}
	
	/**
	 * Listen for selection changes.
	 * 
	 * The cluster f#$k of a if statement below is to
	 * make sure we can select a font with only one of
	 * the box's being used.
	 * 
	 * At first I was going to make a option be selected
	 * by default, but I like this way better.
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(fontBox.getSelectedIndex() != -1 && fontStyleBox.getSelectedIndex() != -1 && fontSizeBox.getSelectedIndex() != -1){
			//Font, Style, Size
			lastFontFace = fontBox.getSelectedValue();
		    lastFontSize = Integer.parseInt(fontSizeBox.getSelectedValue());
		    lastFontModifiers = fontStyleBox.getSelectedIndex();
		    
		}else if(fontBox.getSelectedIndex() != -1 && fontStyleBox.getSelectedIndex() != -1 && fontSizeBox.getSelectedIndex() == -1){
			//Font, Style
			lastFontFace = fontBox.getSelectedValue();
		    lastFontModifiers = fontStyleBox.getSelectedIndex();
		    
		}else if(fontBox.getSelectedIndex() != -1 && fontStyleBox.getSelectedIndex() == -1 && fontSizeBox.getSelectedIndex() == -1){
			//Font
			lastFontFace = fontBox.getSelectedValue();
			
		}else if(fontBox.getSelectedIndex() != -1 && fontStyleBox.getSelectedIndex() == -1 && fontSizeBox.getSelectedIndex() != -1){
			//Font, Size
			lastFontFace = fontBox.getSelectedValue();
		    lastFontSize = Integer.parseInt(fontSizeBox.getSelectedValue());
		    
		}else if(fontBox.getSelectedIndex() == -1 && fontStyleBox.getSelectedIndex() != -1 && fontSizeBox.getSelectedIndex() != -1){
			//Style, Size
		    lastFontSize = Integer.parseInt(fontSizeBox.getSelectedValue());
		    lastFontModifiers = fontStyleBox.getSelectedIndex(); 
		    
		}else if(fontBox.getSelectedIndex() == -1 && fontStyleBox.getSelectedIndex() != -1 && fontSizeBox.getSelectedIndex() == -1){
			//Style
			lastFontModifiers = fontStyleBox.getSelectedIndex();
			
		}else if(fontBox.getSelectedIndex() == -1 && fontStyleBox.getSelectedIndex() == -1 && fontSizeBox.getSelectedIndex() != -1){
			//Size
		    lastFontSize = Integer.parseInt(fontSizeBox.getSelectedValue());
		    
		}
		
		updatePreview();
	}
	
	/**
	 * Update the preview label.
	 */
	public void updatePreview(){
		fontString = "<html>";
		fontStringEnd = "</font>";
		
		if(lastFontModifiers == 1){
			fontString += "<b>";
			fontStringEnd += "</b>";
		}
		
		if(lastFontModifiers == 2){
			fontString += "<i>";
			fontStringEnd += "</i>";
		}
		
		if(lastFontModifiers == 3){
			fontString += "<b><i>";
			fontStringEnd += "</i></b>";
		}
		
		fontString += "<font ";
		fontString += "face='" + lastFontFace + "' ";
		fontString += "size='" + lastFontSize + "' ";
		fontString += "color='" + toHexString(colourPanel.getBackground())  + "'>";
		
		fontStringEnd += "</html>";
		
		previewLabel.setText(fontString + previewText + fontStringEnd);
	}
	
	/**
	 * Get color code.
	 * 
	 * @param c
	 * @return String
	 */
	public static String toHexString(Color c) {
		StringBuilder sb = new StringBuilder('#');

		if(c.getRed() < 16) 
			sb.append('0');
		  
		sb.append(Integer.toHexString(c.getRed()));

		if(c.getGreen() < 16) 
			sb.append('0');
		  
		sb.append(Integer.toHexString(c.getGreen()));

		if(c.getBlue() < 16) 
			sb.append('0');
		  
		sb.append(Integer.toHexString(c.getBlue()));

		return sb.toString();
	}
	
	/**
	 * This will return the selected font.
	 * 
	 * @return Font
	 */
	public String getFontString(){
		return this.fontString.replace("<html>", "");
	}
	
	/**
	 * This will return the font's end string.
	 * 
	 * @return Color
	 */
	public String getFontStringEnd(){
		return this.fontStringEnd.replace("</html>", "");
	}
	
	public void setFontString(String fontString){
		this.fontString = fontString;
	}
	
	public void setFontStringEnd(String fontStringEnd){
		this.fontStringEnd = fontStringEnd;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == colourPanel){
			colourPanel.setBackground(JColorChooser.showDialog(this, "Colour Picker", previewLabel.getForeground()));
			updatePreview();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() == colourPanel){
			colourPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
	}
	
	/**
	 * Eh, was having problems with using HTML
	 * in the chat box, so kept this way and added
	 * a switch statement for font sizes.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == confirm){
			for(PersonalMessage pm : Constants.getPmWindows()){
				pm.chatBox.setForeground(colourPanel.getBackground());
				pm.chatBox.setFont(new Font(lastFontFace,lastFontModifiers,Constants.convertSize(lastFontSize)));
			}
			
			Constants.getPreferencesGUI().save();
			this.dispose();
		}else if(e.getSource() == cancel){
			previewLabel.setForeground(beforeColour);
			colourPanel.setBackground(beforeColour);
			previewLabel.setFont(beforeFont);
			this.dispose();
		}
	}
	
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentResized(ComponentEvent arg0) {}

}