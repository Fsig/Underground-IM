package com.ossietronics.undergroundim.server.encryption;

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
	
	public static void main(String[] args){
		String s = "Test test test one two three Test test test one two three Test test test one two three Test test test one two three Test test test one two three";
		
		System.out.println(encode(s).replace("\r\n", ""));
		System.out.println(decode(encode(s).replace("\r\n", "")));
	}

}
