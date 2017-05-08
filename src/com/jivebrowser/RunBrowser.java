package com.jivebrowser;

/***
 *  File: RunBrowser.java
 *  Authors: Anisha Mascarenhas and Arjun Rao
 *  
 *  Description:
 *  RunBrowser is the driving class of the Browser.
 *  Implements the main method and creates the first browser window.
 *  
 *  Package: com.jivebrowser
 *  JiveBrowser v1.0 
 * */


import com.jivebrowser.components.*;

public class RunBrowser {
	
	public static void main(String[] args) {
     	BrowserWindow mainFrame = new BrowserWindow("Jive Browser");    	
    	mainFrame.initialize();
    }
}
