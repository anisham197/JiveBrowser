package com.jivebrowser.components;


/***
 *  File: BrowserTab.java
 *  Authors: Anisha Mascarenhas and Arjun Rao
 *  
 *  Description:
 *  BrowserTab is the class that handles functionality of one tab of the browser
 *  One browser tab contains its own URL bar (Called locationTextField) and navigation buttons
 *  It uses a reference to the parent JTabbedPane (obtainted via the constructor) and 
 *  a unique Tab identifier (tabIndex).
 *  
 *  Package: com.jivebrowser.components
 *  JiveBrowser v1.0 
 * */



import com.jivebrowser.utils.*;
import com.jivebrowser.controllers.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;
import com.teamdev.jxbrowser.chromium.events.FailLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;
import com.teamdev.jxbrowser.chromium.events.NetError;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;



public class BrowserTab extends JPanel{

	
	// These are the buttons for iterating through the navigation-history list.
    private JButton backButton, forwardButton,goButton;
    // Browser is a java wrapper around Chromium provided by JxBrowser.
    private Browser browser = new Browser();
    // TextField to store URL
    private JTextField locationTextField;
   
    // Store Parent references
    private final JTabbedPane pane; 
    private final int tabIndex; 
    
    //Uses URL below to perform google search on pages that result in 404 errors.
    private static final String GOOGLE_SEARCH = "https://www.google.com/search?q=";
     
    
     
    // Constructor for a Browser Tab.
    public BrowserTab(final JTabbedPane pane,final int tabIndex) {
    	
    	this.setLayout(new BorderLayout());    	
        
    	BrowserView mBrowserView = new BrowserView(browser);  
    	//BrowserView is the main swing component that renders web pages using WebKit internally
        mBrowserView.setVisible(true);               
        
        createButtonPanel();
        
        add(mBrowserView,BorderLayout.CENTER);
        
        this.pane = pane;
        this.tabIndex = tabIndex;
        
        //Title listener handles changing of Tab titles dynamically. 
        browser.addTitleListener(new TitleListener(){

			@Override
			public void onTitleChange(TitleEvent event) {
				pane.setTitleAt(tabIndex,Helpers.trimTitle(event.getTitle()));	
				pane.setToolTipTextAt(tabIndex, event.getTitle());
			}
    		
    	});

    }
    
    //Swing Code to generate the URL bar and navigation buttons
    private void createButtonPanel()
    {
    	JPanel buttonPanel = new JPanel();
        backButton = new JButton("< Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.goBack();
            }
        });
        backButton.setEnabled(false);
        buttonPanel.add(backButton);
        forwardButton = new JButton("Forward >");
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                browser.goForward();
            }
        });
        forwardButton.setEnabled(false);
        buttonPanel.add(forwardButton);
        locationTextField = new JTextField(35);
        locationTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    actionGo();
                }
            }
        });
        buttonPanel.add(locationTextField);
        goButton = new JButton("GO");
        goButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionGo();
            }
        });
        buttonPanel.add(goButton);
        add(buttonPanel, BorderLayout.NORTH);
    }
    
    //Set HTML of the browser to received value
    public void setHTML(String html)
    {
    	browser.loadHTML(html);
    }
    
    // Load and show the page specified in the location text field.
    private void actionGo() {    	
    	browser.setLoadHandler(new DefaultLoadHandler() {
            public boolean onLoad(LoadParams params) {

            	//enable or disable NavigationHistory
            	if(browser.canGoBack()){
            		backButton.setEnabled(true);
            	}
            	else
            	{
            		backButton.setEnabled(false);
            	}
            	
            	if(browser.canGoForward())
            	{
            		forwardButton.setEnabled(true);
            	}
            	else
            	{
            		forwardButton.setEnabled(false);
            	}
            	            	
                // Return false to continue loading
                return false;
            }          
        });
    	
    	//load URL entered at the textField or perform a google search if it fails to load. 
    	browser.loadURL(locationTextField.getText());
    	
    	browser.addLoadListener(new LoadAdapter() {
    		
    		 @Override
    		    public void onFinishLoadingFrame(FinishLoadingEvent event) {
    			 //Handle successful load and update History and tab title
    		        if (event.isMainFrame()) {
    		        	pane.setTitleAt(tabIndex,Helpers.trimTitle(browser.getTitle()));
    		        	pane.setToolTipTextAt(tabIndex, browser.getTitle());
    		        	locationTextField.setText(browser.getURL());
    		        	HistoryController.getInstance().addUrl(browser.getURL(), new Date());
    		        	goButton.transferFocus();
    		        }
    		        
    		    }
    		 
    	    @Override
    	    public void onFailLoadingFrame(FailLoadingEvent event) {
    	        //Handle failed loads by performing Google Search instead
    	    	NetError errorCode = event.getErrorCode();
    	        if (errorCode == NetError.NAME_NOT_RESOLVED) {
    	        	browser.loadURL(GOOGLE_SEARCH + locationTextField.getText());
    	        	HistoryController.getInstance().addUrl(browser.getURL(), new Date());
    	        }
    	    }
      	});
    	
    }
     
    
    
}