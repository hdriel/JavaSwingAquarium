package driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import abstracts_classes.Animal;
import factories.CreateFactoryByType;

//Hadriel Benjo 302185061 , campus: Be'er Sheva

public class AddAnimalDialog extends JDialog implements ActionListener, ChangeListener {

	private JSlider s_size, s_ver_speed, s_hor_speed, s_freq;
	private int val_verSpeed, val_horSpeed, val_sizepx, val_freq;
	private JTextField tf_size, tf_var, tf_hor, tf_freq;
	private JLabel l_size, l_var, l_hor, l_freq;
	private Color clr;
	private JComboBox comboBoxColor;
	private JRadioButton radio_Fish, radio_JellyFish, radio_Lobster, radio_SeaUrchin, radio_Trochus;
	private Animal anim;
	private JButton btn_add;
	private JFrame parent;
	private ArrayList<Animal> listAnimals;
	private Preferences node;

	
	// Ctor
	public AddAnimalDialog(JFrame parent , ArrayList<Animal> listAnimals) {
		super(parent, "Create a new Amimal");
		// set the position of the window
		Preferences root = Preferences.userRoot();
		node = root.node("/com/horstmann/corejava");
	    int left = node.getInt("left", 0);
	    int top = node.getInt("top", 0);
	    setLocation(left, top);
	    
	    
		this.parent = parent;
		this.listAnimals = listAnimals;
		val_verSpeed = val_horSpeed =  5;
		val_freq = 10;
		val_sizepx = 50;
		clr = Color.red;
		
		JPanel m_pane = new JPanel();
		m_pane.setLayout(new BoxLayout(m_pane, BoxLayout.X_AXIS));
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		
		JPanel pane_filed;
		// ----------------------------------------------// 
		pane_filed = new JPanel(new GridLayout(1,1));
		pane_filed.add(new JLabel("choose the type animal:"));
		pane.add(pane_filed);
		
		pane_filed = new JPanel(new GridLayout(1,5));
		radio_Fish = new JRadioButton("Fish");
		radio_JellyFish = new JRadioButton("JellyFish");
		radio_Lobster = new JRadioButton("Lobster");
		radio_SeaUrchin = new JRadioButton("Sea Urchin");
		radio_Trochus = new JRadioButton("Trochus");
		
		ButtonGroup group = new ButtonGroup();
		group.add(radio_Fish);
		group.add(radio_JellyFish);
		group.add(radio_Lobster);
		group.add(radio_SeaUrchin);
		group.add(radio_Trochus);
		
		
		int randrdio = (int) (Math.random()*7 + 1);
		switch (randrdio) {
		case 1:	radio_JellyFish.setSelected(true); 	break;
		case 2:	radio_Lobster.setSelected(true);	break;
		case 3:	radio_SeaUrchin.setSelected(true);	break;
		case 4:	radio_Trochus.setSelected(true);	break;
		default:radio_Fish.setSelected(true);   	break;
		}
		
		pane_filed.add(radio_Fish);
		pane_filed.add(radio_JellyFish);
		pane_filed.add(radio_Lobster);
		pane_filed.add(radio_SeaUrchin);
		pane_filed.add(radio_Trochus);
		pane.add(pane_filed);
        
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		// ----------------------------------------------// 
		l_size = new JLabel("size: ");
		s_size      = new JSlider(JSlider.HORIZONTAL, 20, 300, 50);
		s_size.setMajorTickSpacing(15);
		s_size.setPaintTicks(true);
		s_size.setPaintLabels(true);
		s_size.setLabelTable(s_size.createStandardLabels(30));
		s_size.addChangeListener(this);
		
		tf_size = new JTextField(String.valueOf(50),3);
		tf_size.setHorizontalAlignment(JTextField.CENTER);
		tf_size.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		tf_size.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	tf_size.selectAll();
            }
        });
		tf_size.addKeyListener(new KeyListener() {		 
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	char c = e.getKeyChar();
		        if (!(
		        		(c >= '0') && (c <= '9') ||
		        		(c == KeyEvent.VK_BACK_SPACE) ||
		        		(c == KeyEvent.VK_DELETE)
		        	  )) 
		        {
		          getToolkit().beep();
		          e.consume();
		        }
		    }
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	int num;
		    	try{
		    		num = Integer.parseInt(tf_size.getText());
		    		if(num > 300 || num < 20 ){
			        	tf_size.setForeground(Color.RED);
			        }
			    	else
			    		tf_size.setForeground(Color.BLACK);
		    	}
		    	catch(NumberFormatException nfe){
		    		tf_size.setForeground(Color.RED);
		    	}
		    	
		    }
		    @Override
		    public void keyPressed(KeyEvent event) {
		        
		    }
		});
		
		pane_filed = new JPanel(new GridLayout(2,2));		
		pane_filed.add(l_size);
		pane_filed.add(new JLabel(""));
		pane_filed.add(s_size);
		pane_filed.add(tf_size);
		pane.add(pane_filed);
		
		
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		// ----------------------------------------------// 
		l_var = new JLabel("vertical speed: ");
		s_ver_speed = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);		
		s_ver_speed.setMajorTickSpacing(1);
		s_ver_speed.setPaintTicks(true);
		s_ver_speed.setPaintLabels(true);
		s_ver_speed.setLabelTable(s_ver_speed.createStandardLabels(1));
		s_ver_speed.addChangeListener(this);
		Hashtable labelTable1 = new Hashtable();
		labelTable1.put( new Integer( 10/10 ), new JLabel("Slow") );
		labelTable1.put( new Integer( 10 ), new JLabel("Fast") );
		s_ver_speed.setLabelTable( labelTable1 );
		tf_var = new JTextField(String.valueOf(5),2);
		tf_var.setHorizontalAlignment(JTextField.CENTER);
		tf_var.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		tf_var.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	tf_var.selectAll();
            }
        });
		tf_var.addKeyListener(new KeyListener() {		 
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	char c = e.getKeyChar();
		        if (!(
		        		(c >= '0') && (c <= '9') ||
		        		(c == KeyEvent.VK_BACK_SPACE) ||
		        		(c == KeyEvent.VK_DELETE)
		        	  )) 
		        {
		          getToolkit().beep();
		          e.consume();
		        }
		    }
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	int num;
		    	try{
		    		num = Integer.parseInt(tf_var.getText());
		    		if(num > 10 || num < 1 ){
		    			tf_var.setForeground(Color.RED);
			        }
			    	else
			    		tf_var.setForeground(Color.BLACK);
		    	}
		    	catch(NumberFormatException nfe){
		    		tf_var.setForeground(Color.RED);
		    	}
		    	
		    }
		    @Override
		    public void keyPressed(KeyEvent event) {
		        
		    }
		});
		
		pane_filed = new JPanel(new GridLayout(2,2));
		pane_filed.add(l_var);
		pane_filed.add(new JLabel(""));
		pane_filed.add(s_ver_speed);
		pane_filed.add(tf_var);
		pane.add(pane_filed);
		
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		// ----------------------------------------------// 
		l_hor = new JLabel("horizontal speed: ");
		s_hor_speed = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
		s_hor_speed.setMajorTickSpacing(1);
		s_hor_speed.setPaintTicks(true);
		s_hor_speed.setPaintLabels(true);
		s_hor_speed.setLabelTable(s_hor_speed.createStandardLabels(1));
		s_hor_speed.addChangeListener(this);
		Hashtable labelTable2 = new Hashtable();
		labelTable2.put( new Integer( 10/10 ), new JLabel("Slow") );
		labelTable2.put( new Integer( 10 ), new JLabel("Fast") );
		s_hor_speed.setLabelTable( labelTable2 );
		 
		tf_hor = new JTextField(String.valueOf(5),2);
		tf_hor.setHorizontalAlignment(JTextField.CENTER);
		tf_hor.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		tf_hor.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	tf_hor.selectAll();
            }
        });
		tf_hor.addKeyListener(new KeyListener() {		 
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	char c = e.getKeyChar();
		        if (!(
		        		(c >= '0') && (c <= '9') ||
		        		(c == KeyEvent.VK_BACK_SPACE) ||
		        		(c == KeyEvent.VK_DELETE)
		        	  )) 
		        {
		          getToolkit().beep();
		          e.consume();
		        }
		    }
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	int num;
		    	try{
		    		num = Integer.parseInt(tf_hor.getText());
		    		if(num > 10 || num < 1 ){
		    			tf_hor.setForeground(Color.RED);
			        }
			    	else
			    		tf_hor.setForeground(Color.BLACK);
		    	}
		    	catch(NumberFormatException nfe){
		    		tf_hor.setForeground(Color.RED);
		    	}
		    	
		    }
		    @Override
		    public void keyPressed(KeyEvent event) {
		        
		    }
		});
		
		pane_filed = new JPanel(new GridLayout(2,2));
		pane_filed.add(l_hor);
		pane_filed.add(new JLabel(""));
		pane_filed.add(s_hor_speed);
		pane_filed.add(tf_hor);
		pane.add(pane_filed);
		
		
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		pane.add(Box.createVerticalStrut(15));
		// ----------------------------------------------// 
		l_freq = new JLabel("Frequency hungry (in seconds): ");
		s_freq = new JSlider(JSlider.HORIZONTAL, 5, 20, 10);		
		s_freq.setMajorTickSpacing(1);
		s_freq.setPaintTicks(true);
		s_freq.setPaintLabels(true);
		s_freq.setLabelTable(s_ver_speed.createStandardLabels(1));
		s_freq.addChangeListener(this);
		Hashtable labelTable3 = new Hashtable();
		labelTable3.put( new Integer( 10/10 ), new JLabel("Any minute") );
		labelTable3.put( new Integer( 10 ), new JLabel("Almost never") );
		s_freq.setLabelTable( labelTable3 );
		tf_freq = new JTextField(String.valueOf(10),2);
		tf_freq.setHorizontalAlignment(JTextField.CENTER);
		tf_freq.setFont(new java.awt.Font("Arial", Font.BOLD, 20));
		tf_freq.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            	tf_freq.selectAll();
            }
        });
		tf_freq.addKeyListener(new KeyListener() {		 
		    @Override
		    public void keyTyped(KeyEvent e) {
		    	char c = e.getKeyChar();
		        if (!(
		        		(c >= '0') && (c <= '9') ||
		        		(c == KeyEvent.VK_BACK_SPACE) ||
		        		(c == KeyEvent.VK_DELETE)
		        	  )) 
		        {
		          getToolkit().beep();
		          e.consume();
		        }
		    }
		    @Override
		    public void keyReleased(KeyEvent event) {
		    	int num;
		    	try{
		    		num = Integer.parseInt(tf_var.getText());
		    		if(num > 20 || num < 5 ){
		    			tf_freq.setForeground(Color.RED);
			        }
			    	else
			    		tf_freq.setForeground(Color.BLACK);
		    	}
		    	catch(NumberFormatException nfe){
		    		tf_freq.setForeground(Color.RED);
		    	}
		    	
		    }
		    @Override
		    public void keyPressed(KeyEvent event) {
		        
		    }
		});
		
		pane_filed = new JPanel(new GridLayout(2,2));
		pane_filed.add(l_freq);
		pane_filed.add(new JLabel(""));
		pane_filed.add(s_freq);
		pane_filed.add(tf_freq);
		pane.add(pane_filed);
		
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		pane.add(Box.createVerticalStrut(15));
		// ----------------------------------------------// 
		
		
		Color colors[] = {  Color.BLUE ,Color.RED, Color.GREEN, Color.BLACK, Color.WHITE , Color.CYAN , Color.YELLOW , Color.DARK_GRAY, Color.GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK};
		String strcolors[] = {"BLUE" , "RED" , "GREEN" ,"BLACK" ,"WHITE" ,"CYAN" ,"YELLOW" ,"DARK_GRAY" ,"GRAY" ,"MAGENTA" ,"ORANGE" ,"PINK" };
		int randcolorindex = (int)(Math.random() * colors.length);
		comboBoxColor = new JComboBox(colors);
		comboBoxColor.setSelectedIndex(randcolorindex);
		comboBoxColor.setEditable(true);
		comboBoxColor.setEditor(new ColorComboBoxEditor(colors[(int)(Math.random() * colors.length)]));
		
		pane_filed = new JPanel(new GridLayout(1,2));
		pane_filed.add(new JLabel("Choose color: "));
		pane_filed.add(comboBoxColor);
		pane.add(pane_filed);
		
		pane.add(Box.createVerticalStrut(15));
		pane.add(new JSeparator(SwingConstants.HORIZONTAL));
		pane.add(Box.createVerticalStrut(15));
		// ----------------------------------------------// 
		btn_add = new JButton("Add");
		btn_add.addActionListener(this);
		
		pane_filed = new JPanel(new GridLayout(1,1));
		pane_filed.add(btn_add);
		pane.add(pane_filed);

		
		//getContentPane().add(pane, BorderLayout.PAGE_END);
		m_pane.setLayout(new BoxLayout(m_pane, BoxLayout.LINE_AXIS));
		m_pane.add(Box.createHorizontalStrut(10));
		m_pane.add(new JSeparator(SwingConstants.VERTICAL));
		m_pane.add(Box.createHorizontalStrut(5));
		m_pane.add(pane);
		m_pane.add(Box.createHorizontalStrut(5));
		m_pane.add(new JSeparator(SwingConstants.VERTICAL));
		m_pane.add(Box.createHorizontalStrut(10));
		m_pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		
		
		// set action listener on the button
		getContentPane().add(m_pane, BorderLayout.PAGE_END);
		
		addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                System.out.println("Closed");
                parent.setEnabled(true);
                node.putInt("left", getX());
                node.putInt("top", getY());
                e.getWindow().dispose();
            }
        });
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);
	}
	
	// override the createRootPane inherited by the JDialog, to create the rootPane.
	// create functionality to close the window when "Escape" button is pressed
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");
		Action action = new AbstractAction() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("escaping..");
				setVisible(false);
				parent.setEnabled(true);
				node.putInt("left", getX());
	            node.putInt("top", getY());
				dispose();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	// check and action to the state changed of the JSliders
	public void stateChanged(ChangeEvent e) {
	    JSlider source = (JSlider)e.getSource();
	    if (source == s_size && !source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        tf_size.setText(String.valueOf(fps));
	        val_sizepx = fps;
	    }
	    if (source == s_ver_speed && !source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        tf_var.setText(String.valueOf(fps));
	        val_verSpeed = fps;
	    }
	    if (source == s_hor_speed && !source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        tf_hor.setText(String.valueOf(fps));
	        val_horSpeed = fps;
	    }
	    if (source == s_freq && !source.getValueIsAdjusting()) {
	        int fps = (int)source.getValue();
	        tf_freq.setText(String.valueOf(fps));
	        val_freq = fps;
	    }
	}
	
	// Listeners for all the components by clicking
	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("something clicked..");
		if(e.getSource() == btn_add)
		{
			System.out.println("check and create object..");
			if(radio_Fish.isSelected())
			{			
				anim = (Animal)(new CreateFactoryByType()).create("Animal", "Fish");
				if(anim != null){						
					anim.setPanel((JPanel)parent.getContentPane());
					anim.setHorSpeed(val_horSpeed);
					anim.setVerSpeed(val_verSpeed);
					anim.setSize(val_sizepx);
					anim.setColor((Color) comboBoxColor.getEditor().getItem());	// decorator : call to PaintFish(color);			
					Random rand = new Random();
					anim.setLocation(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
					anim.setFrequency(val_freq);
					anim.attach(AquaFrame.AquaPanelReferance());
					
					System.out.println("Fish object created..");
				}
			}
			else if(radio_JellyFish.isSelected())
			{
				anim = (Animal)(new CreateFactoryByType()).create("Animal", "JellyFish");
				if(anim != null){	
					anim.setPanel((JPanel)parent.getContentPane());
					anim.setHorSpeed(val_horSpeed);
					anim.setVerSpeed(val_verSpeed);
					anim.setSize(val_sizepx);
					anim.setColor((Color) comboBoxColor.getEditor().getItem());	// decorator : call to PaintFish(color);			
					Random rand = new Random();
					anim.setLocation(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
					anim.setFrequency(val_freq);
					anim.attach(AquaFrame.AquaPanelReferance());
					
					System.out.println("JellyFish object created..");
				}
			}
			else if(radio_Lobster.isSelected())
			{
				anim = (Animal)(new CreateFactoryByType()).create("Animal", "Lobster");
				if(anim != null){	
					anim.setPanel((JPanel)parent.getContentPane());
					anim.setHorSpeed(val_horSpeed);
					anim.setVerSpeed(val_verSpeed);
					anim.setSize(val_sizepx);
					anim.setColor((Color) comboBoxColor.getEditor().getItem());				
					Random rand = new Random();
					anim.setLocation(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
					anim.attach(AquaFrame.AquaPanelReferance());
					
					System.out.println("Lobster object created..");
				}
			}
			else if(radio_SeaUrchin.isSelected())
			{
				anim = (Animal)(new CreateFactoryByType()).create("Animal", "SeaUrchin");
				if(anim != null){
					anim.setPanel((JPanel)parent.getContentPane());
					anim.setHorSpeed(val_horSpeed);
					anim.setVerSpeed(val_verSpeed);
					anim.setSize(val_sizepx);
					anim.setColor((Color) comboBoxColor.getEditor().getItem());				
					Random rand = new Random();
					anim.setLocation(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
					anim.attach(AquaFrame.AquaPanelReferance());
					
					System.out.println("SeaUrchin object created..");
				}
			}
			else if(radio_Trochus.isSelected())
			{
				anim = (Animal)(new CreateFactoryByType()).create("Animal", "Trochus");
				if(anim != null){
					anim.setPanel((JPanel)parent.getContentPane());
					anim.setHorSpeed(val_horSpeed);
					anim.setVerSpeed(val_verSpeed);
					anim.setSize(val_sizepx);
					anim.setColor((Color) comboBoxColor.getEditor().getItem());				
					Random rand = new Random();
					anim.setLocation(rand.nextInt(this.getWidth()), rand.nextInt(this.getHeight()));
					anim.attach(AquaFrame.AquaPanelReferance());
					
					System.out.println("Trochus object created..");
				}
			}
			parent.setEnabled(true);
		}
		
		if(anim != null){
			System.out.println("added animal!!");
			listAnimals.add(anim);
			try{
				anim.start();	
			}catch(Exception s){
				listAnimals.remove(anim);
				System.out.println("There can be only five Lobster!");
			}
			
		}
		System.out.println("disposing the window..");
		setVisible(false);
		node.putInt("left", getX());
        node.putInt("top", getY());
		dispose();
	}
	
	
	
	/*
	public class panelDraw extends JPanel {
		private static final long serialVersionUID = 1L;
		Animal anim;
		public panelDraw(Animal anim){
			this.anim = anim;
			repaint();
		}
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        System.out.println("draw the animal in panel draw");
	        anim.setPanel(this);
	        anim.drawAnimal(g);
	    }
	}
	*/
}


