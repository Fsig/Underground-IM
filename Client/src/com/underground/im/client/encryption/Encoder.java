package com.underground.im.client.encryption;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
 * @author Troy
 *
 */
public class Encoder {
	
	/**
	 * Encode a packet to Base64.
	 * 
	 * Removes new line arguments, so we can have longer packets :)
	 * 
	 * @param packet
	 * @return String
	 */
	public static String encode(String packet){
		return new BASE64Encoder().encode(packet.getBytes()).replaceAll("\r\n", "");
	}
	
	/**
	 * Decode a packet from Base64.
	 * 
	 * @param packet
	 * @return String
	 */
	public static String decode(String packet){
		try {
			return new String(new BASE64Decoder().decodeBuffer(packet));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}