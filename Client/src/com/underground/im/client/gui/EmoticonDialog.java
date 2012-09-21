package com.underground.im.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

/**
 * 
 * @author Troy
 *
 */
public class EmoticonDialog extends JDialog implements WindowFocusListener, ActionListener {
	private static final long serialVersionUID = -1165410927620467077L;
	
	private PersonalMessage pm;
	
	//Row 1
	private JButton regular_smile = new JButton();
	private JButton open_smile = new JButton();
	private JButton surprised_smile = new JButton();
	private JButton tongue_smile = new JButton();
	private JButton wink_smile = new JButton();
	private JButton sad_smile = new JButton();
	//Row 2
	private JButton confused_smile = new JButton();
	private JButton disappointed_smile = new JButton();
	private JButton crying_smile = new JButton();
	private JButton embarrassed_smile = new JButton();
	private JButton hot_smile = new JButton();
	private JButton angry_smile = new JButton();
	//Row 3
	private JButton angel_smile = new JButton();
	private JButton devil_smile = new JButton();
	private JButton zip_smile = new JButton();
	private JButton teeth_smile = new JButton();
	private JButton nerd_smile = new JButton();
	private JButton sarcastic_smile = new JButton();
	//Row 4
	private JButton secret_smile = new JButton();
	private JButton sick_smile = new JButton();
	private JButton know_smile = new JButton();
	private JButton think_smile = new JButton();
	private JButton party_smile = new JButton();
	private JButton eye_rolling_smile = new JButton();
	//Row 5
	private JButton sleepy_smile = new JButton();
	private JButton coffe_smile = new JButton();
	private JButton thumbs_up_smile = new JButton();
	private JButton thumbs_down_smile = new JButton();
	private JButton beer_smile = new JButton();
	private JButton martini_smile = new JButton();
	//Row 6
	private JButton gril_smile = new JButton();
	private JButton boy_smile = new JButton();
	private JButton left_smile = new JButton();
	private JButton right_smile = new JButton();
	private JButton bat_smile = new JButton();
	private JButton cake_smile = new JButton();
	//Row 7
	private JButton heart_smile = new JButton();
	private JButton broken_smile = new JButton();
	private JButton lips_smile = new JButton();
	private JButton gift_smile = new JButton();
	private JButton rose_smile = new JButton();
	private JButton wilted_smile = new JButton();
	
	//Row 1
	private ImageIcon regular_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/regular_smile.gif"));
	private ImageIcon open_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/teeth_smile.gif"));
	private ImageIcon surprised_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/omg_smile.gif"));
	private ImageIcon tongue_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/tongue_smile.gif"));
	private ImageIcon wink_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/wink_smile.gif"));
	private ImageIcon sad_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/sad_smile.gif"));
	//Row 2
	private ImageIcon confused_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/confused_smile.gif"));
	private ImageIcon disappointed_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/what_smile.gif"));
	private ImageIcon crying_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/cry_smile.gif"));
	private ImageIcon embarrassed_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/red_smile.gif"));
	private ImageIcon hot_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/shades_smile.gif"));
	private ImageIcon angry_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/angry_smile.gif"));
	//Row 3
	private ImageIcon angel_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/angel_smile.gif"));
	private ImageIcon devil_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/devil_smile.gif"));
	private ImageIcon zip_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/47_47.gif"));
	private ImageIcon teeth_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/48_48.gif"));
	private ImageIcon nerd_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/49_49.gif"));
	private ImageIcon sarcastic_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/50_50.gif"));
	//Row 4
	private ImageIcon secret_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/51_51.gif"));
	private ImageIcon sick_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/52_52.gif"));
	private ImageIcon know_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/71_71.gif"));
	private ImageIcon think_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/72_72.gif"));
	private ImageIcon party_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/74_74.gif"));
	private ImageIcon eye_rolling_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/75_75.gif"));
	//Row 5
	private ImageIcon sleepy_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/77_77.gif"));
	private ImageIcon coffee_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/coffee.gif"));
	private ImageIcon thumbs_up_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/thumbs_up.gif"));
	private ImageIcon thumbs_down_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/thumbs_down.gif"));
	private ImageIcon beer_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/beer_mug.gif"));
	private ImageIcon martini_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/martini.gif"));
	//Row 6
	private ImageIcon girl_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/girl.gif"));
	private ImageIcon boy_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/guy.gif"));
	private ImageIcon left_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/guy_hug.gif"));
	private ImageIcon right_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/girl_hug.gif"));
	private ImageIcon bat_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/bat.gif"));
	private ImageIcon cake_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/cake.gif"));
	//Row 7
	private ImageIcon heart_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/heart.gif"));
	private ImageIcon broken_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/broken_heart.gif"));
	private ImageIcon lips_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/kiss.gif"));
	private ImageIcon gift_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/present.gif"));
	private ImageIcon rose_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/rose.gif"));
	private ImageIcon wilted_smile_Icon = new ImageIcon(MenuBar.class.getResource("/smileys/wilted_rose.gif"));

