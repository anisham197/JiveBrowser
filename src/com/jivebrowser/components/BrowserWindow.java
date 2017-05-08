package com.jivebrowser.components;


/***
 *  File: BrowserWindow.java
 *  Authors: Anisha Mascarenhas and Arjun Rao
 *  
 *  Description:
 *  BrowserWindow is the class that handles functionality of one entire window of the browser
 *  One BrowserWindow contains a JTabbedPane for handling several tabs that can make up that window
 *  
 *  Package: com.jivebrowser.components
 *  JiveBrowser v1.0 
 * */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.jivebrowser.utils.*;
import com.jivebrowser.controllers.*;

public class BrowserWindow extends JFrame {

    private final JTabbedPane pane = new JTabbedPane();
    public static int tabCount = 0;
    
    // Sets up a BrowserWindow with a given window title. Used in RunBrowser to start first browser window. 
    public BrowserWindow(String title) {
    	super(title);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    	
    	initMenuBar();
    	addTab(0);
    }
    
    // initialize () is called in RunBrowser after creating a BrowserWindow. 
    // Sets up Swing parameters to display the browser window.
    public void initialize() {
        pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        
    	getContentPane().add(pane, BorderLayout.CENTER);
        setVisible(true);
    }
    
    
    // Used to add a new tab to the window 
    // parameter i is a unique tab identifier in sequential order.
    private void addTab(int i){
    	String title = "New Tab";
        pane.add(title, new BrowserTab(pane,i));
        initTabComponent(i);
        pane.setSelectedIndex(i);
    }
    
    // Used to add a new tab and open the browser's history (Refer HistoryController.java)
    private void addHistoryTab(int i){
    	
    	String html = HistoryController.getInstance().printHistory();
    	String title = "History";
    	BrowserTab historyTab = new BrowserTab(pane,i);
    	historyTab.setHTML(html);
        pane.add(title,historyTab);
        initTabComponent(i);
        pane.setSelectedIndex(i);
    }
    
    // Initializes a tab wit close button and title being set using TabButtonComponent Class
    // Refer to Swing JTabbedPane examples on official JavaDocs page.
    private void initTabComponent(int i) {
        pane.setTabComponentAt(i,
                 new TabButtonComponent(pane));
    }    
 
    // Initializes Menu Bar with File, View and Help Menus and sub menu items.
    public void initMenuBar() {
    	
    	// Initializes the main menu bar
    	JMenuBar menuBar = new JMenuBar();
    	
    	//Code for File Menu Starts Here
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem addTabMenuItem = new JMenuItem("New Tab");
        
        addTabMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK));
        
        addTabMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               	tabCount++;
            	addTab(tabCount);
            }
        });
        
        JMenuItem closeTabMenuItem = new JMenuItem("Close Tab");
        closeTabMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
        closeTabMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int i = pane.getSelectedIndex();
                if (i != -1) {
                    pane.remove(i);
                    tabCount--;
                }
                if(pane.getTabCount() == 0){
                	System.exit(0);
                }
            }
        });
        
        JMenuItem closeWindowMenuItem = new JMenuItem("Close Window");
        closeWindowMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	System.exit(0);
            }
        });
        
        fileMenu.add(addTabMenuItem);
        fileMenu.add(closeTabMenuItem);
        fileMenu.add(closeWindowMenuItem);
        fileMenu.setMnemonic(KeyEvent.VK_F);    	
        
        //Code for File Menu Ends Here                
    	//Code for View Menu Starts Here
        
        JMenu viewMenu = new JMenu("View");
                
        JMenuItem historyMenuItem = new JMenuItem("History");
        historyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_MASK));
        
        historyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            	            
            	tabCount++;
            	addHistoryTab(tabCount);
            }
        });
       
        viewMenu.add(historyMenuItem);
        viewMenu.setMnemonic(KeyEvent.VK_V);
    	
        // Code for View Menu Ends Here
    	// Code for Help Menu Starts Here
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutMenuItem = new JMenuItem("About JiveBrowser");
        aboutMenuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(pane, Helpers.ABOUT_INFORMATION,"About Jive",JOptionPane.INFORMATION_MESSAGE);
				
			}
        	
        });
        helpMenu.add(aboutMenuItem);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        
    	// Code for Help Menu Ends Here
        
        
        // Add all sub menus to menubar and menubar to Frame
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }  

}
