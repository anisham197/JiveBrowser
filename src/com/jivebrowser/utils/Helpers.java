package com.jivebrowser.utils;

/***
*  File: Helpers.java
*  Authors: Anisha Mascarenhas and Arjun Rao
*  
*  Description:
*  Defines several helper methods used in various other components of the browser. 
*  
*  Package: com.jivebrowser.utils
*  JiveBrowser v1.0 
* */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.ParseException;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

public class Helpers
{
    public static boolean ASC = true;
    public static boolean DESC = false;
    private static Gson gson = new Gson();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final String ABOUT_INFORMATION = "Jive Browser v1.0\nA Java Internet Viewing Experience\nDeveloped by Arjun Rao and Anisha Mascarenhas";
    
  // Uncomment to Debug
    
//    public static void main(String[] args) throws ParseException
//    {
    
    	 
//
//        // Creating dummy unsorted map
////        HashMap<String, Date> unsortMap = new HashMap<String, Date>();        
////        unsortMap.put("www.facebook.com",sdf.parse("2018-1-23 17:22:00"));
////        unsortMap.put("www.google.com",sdf.parse("2017-1-23 17:22:00"));	
////        unsortMap.put("www.yahoo.com",sdf.parse("2015-1-23 17:22:00"));
////        System.out.println("Before Writing......");
////        printMap(unsortMap);
//        
//
//        
////
////
////        System.out.println("First Read......");
////        HashMap<String, Date> sortedMapDesc;
////		try {
////			sortedMapDesc = readHistory();
////			printMap(sortedMapDesc);
////			sortedMapDesc.put("www.yahoo22.com",sdf.parse("2015-1-23 17:22:00"));
////			System.out.println("Writing to file");
////	        writeHistory(sortedMapDesc);								
////		} catch (IOException e) {
//
////			e.printStackTrace();
////		} 
////        
//
//    }

    
    // Used by HistoryController to sort history data by timestamps.
    public static Map<String, Date> sortByDate(Map<String, Date> unsortMap, final boolean order)
    {

        List<Entry<String, Date>> list = new LinkedList<Entry<String, Date>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Date>>()
        {
            public int compare(Entry<String, Date> o1,
                    Entry<String, Date> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Date> sortedMap = new LinkedHashMap<String, Date>();
        for (Entry<String, Date> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    
    
    // Used to read history data from file and set up HashMap
    public static HashMap<String,Date> readHistory() throws IOException
    {
    	HashMap<String,Date> history;
    	//reads HashMap from file and deserializes before returning
    	String configFile = System.getProperty("user.home")+"/.jivehistory";
    	BufferedReader br = new BufferedReader(new FileReader(configFile));
        try {            
            String jsonString = br.readLine();
            Type historyCollection = new TypeToken<HashMap<String,Date>>(){}.getType();
            history = gson.fromJson(jsonString, historyCollection);
        } finally {
            br.close();
        }
    	 
    	return history;
    }
    
    // Used to write history data from hashmap to file.
    public static void writeHistory(HashMap<String,Date> map) throws IOException
    {
    	//Writes map to file by serializing 
    	String json = gson.toJson(map);
    	String home = System.getProperty("user.home");
    	File configFile = new File(home + "/.jivehistory");
    	
    	PrintWriter pw = new PrintWriter(new FileWriter(configFile));
    	try {	
    		pw.write(json);    		
    	} finally {
    		pw.close();
    	}
    	
    }
    
    // Used to set title of a tab and add ellipses ("...") in case of long titles
    public static String trimTitle(String title)
    {
    	String trimmedTitle = title;
    	if(title.length() > 10)
    	{
    		trimmedTitle = title.substring(0, 8) + "...";
    	}
    	return trimmedTitle;
    }
    
    
    // Used to get history data as a html string.
    public static String printMap(Map<String, Date> map)
    {
    	String html="<!DOCTYPE html><html><head><title>History</title></head><body><h1>History</h1><table><tr><td>URL</td><td>Time</td></tr>";
        for (Entry<String, Date> entry : map.entrySet())
        {
            html+="<tr><td><a href='" + entry.getKey()+"'>"+ entry.getKey() + "</a></td><td>"+ sdf.format(entry.getValue())+"</td></tr>";
        }
        html+="</table></body></html>";
        return html;
    }
}
