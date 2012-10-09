package net.undergroundim.client;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;

/**
 * 
 * @author Troy
 *
 */
public class AudioPlayer {
	public static URL MESSAGE;
	public static URL ONLINE;
	public static URL NUDGE;
	public static URL ALERT;
	public static URL FILE;
	public boolean complete = true;
	
	public AudioPlayer(){
		MESSAGE = AudioPlayer.class.getResource("/sounds/type.wav");
		ONLINE = AudioPlayer.class.getResource("/sounds/online.wav");
		NUDGE = AudioPlayer.class.getResource("/sounds/nudge.wav");
		ALERT = AudioPlayer.class.getResource("/sounds/newalert.wav");
		FILE = AudioPlayer.class.getResource("/sounds/vimdone.wav");
	}
	
	public void play(URL t){
		playClip(t);
	}
	
	private void playClip(URL clipFile) {
		complete = false;
		
		try{
			class AudioListener implements LineListener {
				private boolean done = false;
				
				@Override 
				public synchronized void update(LineEvent event) {
					Type eventType = event.getType();
					if (eventType == Type.STOP || eventType == Type.CLOSE) {
						done = true;
						notifyAll();
					}
				}
				
				public synchronized void waitUntilDone() throws InterruptedException {
					while(!done) {
						wait();
					}
				}
			}
				
			AudioListener listener = new AudioListener();
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipFile);
	
			Clip clip = AudioSystem.getClip();
			clip.addLineListener(listener);
			clip.open(audioInputStream);
			clip.start();
			listener.waitUntilDone();
			clip.close();
			audioInputStream.close();
			complete = true;
		}catch(Exception e){
			e.printStackTrace();
			complete = true;
		}
	}

}