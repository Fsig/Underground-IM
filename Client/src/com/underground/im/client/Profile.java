package com.underground.im.client;

/**
 * 
 * @author Troy
 *
 */
public class Profile {
	private String username;
	private String screen_name;
	private String email;
	private byte age;
	private byte sex;
	private String location;
	private String about;
	private String made_date;
	
	/**
	 * Construct a new profile.
	 * 
	 * @param username
	 * @param screen_name
	 * @param age
	 * @param sex
	 * @param location
	 * @param about
	 * @param made_date
	 */
	public Profile(String username, String screen_name, String email, byte age, byte sex, String location, String about, String made_date){
		this.username = username;
		this.screen_name = screen_name;
		this.email = email;
		this.age = age;
		this.sex = sex;
		this.location = location;
		this.about = about;
		this.made_date = made_date;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte getAge() {
		return age;
	}

	public void setAge(byte age) {
		this.age = age;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getMade_date() {
		return made_date;
	}

	public void setMade_date(String made_date) {
		this.made_date = made_date;
	}

}
