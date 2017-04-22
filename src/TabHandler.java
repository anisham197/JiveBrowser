import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TabHandler extends JFrame {

    private final JTabbedPane pane = new JTabbedPane();
    private JMenuItem tabComponentsItem;
    private JMenuItem scrollLayoutItem;
    public static int tabCount = 0;
    
    public static void main(String[] args) {
     	TabHandler mainFrame = new TabHandler("Jive Browser");
    	mainFrame.addTab(0);
    	mainFrame.initialize();
    }
    
    public TabHandler(String title) {
    	super(title);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    	
    	initMenuBar();
    }
    
    public void initialize() {
        pane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setSize(new Dimension(1024, 768));
        setLocationRelativeTo(null);
        
    	getContentPane().add(pane, BorderLayout.CENTER);
        setVisible(true);
    }
     
    private void addTab(int i){
    	String title = "New Tab";
        pane.add(title, new TabBrowser(pane));
        initTabComponent(i);
        pane.setSelectedIndex(i);
    }
     
    private void initTabComponent(int i) {
        pane.setTabComponentAt(i,
                 new TabButtonComponent(pane));
    }    
 
    
    public void initMenuBar() {
    	JMenuBar menuBar = new JMenuBar();
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
        
        JMenu viewMenu = new JMenu("View");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem historyMenuItem = new JMenuItem("History",
                KeyEvent.VK_X);
        historyMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               //TODO: History
            }
        });
       
        viewMenu.add(historyMenuItem);
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);
    }  

}
