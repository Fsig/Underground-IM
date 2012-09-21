package com.underground.im.client;

/**
 * 
 * @author Troy
 *
 */
public class Client {
	private int user_id;
	private String username;
	private String screen_name;
	private int status;
	private boolean online;
	
	public Client(int user_id, String username, String screen_name, int status, boolean online){
		this.user_id = user_id;
		this.username = username;
		this.screen_name = screen_name;
		this.status = status;
		this.online = online;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

}