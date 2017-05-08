package com.jivebrowser.controllers;

/***
 *  File: HistoryController.java
 *  Authors: Anisha Mascarenhas and Arjun Rao
 *  
 *  Description:
 *  History Controller handles the browser's history
 *  Each user in the OS will have a separate log of browsing history
 *  History is stored in the user's home folder in a file called ".jivehistory"
 *  It is implemented as a Singleton class
 *  
 *  Package: com.jivebrowser.controllers
 *  JiveBrowser v1.0 
 * */


import com.jivebrowser.utils.*;
import java.io.IOException;
import java.util.*;

public class HistoryController {

	// history is stored in a hashmap with URL and timestamp of last access. 
	private HashMap<String, Date> history;
	
	// Constructor is private as HistoryController is a singleton class that is common throughout the lifecycle of the browser.
	private HistoryController() {

		try {
			history = Helpers.readHistory();
		} catch (IOException e) {
			System.out.println("HistoryException: "+e.getMessage());
			//e.printStackTrace();
		}finally {
			if(history == null )
				history = new HashMap<>();
		}
	}

	private static class HistoryCreator {
		private static final HistoryController INSTANCE = new HistoryController();
	}

	// Use this method to get a singleton object
	public static HistoryController getInstance() {
		return HistoryCreator.INSTANCE;
	}
	
	// This method is used to add an URL to history and write it to the file.
	public void addUrl(String url, Date timestamp){
		history.put(url, timestamp);
		
		try {
			Helpers.writeHistory(history);
		} catch (IOException e) {
			System.out.println("HistoryException: "+e.getMessage());
			//e.printStackTrace();
		}
	}
	
	public HashMap<String, Date> getHistory() {
		return history;
	}
	
	// Used to return all history data from the hashmap in a string format sorted by timestamp. 
	public String printHistory(){
		return Helpers.printMap(Helpers.sortByDate(history, Helpers.DESC));
	}
}
