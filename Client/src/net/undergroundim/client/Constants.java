package net.undergroundim.client;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.ImageIcon;

import net.undergroundim.client.gui.Account;
import net.undergroundim.client.gui.AddFriendDialog;
import net.undergroundim.client.gui.EmoticonDialog;
import net.undergroundim.client.gui.FontDialog;
import net.undergroundim.client.gui.FriendList;
import net.undergroundim.client.gui.Login;
import net.undergroundim.client.gui.PersonalMessage;
import net.undergroundim.client.gui.PreferenceDialog;
import net.undergroundim.client.gui.ProfileEdit;
import net.undergroundim.client.gui.ProfileView;
import net.undergroundim.client.networking.PacketManager;


/**
 * 
 * @author Troy
 *
 */
public class Constants {
	private static double version = 1.011;
	private static String buildDate = "09/Oct/2012";
	
	private static File userHome = new File(System.getProperty("user.home") + "\\Underground IM");
	private static File prefFile = new File(System.getProperty("user.home") + "\\Underground IM\\preferences.ini");
	
	private static boolean fontEnabled;
	private static boolean saveLogFiles;
	private static boolean trimChatLog;
	private static boolean playSounds;
	private static boolean showFileTransfer;
	private static boolean timeFormat;
	private static boolean playNudges;
	private static String logFileName = "";
	private static String logFileLocation = "";
	private static String downloadFileLocation = "";
	private static int lineCount = 1000;
	private static String lastServer = "";
	private static String lastUsername ="";
	
	public static Image icon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/earth-icon.png"));
	public static Image iconTray = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/earth-icon16.png"));
	public static Image settingsIcon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/gnome_preferences_system.png"));
	public static Image fontIcon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/font-x-generic-icon.png"));
	public static Image mailIcon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/e_mail32.png"));
	public static Image profileIconMain = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/user_silhouette.png"));
	public static Image accountIcon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/vcard.png"));
	public static Image fileTransferIcon = Toolkit.getDefaultToolkit().getImage(Constants.class.getResource("/icons/transfer_document32.png"));
	
	public static ImageIcon profileIcon = new ImageIcon(Constants.class.getResource("/icons/user_silhouette.png"));
	public static ImageIcon onlineIcon = new ImageIcon(Constants.class.getResource("/icons/buddy_online.png"));
	public static ImageIcon awayIcon = new ImageIcon(Constants.class.getResource("/icons/buddy_away.png"));
	public static ImageIcon dndIcon = new ImageIcon(Constants.class.getResource("/icons/buddy_dnd.png"));
	public static ImageIcon offlineIcon = new ImageIcon(Constants.class.getResource("/icons/buddy_offline.png"));
	public static ImageIcon fontIcon2 = new ImageIcon(PersonalMessage.class.getResource("/icons/font.png"));
	public static ImageIcon emoticonIcon = new ImageIcon(PersonalMessage.class.getResource("/icons/regular_smile.png"));
	public static ImageIcon nudgeIcon = new ImageIcon(PersonalMessage.class.getResource("/icons/nudge.png"));
	public static ImageIcon addIcon = new ImageIcon(PersonalMessage.class.getResource("/icons/buddy_add.png"));
	public static ImageIcon transferIcon = new ImageIcon(PersonalMessage.class.getResource("/icons/Transfer.png"));
	
	private static Client user;
	private static Profile userProfile;
	
	private static PacketManager packetManager = new PacketManager();
	private static Login loginGUI = new Login();
	private static FriendList friendList;
	private static FontDialog fontDialog = new FontDialog();
	private static PreferenceDialog preferencesGUI = new PreferenceDialog();
	private static ProfileView profileView = new ProfileView();
	private static ProfileEdit profileEdit = new ProfileEdit();
	private static Account account = new Account();
	private static EmoticonDialog emoticonDialog = new EmoticonDialog();
	private static AddFriendDialog addFriendDialog = new AddFriendDialog();
	private static AudioPlayer audioPlayer = new AudioPlayer();
	
	private static ArrayList<Client> friends = new ArrayList<Client>();
	private static ArrayList<Emoticon> emotions = new ArrayList<Emoticon>();
	private static ArrayList<PersonalMessage> pmWindows = new ArrayList<PersonalMessage>();

	private static String PMString = "<font face='Dialog' size='3' color='#FF6CFF'>";
	private static String PMEndString = "</font>";
	private static String lastPMUser;
	