	public EmoticonDialog(){
		this.setTitle("Emoticons");
		this.setSize(140, 163);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(null);
		this.setResizable(false);
		this.setUndecorated(true);
		this.addWindowFocusListener(this);
		
		//Row 1
		regular_smile.setBounds(5, 5, 20, 21);
		regular_smile.setIcon(regular_smile_Icon);
		regular_smile.setToolTipText(":)");
		regular_smile.addActionListener(this);
		open_smile.setBounds(27, 5, 20, 21);
		open_smile.setIcon(open_smile_Icon);
		open_smile.setToolTipText(":D");
		open_smile.addActionListener(this);
		surprised_smile.setBounds(49, 5, 20, 21);
		surprised_smile.setIcon(surprised_smile_Icon);
		surprised_smile.setToolTipText(":O");
		surprised_smile.addActionListener(this);
		tongue_smile.setBounds(71, 5, 20, 21);
		tongue_smile.setIcon(tongue_smile_Icon);
		tongue_smile.setToolTipText(":P");
		tongue_smile.addActionListener(this);
		wink_smile.setBounds(93, 5, 20, 21);
		wink_smile.setIcon(wink_smile_Icon);
		wink_smile.setToolTipText(";)");
		wink_smile.addActionListener(this);
		sad_smile.setBounds(115, 5, 20, 21);
		sad_smile.setIcon(sad_smile_Icon);
		sad_smile.setToolTipText(":(");
		sad_smile.addActionListener(this);
		
		//Row 2
		confused_smile.setBounds(5, 27, 20, 21);
		confused_smile.setIcon(confused_smile_Icon);
		confused_smile.setToolTipText(":S");
		confused_smile.addActionListener(this);
		disappointed_smile.setBounds(27, 27, 20, 21);
		disappointed_smile.setIcon(disappointed_smile_Icon);
		disappointed_smile.setToolTipText(":|");
		disappointed_smile.addActionListener(this);
		crying_smile.setBounds(49, 27, 20, 21);
		crying_smile.setIcon(crying_smile_Icon);
		crying_smile.setToolTipText(":'(");
		crying_smile.addActionListener(this);
		embarrassed_smile.setBounds(71, 27, 20, 21);
		embarrassed_smile.setIcon(embarrassed_smile_Icon);
		embarrassed_smile.setToolTipText(":$");
		embarrassed_smile.addActionListener(this);
		hot_smile.setBounds(93, 27, 20, 21);
		hot_smile.setIcon(hot_smile_Icon);
		hot_smile.setToolTipText("(H)");
		hot_smile.addActionListener(this);
		angry_smile.setBounds(115, 27, 20, 21);
		angry_smile.setIcon(angry_smile_Icon);
		angry_smile.setToolTipText(":@");
		angry_smile.addActionListener(this);
		
		//Row 3
		angel_smile.setBounds(5, 49, 20, 21);
		angel_smile.setIcon(angel_smile_Icon);
		angel_smile.setToolTipText("(A)");
		angel_smile.addActionListener(this);
		devil_smile.setBounds(27, 49, 20, 21);
		devil_smile.setIcon(devil_smile_Icon);
		devil_smile.setToolTipText("(6)");
		devil_smile.addActionListener(this);
		zip_smile.setBounds(49, 49, 20, 21);
		zip_smile.setIcon(zip_smile_Icon);
		zip_smile.setToolTipText(":#");
		zip_smile.addActionListener(this);
		teeth_smile.setBounds(71, 49, 20, 21);
		teeth_smile.setIcon(teeth_smile_Icon);
		teeth_smile.setToolTipText("8o|");
		teeth_smile.addActionListener(this);
		nerd_smile.setBounds(93, 49, 20, 21);
		nerd_smile.setIcon(nerd_smile_Icon);
		nerd_smile.setToolTipText("8-)");
		nerd_smile.addActionListener(this);
		sarcastic_smile.setBounds(115, 49, 20, 21);
		sarcastic_smile.setIcon(sarcastic_smile_Icon);
		sarcastic_smile.setToolTipText("^o)");
		sarcastic_smile.addActionListener(this);
		
		//Row 4
		secret_smile.setBounds(5, 71, 20, 21);
		secret_smile.setIcon(secret_smile_Icon);
		secret_smile.setToolTipText(":-*");
		secret_smile.addActionListener(this);
		sick_smile.setBounds(27, 71, 20, 21);
		sick_smile.setIcon(sick_smile_Icon);
		sick_smile.setToolTipText("+o(");
		sick_smile.addActionListener(this);
		know_smile.setBounds(49, 71, 20, 21);
		know_smile.setIcon(know_smile_Icon);
		know_smile.setToolTipText(":^)");
		know_smile.addActionListener(this);
		think_smile.setBounds(71, 71, 20, 21);
		think_smile.setIcon(think_smile_Icon);
		think_smile.setToolTipText("*-)");
		think_smile.addActionListener(this);
		party_smile.setBounds(93, 71, 20, 21);
		party_smile.setIcon(party_smile_Icon);
		party_smile.setToolTipText("<:o)");
		party_smile.addActionListener(this);
		eye_rolling_smile.setBounds(115, 71, 20, 21);
		eye_rolling_smile.setIcon(eye_rolling_smile_Icon);
		eye_rolling_smile.setToolTipText("8-|");
		eye_rolling_smile.addActionListener(this);
		
		//Row 5
		sleepy_smile.setBounds(5, 93, 20, 21);
		sleepy_smile.setIcon(sleepy_smile_Icon);
		sleepy_smile.setToolTipText("|-)");
		sleepy_smile.addActionListener(this);
		coffe_smile.setBounds(27, 93, 20, 21);
		coffe_smile.setIcon(coffee_smile_Icon);
		coffe_smile.setToolTipText("(C)");
		coffe_smile.addActionListener(this);
		thumbs_up_smile.setBounds(49, 93, 20, 21);
		thumbs_up_smile.setIcon(thumbs_up_smile_Icon);
		thumbs_up_smile.setToolTipText("(Y)");
		thumbs_up_smile.addActionListener(this);
		thumbs_down_smile.setBounds(71, 93, 20, 21);
		thumbs_down_smile.setIcon(thumbs_down_smile_Icon);
		thumbs_down_smile.setToolTipText("(N)");
		thumbs_down_smile.addActionListener(this);
		beer_smile.setBounds(93, 93, 20, 21);
		beer_smile.setIcon(beer_smile_Icon);
		beer_smile.setToolTipText("(B)");
		beer_smile.addActionListener(this);
		martini_smile.setBounds(115, 93, 20, 21);
		martini_smile.setIcon(martini_smile_Icon);
		martini_smile.setToolTipText("(D)");
		martini_smile.addActionListener(this);
		
		//Row 6
		gril_smile.setBounds(5, 115, 20, 21);
		gril_smile.setIcon(girl_smile_Icon);
		gril_smile.setToolTipText("(X)");
		gril_smile.addActionListener(this);
		boy_smile.setBounds(27, 115, 20, 21);
		boy_smile.setIcon(boy_smile_Icon);
		boy_smile.setToolTipText("(Z)");
		boy_smile.addActionListener(this);
		left_smile.setBounds(49, 115, 20, 21);
		left_smile.setIcon(left_smile_Icon);
		left_smile.setToolTipText("({)");
		left_smile.addActionListener(this);
		right_smile.setBounds(71, 115, 20, 21);
		right_smile.setIcon(right_smile_Icon);
		right_smile.setToolTipText("(})");
		right_smile.addActionListener(this);
		bat_smile.setBounds(93, 115, 20, 21);
		bat_smile.setIcon(bat_smile_Icon);
		bat_smile.setToolTipText(":[");
		bat_smile.addActionListener(this);
		cake_smile.setBounds(115, 115, 20, 21);
		cake_smile.setIcon(cake_smile_Icon);
		cake_smile.setToolTipText("(^)");
		cake_smile.addActionListener(this);
		
		//Row 6
		heart_smile.setBounds(5, 137, 20, 21);
		heart_smile.setIcon(heart_smile_Icon);
		heart_smile.setToolTipText("(L)");
		heart_smile.addActionListener(this);
		broken_smile.setBounds(27, 137, 20, 21);
		broken_smile.setIcon(broken_smile_Icon);
		broken_smile.setToolTipText("(U)");
		broken_smile.addActionListener(this);
		lips_smile.setBounds(49, 137, 20, 21);
		lips_smile.setIcon(lips_smile_Icon);
		lips_smile.setToolTipText("(K)");
		lips_smile.addActionListener(this);
		gift_smile.setBounds(71, 137, 20, 21);
		gift_smile.setIcon(gift_smile_Icon);
		gift_smile.setToolTipText("(G)");
		gift_smile.addActionListener(this);
		rose_smile.setBounds(93, 137, 20, 21);
		rose_smile.setIcon(rose_smile_Icon);
		rose_smile.setToolTipText("(F)");
		rose_smile.addActionListener(this);
		wilted_smile.setBounds(115, 137, 20, 21);
		wilted_smile.setIcon(wilted_smile_Icon);
		wilted_smile.setToolTipText("(W)");
		wilted_smile.addActionListener(this);
		
		this.add(regular_smile);
		this.add(open_smile);
		this.add(surprised_smile);
		this.add(tongue_smile);
		this.add(wink_smile);
		this.add(sad_smile);
		this.add(confused_smile);
		this.add(disappointed_smile);
		this.add(crying_smile);
		this.add(embarrassed_smile);
		this.add(hot_smile);
		this.add(angry_smile);
		this.add(angel_smile);
		this.add(devil_smile);
		this.add(zip_smile);
		this.add(teeth_smile);
		this.add(nerd_smile);
		this.add(sarcastic_smile);
		this.add(secret_smile);
		this.add(sick_smile);
		this.add(know_smile);
		this.add(think_smile);
		this.add(party_smile);
		this.add(eye_rolling_smile);
		this.add(sleepy_smile);
		this.add(coffe_smile);
		this.add(thumbs_up_smile);
		this.add(thumbs_down_smile);
		this.add(beer_smile);
		this.add(martini_smile);
		this.add(gril_smile);
		this.add(boy_smile);
		this.add(left_smile);
		this.add(right_smile);
		this.add(bat_smile);
		this.add(cake_smile);
		this.add(heart_smile);
		this.add(broken_smile);
		this.add(lips_smile);
		this.add(gift_smile);
		this.add(rose_smile);
		this.add(wilted_smile);
	}
	
