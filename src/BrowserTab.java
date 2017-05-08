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

	
	// These are the buttons for iterating through the page list.
    private JButton backButton, forwardButton;
    private Browser browser = new Browser();
    // Page location text field.
    private JTextField locationTextField;
    private final JTabbedPane pane; 
    private final int tabIndex; 
    
    private static final String GOOGLE_SEARCH = "https://www.google.com/search?q=";
    // 
    
     
    // Constructor for Web Browser.
    public BrowserTab(final JTabbedPane pane,final int tabIndex) {
    	
    	this.setLayout(new BorderLayout());    	
        
    	BrowserView mBrowserView = new BrowserView(browser);        
        mBrowserView.setVisible(true);               
        createButtonPanel();
        add(mBrowserView,BorderLayout.CENTER);
        this.pane = pane;
        this.tabIndex = tabIndex;
        
        browser.addTitleListener(new TitleListener(){

			@Override
			public void onTitleChange(TitleEvent event) {
				pane.setTitleAt(tabIndex,event.getTitle());				
			}
    		
    	});

    }
    
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
        JButton goButton = new JButton("GO");
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
    	
    	// TODO: enable search
    	browser.loadURL(locationTextField.getText());
    	
    	browser.addLoadListener(new LoadAdapter() {
    		
    		 @Override
    		    public void onFinishLoadingFrame(FinishLoadingEvent event) {
    		        if (event.isMainFrame()) {
    		        	pane.setTitleAt(tabIndex,browser.getTitle());
    		        	HistoryController.getInstance().addUrl(browser.getURL(), new Date());
    		        }
    		        
    		    }
    		 
    	    @Override
    	    public void onFailLoadingFrame(FailLoadingEvent event) {
    	        NetError errorCode = event.getErrorCode();
    	        if (errorCode == NetError.NAME_NOT_RESOLVED) {
    	        	browser.loadURL(GOOGLE_SEARCH + locationTextField.getText());
    	        	HistoryController.getInstance().addUrl(browser.getURL(), new Date());
    	        }
    	    }
      	});
    	
    }
     
    
    
}