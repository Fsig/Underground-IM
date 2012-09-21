package com.underground.im.client;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * 
 * @author Troy
 *
 */
public class Clipboard implements ClipboardOwner{
	private static Clipboard instance = new Clipboard();
	
	public static void setClipboardContents(String clip){
		StringSelection stringSelection = new StringSelection(clip);
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, (ClipboardOwner) instance);
	}

	  /**
	  * Get the String residing on the clipboard.
	  *
	  * @return any text found on the Clipboard; if none found, return an
	  * empty String.
	  */
	public static String getClipboardContents() {
		String result = "";
	    java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
	    
	    if(hasTransferableText){
	    	try {
	    		result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }catch (UnsupportedFlavorException ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }catch (IOException ex) {
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    
	    return result;
	}

	@Override
	public void lostOwnership(java.awt.datatransfer.Clipboard arg0,Transferable arg1) {
		//Do nothing
	}
	
}