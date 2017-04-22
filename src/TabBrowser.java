import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.DefaultLoadHandler;
import com.teamdev.jxbrowser.chromium.LoadParams;
import com.teamdev.jxbrowser.chromium.events.TitleEvent;
import com.teamdev.jxbrowser.chromium.events.TitleListener;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
 

public class TabBrowser extends JPanel{

	
	// These are the buttons for iterating through the page list.
    private JButton backButton, forwardButton;
    private Browser browser = new Browser();
    // Page location text field.
    private JTextField locationTextField;
    private final JTabbedPane pane; 
    // 
    
     
    // Constructor for Web Browser.
    public TabBrowser(final JTabbedPane pane) {
    	this.setLayout(new BorderLayout());    	
        BrowserView mBrowserView = new BrowserView(browser);        
        mBrowserView.setVisible(true);               
        createButtonPanel();
        add(mBrowserView,BorderLayout.CENTER);
        this.pane = pane;
        browser.addTitleListener(new TitleListener(){

			@Override
			public void onTitleChange(TitleEvent event) {
				pane.setTitleAt(pane.getSelectedIndex(),event.getTitle());				
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
    	
    	browser.loadURL(locationTextField.getText());
    	// TODO add history
    	HistoryController.getInstance().addUrl(browser.getURL(), new Date());
    	
    }
     
    
    
}