	public void setTarget(PersonalMessage pm){
		this.pm = pm;
	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == regular_smile){
			pm.chatBox.setText(pm.chatBox.getText() + regular_smile.getToolTipText());
		}
		
		else if(e.getSource() == open_smile){
			pm.chatBox.setText(pm.chatBox.getText() + open_smile.getToolTipText());
		}
		
		else if(e.getSource() == surprised_smile){
			pm.chatBox.setText(pm.chatBox.getText() + surprised_smile.getToolTipText());
		}
		
		else if(e.getSource() == tongue_smile){
			pm.chatBox.setText(pm.chatBox.getText() + tongue_smile.getToolTipText());
		}
		
		else if(e.getSource() == wink_smile){
			pm.chatBox.setText(pm.chatBox.getText() + wink_smile.getToolTipText());
		}
		
		else if(e.getSource() == sad_smile){
			pm.chatBox.setText(pm.chatBox.getText() + sad_smile.getToolTipText());
		}
		
		else if(e.getSource() == confused_smile){
			pm.chatBox.setText(pm.chatBox.getText() + confused_smile.getToolTipText());
		}
		
		else if(e.getSource() == disappointed_smile){
			pm.chatBox.setText(pm.chatBox.getText() + disappointed_smile.getToolTipText());
		}
		
