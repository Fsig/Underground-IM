package net.undergroundim.client;

/**
 * 
 * @author Troy
 *
 */
public class Emoticon {
	private String key;
	private String value;
	
	/**
	 * Construct a new Emoticon.
	 * 
	 * @param key
	 * @param value
	 */
	public Emoticon(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}