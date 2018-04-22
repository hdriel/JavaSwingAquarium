package driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import DesignPatterns.Singleton_Worm;
import abstracts_classes.Animal;


public class AquaFrame extends JFrame implements  ActionListener{

	private int AMOUNT_ANIMALS = 5;
	private int hieght = 700;
	private int width = 700;
	
	private ArrayList<Animal> listAnimals = new ArrayList<Animal>();
	private Singleton_Worm sw;
	private JButton btn_AddAnimal , btn_Sleep, btn_WakeUp, btn_ClearAll;
	private static JButton btn_Food;
	private JButton btn_Info;
	private JButton btn_Exit;
	private Thread ControllerThread;
	private JPanel p_main; 
	private static AquaPanel p_aqua;
	private Color clr_none = new Color(240,240,240);
	private int clicked = 0;
	private static int status_worms = 20;
	private InfoJTable table;
	private boolean moreThen5 = false;
	private Preferences node;
	private String imagebgpath = "";
	private int left_ScreenAquarum = 0;
	private int top_ScreenAquarum = 0;
	private int width_ScreenAquarum = 700;
	private int height_ScreenAquarum = 700;
	private JLabel moreOptionInfo = new JLabel("NOTE: click on (select) some row , then right click on mouse to more option");
	
	// Ctor
	public AquaFrame(){
		super("Animals in aquarium");
		
		Preferences root = Preferences.userRoot();
		node = root.node("/com/horstmann/corejava");
		left_ScreenAquarum = node.getInt("left", 0);
		top_ScreenAquarum = node.getInt("top", 0);
	    setLocation(left_ScreenAquarum, top_ScreenAquarum);
	    width_ScreenAquarum = node.getInt("width_screen", width_ScreenAquarum);
	    height_ScreenAquarum = node.getInt("height_screen", height_ScreenAquarum);
	    imagebgpath = node.get("imagebg", "");
	    this.setSize(width_ScreenAquarum,height_ScreenAquarum);
	    
	    p_main = new JPanel();
	    p_aqua = new AquaPanel(listAnimals, sw);
		p_aqua.setSize(width, hieght);
		p_aqua.setBackground(clr_none);
		Animal.setAquaPanel(p_aqua);
		
		sw.getInstance(p_aqua);
		p_aqua.setWorm(sw);
		
		ControllerThread = new Thread(p_aqua);
		ControllerThread.start();
		
		init_Menu();
		init_Butons();
		//btn_WakeUp.setEnabled(false);
		
		p_main.add(p_aqua);
		this.add(p_main);
		this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	finishProgram("Fram");
                e.getWindow().dispose();
            }
        });
		
		
		this.setVisible(true);	
		// create functionality to close the window when "Escape" button is pressed
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action action = new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				finishProgram("Key ESC");
                System.exit(0);
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		
		init_background();
	}
	
	// Listeners for all the components by clicking
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btn_AddAnimal)
		{
			if(!moreThen5)
			{
				this.setEnabled(false);
				new AddAnimalDialog(this, listAnimals);
				if(listAnimals.size() == AMOUNT_ANIMALS - 1){
					moreThen5 = true;
				}
			}
			else{
				int output = JOptionPane.showConfirmDialog(
							this
			               , "Do you want to add more then 5 animal to the aquarium ?"
			               ,"Adding animal dialog" 
			               ,JOptionPane.YES_NO_OPTION);

	            if(output == JOptionPane.YES_OPTION){
	            	this.setEnabled(false);
					new AddAnimalDialog(this, listAnimals);
					p_aqua.returnLife();
	            }else if(output == JOptionPane.NO_OPTION){
	            }
			    moreThen5 = false;
			}
			p_aqua.returnLife();
		}
		else if(e.getSource() == btn_Sleep)
		{
			p_aqua.setSuspended();
			//btn_WakeUp.setEnabled(true);
		}
		else if(e.getSource() == btn_WakeUp)
		{
			p_aqua.setResumed();
			//btn_WakeUp.setEnabled(false);
		}
		else if(e.getSource() == btn_ClearAll)
		{
			p_aqua.setDestroyThread();
		}
		else if(e.getSource() == btn_Food)
		{
			p_aqua.raiseFoodFlag();
			btn_Food.setText("Food (" + status_worms +")");
		}
		else if(e.getSource() == btn_Info)
		{
			clicked++;
	        
			if(clicked % 2 == 0){ // display aquarium
				btn_AddAnimal.setEnabled(true);
				btn_ClearAll.setEnabled(true);
				btn_Food.setEnabled(true);
				btn_Sleep.setEnabled(true);
				btn_WakeUp.setEnabled(true);
				
				p_main.remove(moreOptionInfo);
				p_main.remove(table);
				p_main.add(p_aqua);
				
			    width_ScreenAquarum = node.getInt("width_screen", width_ScreenAquarum);
				height_ScreenAquarum = node.getInt("height_screen", height_ScreenAquarum);
				this.setSize(width_ScreenAquarum,height_ScreenAquarum);
			}
			else{ // display table
				btn_AddAnimal.setEnabled(false);
				btn_ClearAll.setEnabled(false);
				btn_Food.setEnabled(false);
				btn_Sleep.setEnabled(false);
				btn_WakeUp.setEnabled(false);
				
				table = new InfoJTable(listAnimals, p_aqua, btn_Info);
				table.setAnimals(listAnimals);
				p_main.remove(p_aqua);
				moreOptionInfo.setHorizontalAlignment(JLabel.CENTER);
				p_main.add(moreOptionInfo, BorderLayout.NORTH);
				p_main.add(table);
				
				node.putInt("width_screen", getWidth());
			    node.putInt("height_screen", getHeight());
				this.pack();
			}
			//for(Locatable c : listAnimals)
			//	System.out.println(c);
		}
		else if(e.getSource() == btn_Exit)
		{
			finishProgram("Button");
            System.exit(0);
		}
		else
		{
			
		}
		repaint();
	}
	
	//  *********** Other help function  *************
	// finish the program and save data
	private void finishProgram(String from){
			p_aqua.setDestroyThread();
			p_aqua.finish();
			ControllerThread.stop();
			ControllerThread.interrupt();
	        System.out.println("Closed by EXIT of "+ from);
	        node.putInt("left", getX());
	        node.putInt("top", getY());
	        node.putInt("width_screen", getWidth());
	        node.putInt("height_screen", getHeight());
	        node.put("imagebg", imagebgpath);
		}
	// Resize the image dependently panel size 
	private Image ScaleImage(Image image, int w, int h){
		BufferedImage resizedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		g2.dispose();
		return resizedImage;
	}	
	public static AquaPanel AquaPanelReferance(){
		return p_aqua;
	}
	//  *********** initialization *******************
	private void init_background(){
			if(imagebgpath.equals("")){
				p_aqua.setBackground(clr_none);
			}
			else if(imagebgpath.equals("BLUE")){
				p_aqua.setBackground(Color.BLUE);
			}
			else{
				try{
			    	File f = new File(imagebgpath);
					System.out.println(f.getPath());
					Image img;
					try {
						img = ImageIO.read(f);
						img = ScaleImage(img, p_aqua.getWidth(), p_aqua.getHeight());
						p_aqua.setImageBackground(img);
						System.out.println("set background image!");
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("can't read the image file!");
					}
			    }
			    catch (NullPointerException  ef){
			    	
			    }
			}
		}
	private void init_Butons(){
		JPanel panel_buttons = new JPanel();
		panel_buttons.setLayout(new GridLayout(1, 7));
		
		btn_AddAnimal = new JButton("Add Animal");
		btn_AddAnimal.addActionListener(this);
		panel_buttons.add(btn_AddAnimal);
		
		btn_Sleep = new JButton("Sleep");
		btn_Sleep.addActionListener(this);
		panel_buttons.add(btn_Sleep);
		
		btn_WakeUp = new JButton("Wake up");
		btn_WakeUp.addActionListener(this);
		panel_buttons.add(btn_WakeUp);
		
		btn_ClearAll = new JButton("Clear All");
		btn_ClearAll.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
		btn_ClearAll.addActionListener(this);
		panel_buttons.add(btn_ClearAll);
		
		btn_Food = new JButton("Food (" + status_worms +")");
		btn_Food.addActionListener(this);
		panel_buttons.add(btn_Food);
		
		btn_Info= new JButton("Info");
		btn_Info.addActionListener(this);
		panel_buttons.add(btn_Info);
		
		btn_Exit= new JButton("Exit");
		btn_Exit.addActionListener(this);
		panel_buttons.add(btn_Exit);
		
		
		p_main.setLayout(new BorderLayout());
		p_main.add(panel_buttons, BorderLayout.SOUTH);
	}
	
	public static void initWormStatus(int w){
		status_worms = w;
		btn_Food.setText("Food (" + status_worms +")");
	}
	//  *********** menu bar *************************
	private void init_Menu(){
		JMenuBar menuBar = new JMenuBar(); 
				
		//_____________________________________________//
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		// --------------------------------------------//
		JMenuItem menuItemExit = new JMenuItem();
		Action ExitAction = new AbstractAction("Exit") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	finishProgram("Menu");
                System.exit(0);
		    }
		};
		ExitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK ) );
		menuItemExit.setAction(ExitAction);
		menuFile.add(menuItemExit);  
		//_____________________________________________//
		JMenu menuBackground = new JMenu("Background");
		menuBackground.setMnemonic(KeyEvent.VK_B);
		// --------------------------------------------//
		JMenuItem menuItemImage = new JMenuItem();
		Action ImageAction = new AbstractAction("Image") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	FileDialog fd = new FileDialog(new JFrame(), "Please choose a file:",FileDialog.LOAD);

				fd.setVisible(true);
				File f;
				if (fd.getFile() != null)
			    {
					f = new File(fd.getDirectory(), fd.getFile());
					System.out.println(f.getPath());
					imagebgpath = f.getPath();
					//BufferedImage img;
					Image img;
					try {
						img = ImageIO.read(f);
						img = ScaleImage(img, p_aqua.getWidth(), p_aqua.getHeight());
						p_aqua.setImageBackground(img);
						System.out.println("set background image!");
					} catch (IOException e1) {
						e1.printStackTrace();
						System.out.println("can't read the image file!");
					}
					
			    }
		    }
		};
		ImageAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK ) );
		menuItemImage.setAction(ImageAction);
		menuBackground.add(menuItemImage);  
		
		JMenuItem menuItemBlue = new JMenuItem("Blue");
		Action BlueAction = new AbstractAction("Blue") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	p_aqua.setBackground(Color.BLUE);
		    	p_aqua.setImageBackground(null);
		    	imagebgpath = "BLUE";
		    }
		};
		BlueAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK ) );
		menuItemBlue.setAction(BlueAction);
		menuBackground.add(menuItemBlue);  
		
		JMenuItem menuItemNone = new JMenuItem();
		Action NoneAction = new AbstractAction("None") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	p_aqua.setBackground(clr_none);
		    	p_aqua.setImageBackground(null);
		    	imagebgpath = "";
		    }
		};
		NoneAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK ) );
		menuItemNone.setAction(NoneAction);
		menuBackground.add(menuItemNone);  
		//_____________________________________________//
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		// --------------------------------------------//
		JMenuItem menuItemHelp = new JMenuItem();
		Action HelpAction = new AbstractAction("Help") {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	JOptionPane.showMessageDialog(null,"Home Word 3\nGUI @ Threads\n__Hadriel_Benjo__"); 
		    }
		};
		HelpAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke ( KeyEvent.VK_H, KeyEvent.CTRL_DOWN_MASK ) );
		menuItemHelp.setAction(HelpAction);
		menuHelp.add(menuItemHelp);  
		//_____________________________________________//
		
		menuBar.add(menuFile); 
		menuBar.add(menuBackground);       
		menuBar.add(menuHelp);
		
		this.setJMenuBar(menuBar);                     
	}
	
	//  *********** main method **********************
	public static void main(String [] args){
		AquaFrame a = new AquaFrame();
	}
}