	public static Date date;
	private static SimpleDateFormat hour24 = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat hour12 = new SimpleDateFormat("hh:mm:ss a");
	
	/**
	 * Set up the Emoticons.
	 * 
	 * TODO In the future maybe add in the
	 * ability to add custom Emoticons.
	 * 
	 */
	public static void setEmotions(){
		emotions.add(new Emoticon(":\\)","<img src=\"" + Constants.class.getResource("/smileys/regular_smile.gif") + "\">"));
		emotions.add(new Emoticon(":D","<img src=\"" + Constants.class.getResource("/smileys/teeth_smile.gif") + "\">"));
		emotions.add(new Emoticon(":O","<img src=\"" + Constants.class.getResource("/smileys/omg_smile.gif") + "\">"));
		emotions.add(new Emoticon(":P","<img src=\"" + Constants.class.getResource("/smileys/tongue_smile.gif") + "\">"));
		emotions.add(new Emoticon(";\\)","<img src=\"" + Constants.class.getResource("/smileys/wink_smile.gif") + "\">"));
		emotions.add(new Emoticon(":\\(","<img src=\"" + Constants.class.getResource("/smileys/sad_smile.gif") + "\">"));
		emotions.add(new Emoticon(":S","<img src=\"" + Constants.class.getResource("/smileys/confused_smile.gif") + "\">"));
		emotions.add(new Emoticon(":\\|","<img src=\"" + Constants.class.getResource("/smileys/what_smile.gif") + "\">"));
		emotions.add(new Emoticon(":'\\(","<img src=\"" + Constants.class.getResource("/smileys/cry_smile.gif") + "\">"));
		emotions.add(new Emoticon(":\\$","<img src=\"" + Constants.class.getResource("/smileys/red_smile.gif") + "\">"));
		emotions.add(new Emoticon("\\(h\\)","<img src=\"" + Constants.class.getResource("/smileys/shades_smile.gif") + "\">"));
		emotions.add(new Emoticon(":@","<img src=\"" + Constants.class.getResource("/smileys/angry_smile.gif") + "\">"));
		emotions.add(new Emoticon("\\(a\\)","<img src=\"" + Constants.class.getResource("/smileys/angel_smile.gif") + "\">"));
		emotions.add(new Emoticon("\\(6\\)","<img src=\"" + Constants.class.getResource("/smileys/devil_smile.gif") + "\">"));
		emotions.add(new Emoticon(":\\#","<img src=\"" + Constants.class.getResource("/smileys/47_47.gif") + "\">"));
		emotions.add(new Emoticon("8o\\|","<img src=\"" + Constants.class.getResource("/smileys/48_48.gif") + "\">"));
		emotions.add(new Emoticon("8\\-\\)","<img src=\"" + Constants.class.getResource("/smileys/49_49.gif") + "\">"));
		emotions.add(new Emoticon("\\^o\\)","<img src=\"" + Constants.class.getResource("/smileys/50_50.gif") + "\">"));
		emotions.add(new Emoticon("\\:\\-\\*","<img src=\"" + Constants.class.getResource("/smileys/51_51.gif") + "\">"));
		emotions.add(new Emoticon("\\+o\\(","<img src=\"" + Constants.class.getResource("/smileys/52_52.gif") + "\">"));
		emotions.add(new Emoticon(":\\^\\)","<img src=\"" + Constants.class.getResource("/smileys/75_75.gif") + "\">"));
		emotions.add(new Emoticon("\\*-\\)","<img src=\"" + Constants.class.getResource("/smileys/71_71.gif") + "\">"));
		emotions.add(new Emoticon("\\<:o\\)","<img src=\"" + Constants.class.getResource("/smileys/74_74.gif") + "\">"));
		emotions.add(new Emoticon("\\|-\\)","<img src=\"" + Constants.class.getResource("/smileys/77_77.gif") + "\">"));
		emotions.add(new Emoticon("\\(c\\)|\\(C\\)","<img src=\"" + Constants.class.getResource("/smileys/coffee.gif") + "\">"));
		emotions.add(new Emoticon("\\(y\\)|\\(Y\\)","<img src=\"" + Constants.class.getResource("/smileys/thumbs_up.gif") + "\">"));
		emotions.add(new Emoticon("\\(n\\)|\\(N\\)","<img src=\"" + Constants.class.getResource("/smileys/thumbs_down.gif") + "\">"));
		emotions.add(new Emoticon("\\(b\\)|\\(B\\)","<img src=\"" + Constants.class.getResource("/smileys/beer_mug.gif") + "\">"));
		emotions.add(new Emoticon("\\(d\\)|\\(D\\)","<img src=\"" + Constants.class.getResource("/smileys/martini.gif") + "\">"));
		emotions.add(new Emoticon("\\(x\\)|\\(X\\)","<img src=\"" + Constants.class.getResource("/smileys/girl.gif") + "\">"));
		emotions.add(new Emoticon("\\(z\\)|\\(Z\\)","<img src=\"" + Constants.class.getResource("/smileys/guy.gif") + "\">"));
		emotions.add(new Emoticon("\\(\\{\\)","<img src=\"" + Constants.class.getResource("/smileys/guy_hug.gif") + "\">"));
		emotions.add(new Emoticon("\\(\\}\\)","<img src=\"" + Constants.class.getResource("/smileys/girl_hug.gif") + "\">"));
		emotions.add(new Emoticon(":\\[","<img src=\"" + Constants.class.getResource("/smileys/bat.gif") + "\">"));
		emotions.add(new Emoticon(":\\[","<img src=\"" + Constants.class.getResource("/smileys/bat.gif") + "\">"));
		emotions.add(new Emoticon("\\(\\^\\)","<img src=\"" + Constants.class.getResource("/smileys/cake.gif") + "\">"));
		emotions.add(new Emoticon("\\(l\\)|\\(L\\)","<img src=\"" + Constants.class.getResource("/smileys/heart.gif") + "\">"));
		emotions.add(new Emoticon("\\(u\\)|\\(U\\)","<img src=\"" + Constants.class.getResource("/smileys/broken_heart.gif") + "\">"));
		emotions.add(new Emoticon("\\(k\\)|\\(K\\)","<img src=\"" + Constants.class.getResource("/smileys/kiss.gif") + "\">"));
		emotions.add(new Emoticon("\\(g\\)|\\(G\\)","<img src=\"" + Constants.class.getResource("/smileys/present.gif") + "\">"));
		emotions.add(new Emoticon("\\(f\\)|\\(F\\)","<img src=\"" + Constants.class.getResource("/smileys/rose.gif") + "\">"));
		emotions.add(new Emoticon("\\(w\\)|\\(W\\)","<img src=\"" + Constants.class.getResource("/smileys/wilted_rose.gif") + "\">"));
		emotions.add(new Emoticon("\\(p\\)|\\(P\\)","<img src=\"" + Constants.class.getResource("/smileys/camera.gif") + "\">"));
		emotions.add(new Emoticon("\\(~\\)","<img src=\"" + Constants.class.getResource("/smileys/film.gif") + "\">"));
		emotions.add(new Emoticon("\\(@\\)","<img src=\"" + Constants.class.getResource("/smileys/cat.gif") + "\">"));
		emotions.add(new Emoticon("\\(&\\)","<img src=\"" + Constants.class.getResource("/smileys/dog.gif") + "\">"));
		emotions.add(new Emoticon("\\(t\\)|\\(T\\)","<img src=\"" + Constants.class.getResource("/smileys/phone.gif") + "\">"));
		emotions.add(new Emoticon("\\(i\\)|\\(I\\)","<img src=\"" + Constants.class.getResource("/smileys/lightbulb.gif") + "\">"));
		emotions.add(new Emoticon("\\(8\\)","<img src=\"" + Constants.class.getResource("/smileys/note.gif") + "\">"));
		emotions.add(new Emoticon("\\(s\\)|\\(S\\)","<img src=\"" + Constants.class.getResource("/smileys/moon.gif") + "\">"));
		emotions.add(new Emoticon("\\(\\*\\)","<img src=\"" + Constants.class.getResource("/smileys/star.gif") + "\">"));	
		emotions.add(new Emoticon("\\(e\\)|\\(E\\)","<img src=\"" + Constants.class.getResource("/smileys/envelope.gif") + "\">"));
		emotions.add(new Emoticon("\\(o\\)|\\(O\\)","<img src=\"" + Constants.class.getResource("/smileys/clock.gif") + "\">"));
		emotions.add(new Emoticon("\\(sn\\)","<img src=\"" + Constants.class.getResource("/smileys/53_53.gif") + "\">"));
		emotions.add(new Emoticon("\\(bah\\)","<img src=\"" + Constants.class.getResource("/smileys/70_70.gif") + "\">"));
		emotions.add(new Emoticon("\\(pl\\)","<img src=\"" + Constants.class.getResource("/smileys/55_55.gif") + "\">"));
		emotions.add(new Emoticon("\\(\\|\\|\\)","<img src=\"" + Constants.class.getResource("/smileys/56_56.gif") + "\">"));
		emotions.add(new Emoticon("\\(pi\\)","<img src=\"" + Constants.class.getResource("/smileys/57_57.gif") + "\">"));
		emotions.add(new Emoticon("\\(so\\)","<img src=\"" + Constants.class.getResource("/smileys/58_58.gif") + "\">"));
		emotions.add(new Emoticon("\\(au\\)","<img src=\"" + Constants.class.getResource("/smileys/59_59.gif") + "\">"));
		emotions.add(new Emoticon("\\(ap\\)","<img src=\"" + Constants.class.getResource("/smileys/60_60.gif") + "\">"));
		emotions.add(new Emoticon("\\(um\\)","<img src=\"" + Constants.class.getResource("/smileys/61_61.gif") + "\">"));
		emotions.add(new Emoticon("\\(ip\\)","<img src=\"" + Constants.class.getResource("/smileys/62_62.gif") + "\">"));
		emotions.add(new Emoticon("\\(co\\)","<img src=\"" + Constants.class.getResource("/smileys/63_63.gif") + "\">"));
		emotions.add(new Emoticon("\\(mp\\)","<img src=\"" + Constants.class.getResource("/smileys/64_64.gif") + "\">"));	
		emotions.add(new Emoticon("\\(st\\)","<img src=\"" + Constants.class.getResource("/smileys/66_66.gif") + "\">"));
		emotions.add(new Emoticon("\\(li\\)","<img src=\"" + Constants.class.getResource("/smileys/73_73.gif") + "\">"));
		emotions.add(new Emoticon("\\(mo\\)","<img src=\"" + Constants.class.getResource("/smileys/69_69.gif") + "\">"));
	}
	