		else if(e.getSource() == crying_smile){
			pm.chatBox.setText(pm.chatBox.getText() + crying_smile.getToolTipText());
		}
		
		else if(e.getSource() == embarrassed_smile){
			pm.chatBox.setText(pm.chatBox.getText() + embarrassed_smile.getToolTipText());
		}
		
		else if(e.getSource() == hot_smile){
			pm.chatBox.setText(pm.chatBox.getText() + hot_smile.getToolTipText());
		}
		
		else if(e.getSource() == angry_smile){
			pm.chatBox.setText(pm.chatBox.getText() + angry_smile.getToolTipText());
		}
		
		else if(e.getSource() == devil_smile){
			pm.chatBox.setText(pm.chatBox.getText() + devil_smile.getToolTipText());
		}
		
		else if(e.getSource() == zip_smile){
			pm.chatBox.setText(pm.chatBox.getText() + zip_smile.getToolTipText());
		}
		
		else if(e.getSource() == teeth_smile){
			pm.chatBox.setText(pm.chatBox.getText() + teeth_smile.getToolTipText());
		}
		
		else if(e.getSource() == nerd_smile){
			pm.chatBox.setText(pm.chatBox.getText() + nerd_smile.getToolTipText());
		}
		
		else if(e.getSource() == sarcastic_smile){
			pm.chatBox.setText(pm.chatBox.getText() + sarcastic_smile.getToolTipText());
		}
		