	/**
	 * Get the current date/time.
	 * 
	 * @return String
	 */
	public static String getDate(){
		if(timeFormat)
			return hour24.format(date = new Date());
		else
			return hour12.format(date = new Date());
	}
	
	/**
	 * This will convert the bytes to a string.
	 * 
	 * @param b
	 * @return String
	 */
	public static String bytesToString(byte[] b) {
	    byte[] b2 = new byte[b.length + 1];
	    b2[0] = 1;
	    System.arraycopy(b, 0, b2, 1, b.length);
	    return new BigInteger(b2).toString(36);
	}
	
    /**
     * Convert the string back to bytes.
     * 
     * @param s
     * @return byte[]
     */
	public static byte[] stringToBytes(String s) {
	    byte[] b2 = new BigInteger(s, 36).toByteArray();
	    return Arrays.copyOfRange(b2, 1, b2.length);
	}
	
	/**
	 * Convert the password to English.
	 * Also will need to make this hash
	 * the password later...
	 * 
	 * @param cPassword
	 * @return String
	 */
    public static String convertPassword(char[] cPassword){
    	String strRet = new String("");
    	for(int i = 0; i < cPassword.length; i++){
    		strRet += cPassword[i];
    	}
    	return strRet;
    }
	
	/**
	 * Hash the password...
	 * 
	 * @param password
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 */
	public static String getHash(String password){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.reset();
			byte[] input = digest.digest(password.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < input.length; i++) {
				sb.append(Integer.toString((input[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param l
	 * @return String
	 */
	public static String getMod(long l){
		DecimalFormat dec = new DecimalFormat("#.##");
		String result = "";
		
		if(l > 1024 && l < 1048576){ //KB
			result += dec.format((double)l / 1024) + " KB";
		}else if(l > 1048576 && l < 1073741824){ //MB
			result += dec.format((double)l / 1048576) + " MB";
		}else if(l > 1073741824){ //GB
			result += dec.format((double)l / 1073741824) + " GB";
		}else{
			result += dec.format((double) l) + " Bytes";
		}
		
		return result;
	}
	
	/**
	 * Get the stack trace as a string.
	 * 
	 * @param throwable
	 * @return String
	 */
	public static String getStack(Exception e) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return writer.toString();
	}
	
	/**
	 * Convert the font size for text areas.
	 * 
	 * @param size
	 * @return Integer
	 */
	public static int convertSize(int size){
		int newFontSize = 12;
		
		switch(size){
		case 1:
			newFontSize = 8;
			break;
		case 2:
			newFontSize = 10;
			break;
		case 3:
			newFontSize = 12;
			break;
		case 4:
			newFontSize = 16;
			break;
		case 5:
			newFontSize = 18;
			break;
		case 6:
			newFontSize = 26;
			break;
		case 7:
			newFontSize = 38;
			break;
		}
		
		return newFontSize;
	}
	
	public static double getVersion() {
		return version;
	}

	public static String getBuildDate() {
		return buildDate;
	}

	public static boolean isFontEnabled() {
		return fontEnabled;
	}

	public static void setFontEnabled(boolean fontEnabled) {
		Constants.fontEnabled = fontEnabled;
	}

	public static boolean isSaveLogFiles() {
		return saveLogFiles;
	}

	public static void setSaveLogFiles(boolean saveLogFiles) {
		Constants.saveLogFiles = saveLogFiles;
	}
	
	public static Client getUser() {
		return user;
	}

	public static void setUser(Client user) {
		Constants.user = user;
	}

	public static File getUserHome() {
		return userHome;
	}

	public static void setUserHome(File userHome) {
		Constants.userHome = userHome;
	}

	public static File getPrefFile() {
		return prefFile;
	}

	public static Profile getUserProfile() {
		return userProfile;
	}

	public static void setUserProfile(Profile userProfile) {
		Constants.userProfile = userProfile;
	}

	public static PacketManager getPacketManager() {
		return packetManager;
	}

	public static Login getLoginGUI() {
		return loginGUI;
	}

	public static void setLoginGUI(Login loginGUI) {
		Constants.loginGUI = loginGUI;
	}

	public static FriendList getFriendList() {
		return friendList;
	}
	
	public static void setFriendList(FriendList friendList) {
		Constants.friendList = friendList;
	}

	public static FontDialog getFontDialog() {
		return fontDialog;
	}

	public static PreferenceDialog getPreferencesGUI() {
		return preferencesGUI;
	}
	
	public static ProfileView getProfileView() {
		return profileView;
	}

	public static ProfileEdit getProfileEdit() {
		return profileEdit;
	}

	public static Account getAccount() {
		return account;
	}

	public static EmoticonDialog getEmoticonDialog() {
		return emoticonDialog;
	}

	public static AddFriendDialog getAddFriendDialog() {
		return addFriendDialog;
	}

	public static ArrayList<Client> getFriends() {
		return friends;
	}
	
	public static Client getFriend(int user_id){
		for(Client c : friends)
			if(c.getUser_id() == user_id)
				return c;
		
		return null;
	}
	
	public static Client getFriend(String username){
		for(Client c : friends)
			if(c.getUsername() == username)
				return c;
		
		return null;
	}

	public static void setFriends(ArrayList<Client> friends) {
		Constants.friends = friends;
	}
	
	public static void addFriend(Client friend){
		Constants.friends.add(friend);
	}
	
	public static void removeFriend(int user_id){
		Constants.friends.remove(getFriend(user_id));
	}

	public static String getLogFileName() {
		return logFileName;
	}

	public static void setLogFileName(String logFileName) {
		Constants.logFileName = logFileName;
	}

	public static String getLogFileLocation() {
		return logFileLocation;
	}

	public static void setLogFileLocation(String logFileLocation) {
		Constants.logFileLocation = logFileLocation;
	}

	public static String getDownloadFileLocation() {
		return downloadFileLocation;
	}

	public static void setDownloadFileLocation(String downloadFileLocation) {
		Constants.downloadFileLocation = downloadFileLocation;
	}

	public static boolean isTrimChatLog() {
		return trimChatLog;
	}

	public static void setTrimChatLog(boolean trimChatLog) {
		Constants.trimChatLog = trimChatLog;
	}

	public static boolean isPlaySounds() {
		return playSounds;
	}

	public static void setPlaySounds(boolean playSounds) {
		Constants.playSounds = playSounds;
	}

	public static boolean isShowFileTransfer() {
		return showFileTransfer;
	}

	public static void setShowFileTransfer(boolean showFileTransfer) {
		Constants.showFileTransfer = showFileTransfer;
	}

	public static boolean isTimeFormat() {
		return timeFormat;
	}

	public static void setTimeFormat(boolean timeFormat) {
		Constants.timeFormat = timeFormat;
	}

	public static boolean isPlayNudges() {
		return playNudges;
	}

	public static void setPlayNudges(boolean playNudges) {
		Constants.playNudges = playNudges;
	}

	public static int getLineCount() {
		return lineCount;
	}

	public static void setLineCount(int lineCount) {
		Constants.lineCount = lineCount;
	}

	public static String getLastServer() {
		return lastServer;
	}

	public static void setLastServer(String lastServer) {
		Constants.lastServer = lastServer;
	}

	public static String getLastUsername() {
		return lastUsername;
	}

	public static void setLastUsername(String lastUsername) {
		Constants.lastUsername = lastUsername;
	}

	public static AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public static ArrayList<Emoticon> getEmotions() {
		return emotions;
	}

	public static ArrayList<PersonalMessage> getPmWindows() {
		return pmWindows;
	}

	public static void setPmWindows(ArrayList<PersonalMessage> pmWindows) {
		Constants.pmWindows = pmWindows;
	}
	
	public static void removePMWindow(PersonalMessage pm){
		Constants.pmWindows.remove(pm);
	}
	
	public static void addPmWindow(PersonalMessage pm, boolean group){
		Constants.pmWindows.add(pm);
		
		if(group){
			pm.groupChat = true;
			pm.setGroupChat();
			pm.updateClientList();
		}
	}
	
	public static PersonalMessage getPM(int user_id){
		for(PersonalMessage pm : pmWindows)
			if(!pm.groupChat)
				if(pm.clients.get(0).getUser_id() == user_id)
					return pm;
		
		return null;
	}
	
	public static PersonalMessage getPM(String username){
		for(PersonalMessage pm : pmWindows)
			if(!pm.groupChat)
				if(pm.clients.get(0).getUsername().equals(username))
					return pm;
		
		return null;
	}
	
	public static PersonalMessage getPMGroup(int user_id){
		for(PersonalMessage pm : pmWindows)
			if(pm.groupChat)
				for(Client client : pm.clients)
					if(client.getUser_id() == user_id)
						return pm;
		
		return null;
	}
	
	public static PersonalMessage getPMGroup(String username){
		for(PersonalMessage pm : pmWindows)
			if(pm.groupChat)
				for(Client client : pm.clients)
					if(client.getUsername().equals(username))
						return pm;
		
		return null;
	}

	public static String getPMString() {
		return PMString;
	}

	public static void setPMString(String pMString) {
		PMString = pMString;
	}

	public static String getPMEndString() {
		return PMEndString;
	}

	public static void setPMEndString(String pMEndString) {
		PMEndString = pMEndString;
	}

	public static void setEmotions(ArrayList<Emoticon> emotions) {
		Constants.emotions = emotions;
	}

	public static String getLastPMUser() {
		return lastPMUser;
	}

	public static void setLastPMUser(String lastPMUser) {
		Constants.lastPMUser = lastPMUser;
	}
	
	/**
	 * Trim bytes
	 * 
	 * @param bytes
	 * @return byte[]
	 */
	public static final byte[] trim( byte[] bytes ){
		if(isEmpty(bytes))
			return new byte[0];

		int start = trimLeft( bytes, 0 );
		int end = trimRight( bytes, bytes.length - 1 );

		int length = end - start + 1;

		if(length != 0){
			byte[] newBytes = new byte[end - start + 1];
	        System.arraycopy( bytes, start, newBytes, 0, length );
	        return newBytes;
	    }else{
	        return new byte[0];
	    }
	}
	  
	public static final int trimLeft(byte[] bytes, int pos){
		if(bytes == null)
			return pos;

		while ((pos < bytes.length) && (bytes[pos] == 0))
			pos++;
			
		return pos;
	}
	 
	public static final int trimRight(byte[] bytes, int pos){
		if(bytes == null)
			return pos;


		while((pos >= 0) && (bytes[pos] == 0))
			pos--;

		return pos;
	}
	  
	public static final boolean isEmpty(byte[] bytes){
		return bytes == null || bytes.length == 0;
	}
	
}