		else if(e.getSource() == secret_smile){
			pm.chatBox.setText(pm.chatBox.getText() + secret_smile.getToolTipText());
		}
		
		else if(e.getSource() == sick_smile){
			pm.chatBox.setText(pm.chatBox.getText() + sick_smile.getToolTipText());
		}
		
		else if(e.getSource() == know_smile){
			pm.chatBox.setText(pm.chatBox.getText() + know_smile.getToolTipText());
		}
		
		else if(e.getSource() == think_smile){
			pm.chatBox.setText(pm.chatBox.getText() + think_smile.getToolTipText());
		}
		
		else if(e.getSource() == party_smile){
			pm.chatBox.setText(pm.chatBox.getText() + party_smile.getToolTipText());
		}
		
		else if(e.getSource() == eye_rolling_smile){
			pm.chatBox.setText(pm.chatBox.getText() + eye_rolling_smile.getToolTipText());
		}
		
		else if(e.getSource() == sleepy_smile){
			pm.chatBox.setText(pm.chatBox.getText() + sleepy_smile.getToolTipText());
		}
		
		else if(e.getSource() == coffe_smile){
			pm.chatBox.setText(pm.chatBox.getText() + coffe_smile.getToolTipText());
		}
		
		else if(e.getSource() == thumbs_up_smile){
			pm.chatBox.setText(pm.chatBox.getText() + thumbs_up_smile.getToolTipText());
		}
		
		else if(e.getSource() == thumbs_down_smile){
			pm.chatBox.setText(pm.chatBox.getText() + thumbs_down_smile.getToolTipText());
		}
		
		else if(e.getSource() == beer_smile){
			pm.chatBox.setText(pm.chatBox.getText() + beer_smile.getToolTipText());
		}
		
		else if(e.getSource() == martini_smile){
			pm.chatBox.setText(pm.chatBox.getText() + martini_smile.getToolTipText());
		}
		
		else if(e.getSource() == gril_smile){
			pm.chatBox.setText(pm.chatBox.getText() + gril_smile.getToolTipText());
		}
		
		else if(e.getSource() == boy_smile){
			pm.chatBox.setText(pm.chatBox.getText() + boy_smile.getToolTipText());
		}
		
		else if(e.getSource() == left_smile){
			pm.chatBox.setText(pm.chatBox.getText() + left_smile.getToolTipText());
		}
		
		else if(e.getSource() == right_smile){
			pm.chatBox.setText(pm.chatBox.getText() + right_smile.getToolTipText());
		}
		
		else if(e.getSource() == bat_smile){
			pm.chatBox.setText(pm.chatBox.getText() + bat_smile.getToolTipText());
		}
		
		else if(e.getSource() == cake_smile){
			pm.chatBox.setText(pm.chatBox.getText() + cake_smile.getToolTipText());
		}
		
		else if(e.getSource() == heart_smile){
			pm.chatBox.setText(pm.chatBox.getText() + heart_smile.getToolTipText());
		}
		
		else if(e.getSource() == broken_smile){
			pm.chatBox.setText(pm.chatBox.getText() + broken_smile.getToolTipText());
		}
		
		else if(e.getSource() == lips_smile){
			pm.chatBox.setText(pm.chatBox.getText() + lips_smile.getToolTipText());
		}
		
		else if(e.getSource() == gift_smile){
			pm.chatBox.setText(pm.chatBox.getText() + gift_smile.getToolTipText());
		}
		
		else if(e.getSource() == rose_smile){
			pm.chatBox.setText(pm.chatBox.getText() + rose_smile.getToolTipText());
		}
		
		else if(e.getSource() == wilted_smile){
			pm.chatBox.setText(pm.chatBox.getText() + wilted_smile.getToolTipText());
		}

		this.dispose();
	}
	
	public void windowGainedFocus(WindowEvent arg0) {}

}