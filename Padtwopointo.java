//-----------------------------------------------------
//SEN 602 July 2018 Bit-Changer 'Pad"
//Student name: Sam Gebra  
//NU ID Number: 040970530
//
//File Name: Padtwopointo.java
//
//This class displays a button-pixlated pad
//for the user to draw a shape (in this case
//a number) after entering the number the 
//user manually use the textField to input 
//the drawn shape in numeric integer value
//press submit and then repeat the process
// several times until satisfied with the data
//library size
//
// note: 'several times' in this case was set to 
// 10 for simplicity
//
// Also the software has extra features
// 'clear' button to erase drawing
// 'my csv' button to show the user
// where is their saved file is
// and finally 'help' button to give the user
// useful tips of how to use the class 'Pad'
//
//Date: September  02, 2018
//----------------------------------------------------
//For GUI
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
//For user data input
import java.util.Scanner; 
//For optional sleep timers when needed
import java.util.concurrent.TimeUnit; 
//For csv file saving
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public final class Padtwopointo extends JFrame implements ActionListener {
	//-------------------------------------------------------------
	// Declaring the private attributes pad
	//-------------------------------------------------------------
	private GridLayout drawingPadLayout = new GridLayout(10,5);
	private GridLayout utilitiesLayout = new GridLayout(2,2);
	private JPanel drawingPad = new JPanel();
	private JPanel utilities = new JPanel();
	private JButton submit = new JButton("SUBMIT");
	private JButton done = new JButton("DONE");
	private JButton clear = new JButton("CLEAR");
	private JButton csvPathButton = new JButton("MY CSV");
	private String mycsvPath = "";
	private JButton help = new JButton("HELP");
	private JTextField inputField = new JTextField(20);
	private String enteredData;
	private String userFilenameInput;
	private String fileName = "defaultNeuralNetworkfile.csv";
	private final int padV = 10;
	private final int padH = 5;
	private final int totalPadButtons = 50;
	private final int numberOfEntries = 10; // for  simplicity we are setting that to ten only 
	private File file;
	private FileWriter neuralNetworkFeedback;
    private StringBuilder stringBuilderVar = new StringBuilder();
    private JFrame path_menu;
    private JFrame help_menu;
    public static final int WIDTH = 250; // The width of the SwingCalculator window
	public static final int HEIGHT = 520; // The height of the Swing
	private String cycleCounterMessage = "No entry yet!";
	private JTextField cycleCounterTextBox = new JTextField(40);
	

		
	// Declaring the pad buttons
	private JButton[][] arrayOfButtons = new JButton[padV][padH];
	// Declaring the array that is going to store the pad input values
	// this array is going to be of boolean type
	// where false is for a button-pixel that was not chosen
	// and true for when that button-pixel is chosen
	// by chosen we mean clicked-on or hovered-above 
	// using the mouse
	private boolean[][] padFeedBack = new boolean[padV][padH];
	// Declaring the array that is going to store the input data from
	// the padFeedBack and place it next to the output data which is
	// the number that the user has entered 
	private int[][] finalCSVdata = new int[numberOfEntries][totalPadButtons+1];
	//also, totalPadButtons+1 is for the added extra new column for the OUTPUT value taken from the textField
	
	
	private int currentCSVrow = 0;
	private int CurrentCSVcol = 0;

	
	public Padtwopointo() {
		//Setting up the window's title to Pad 2.0
		super("Pad 2.0");
		
		//Setting up the GridLayout Horizontal and Vertical Gaps
		drawingPadLayout.setHgap(0);
		drawingPadLayout.setVgap(0);
		drawingPad.setLayout(drawingPadLayout);
		utilitiesLayout.setHgap(0);
		utilitiesLayout.setVgap(0);
		utilities.setLayout(utilitiesLayout);
		
		//Set pad buttons size
		for(int i = 0; i < padV; i++)
		{
			for(int j = 0; j < padH; j++)
			{
				//initializing each JButton
				arrayOfButtons[i][j] = new JButton("");				
				//Set pad buttons size
				(arrayOfButtons[i][j]).setPreferredSize(new Dimension(20, 40));				
				//Set the JButton color to blue
				(arrayOfButtons[i][j]).setBackground(Color.BLUE);
				//set Opaque
				(arrayOfButtons[i][j]).setOpaque(true);
				//Add buttons to drawingPad
				drawingPad.add(arrayOfButtons[i][j]);
				//finally setting all the elements of the array padFeedBack to false/zero
				padFeedBack[i][j] =false;
			}
		}
		
		//Adding the action listener to the buttons
		inputField.addActionListener(this);
		submit.addActionListener(this);
		done.addActionListener(this);
		help.addActionListener(this);
		csvPathButton.addActionListener(this);
		clear.addActionListener(this);
		
		//Add control buttons to utilities
		utilities.add(inputField);
		utilities.add(submit);
		utilities.add(done);
		utilities.add(clear);
		utilities.add(csvPathButton);
		utilities.add(help);
		inputField.setText("Enter output!");
		
		// For a more use-friendly UI
		// Making the displayed message or number disappear
		// upon clicking inside the Text Field				
		inputField.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent evt) {
						inputField.setText("");
					}
				});
		
		//creating an action listener for each button
		//please note that the blocks for each button
		// was written in a way to facilitate numbering 
		// and typo errors detection
		//--------------------------------------------		
		// ---- first column of the drawing Pad
		arrayOfButtons[0][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[0][0].setBackground(Color.RED);
			   padFeedBack[0][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[0][0].setBackground(Color.GRAY);}});
			arrayOfButtons[1][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[1][0].setBackground(Color.RED);
			   padFeedBack[1][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[1][0].setBackground(Color.GRAY);}});
			arrayOfButtons[2][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[2][0].setBackground(Color.RED);
			   padFeedBack[2][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[2][0].setBackground(Color.GRAY);}});
			arrayOfButtons[3][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[3][0].setBackground(Color.RED);
			   padFeedBack[3][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[3][0].setBackground(Color.GRAY);}});
			arrayOfButtons[4][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[4][0].setBackground(Color.RED);
			   padFeedBack[4][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[4][0].setBackground(Color.GRAY);}});
			arrayOfButtons[5][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[5][0].setBackground(Color.RED);
			   padFeedBack[5][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[5][0].setBackground(Color.GRAY);}});
			arrayOfButtons[6][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[6][0].setBackground(Color.RED);
			   padFeedBack[6][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[6][0].setBackground(Color.GRAY);}});
			arrayOfButtons[7][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[7][0].setBackground(Color.RED);
			   padFeedBack[7][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[7][0].setBackground(Color.GRAY);}});
			arrayOfButtons[8][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[8][0].setBackground(Color.RED);
			   padFeedBack[8][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[8][0].setBackground(Color.GRAY);}});
			arrayOfButtons[9][0].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			arrayOfButtons[9][0].setBackground(Color.RED);
			   padFeedBack[9][0] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
			arrayOfButtons[9][0].setBackground(Color.GRAY);}});
			// ---- second column of the drawing Pad
			arrayOfButtons[0][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[0][1].setBackground(Color.RED);
				   padFeedBack[0][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[0][1].setBackground(Color.GRAY);}});
				arrayOfButtons[1][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[1][1].setBackground(Color.RED);
				   padFeedBack[1][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[1][1].setBackground(Color.GRAY);}});
				arrayOfButtons[2][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[2][1].setBackground(Color.RED);
				   padFeedBack[2][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[2][1].setBackground(Color.GRAY);}});
				arrayOfButtons[3][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[3][1].setBackground(Color.RED);
				   padFeedBack[3][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[3][1].setBackground(Color.GRAY);}});
				arrayOfButtons[4][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[4][1].setBackground(Color.RED);
				   padFeedBack[4][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[4][1].setBackground(Color.GRAY);}});
				arrayOfButtons[5][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[5][1].setBackground(Color.RED);
				   padFeedBack[5][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[5][1].setBackground(Color.GRAY);}});
				arrayOfButtons[6][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[6][1].setBackground(Color.RED);
				   padFeedBack[6][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[6][1].setBackground(Color.GRAY);}});
				arrayOfButtons[7][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[7][1].setBackground(Color.RED);
				   padFeedBack[7][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[7][1].setBackground(Color.GRAY);}});
				arrayOfButtons[8][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[8][1].setBackground(Color.RED);
				   padFeedBack[8][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[8][1].setBackground(Color.GRAY);}});
				arrayOfButtons[9][1].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
				arrayOfButtons[9][1].setBackground(Color.RED);
				   padFeedBack[9][1] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
				arrayOfButtons[9][1].setBackground(Color.GRAY);}});
				// ---- third column of the drawing Pad
				arrayOfButtons[0][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[0][2].setBackground(Color.RED);
					   padFeedBack[0][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[0][2].setBackground(Color.GRAY);}});
					arrayOfButtons[1][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[1][2].setBackground(Color.RED);
					   padFeedBack[1][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[1][2].setBackground(Color.GRAY);}});
					arrayOfButtons[2][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[2][2].setBackground(Color.RED);
					   padFeedBack[2][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[2][2].setBackground(Color.GRAY);}});
					arrayOfButtons[3][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[3][2].setBackground(Color.RED);
					   padFeedBack[3][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[3][2].setBackground(Color.GRAY);}});
					arrayOfButtons[4][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[4][2].setBackground(Color.RED);
					   padFeedBack[4][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[4][2].setBackground(Color.GRAY);}});
					arrayOfButtons[5][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[5][2].setBackground(Color.RED);
					   padFeedBack[5][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[5][2].setBackground(Color.GRAY);}});
					arrayOfButtons[6][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[6][2].setBackground(Color.RED);
					   padFeedBack[6][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[6][2].setBackground(Color.GRAY);}});
					arrayOfButtons[7][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[7][2].setBackground(Color.RED);
					   padFeedBack[7][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[7][2].setBackground(Color.GRAY);}});
					arrayOfButtons[8][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[8][2].setBackground(Color.RED);
					   padFeedBack[8][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[8][2].setBackground(Color.GRAY);}});
					arrayOfButtons[9][2].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
					arrayOfButtons[9][2].setBackground(Color.RED);
					   padFeedBack[9][2] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
					arrayOfButtons[9][2].setBackground(Color.GRAY);}});
					// ---- forth column of the drawing Pad
					arrayOfButtons[0][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[0][3].setBackground(Color.RED);
						   padFeedBack[0][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[0][3].setBackground(Color.GRAY);}});
						arrayOfButtons[1][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[1][3].setBackground(Color.RED);
						   padFeedBack[1][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[1][3].setBackground(Color.GRAY);}});
						arrayOfButtons[2][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[2][3].setBackground(Color.RED);
						   padFeedBack[2][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[2][3].setBackground(Color.GRAY);}});
						arrayOfButtons[3][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[3][3].setBackground(Color.RED);
						   padFeedBack[3][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[3][3].setBackground(Color.GRAY);}});
						arrayOfButtons[4][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[4][3].setBackground(Color.RED);
						   padFeedBack[4][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[4][3].setBackground(Color.GRAY);}});
						arrayOfButtons[5][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[5][3].setBackground(Color.RED);
						   padFeedBack[5][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[5][3].setBackground(Color.GRAY);}});
						arrayOfButtons[6][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[6][3].setBackground(Color.RED);
						   padFeedBack[6][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[6][3].setBackground(Color.GRAY);}});
						arrayOfButtons[7][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[7][3].setBackground(Color.RED);
						   padFeedBack[7][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[7][3].setBackground(Color.GRAY);}});
						arrayOfButtons[8][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[8][3].setBackground(Color.RED);
						   padFeedBack[8][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[8][3].setBackground(Color.GRAY);}});
						arrayOfButtons[9][3].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
						arrayOfButtons[9][3].setBackground(Color.RED);
						   padFeedBack[9][3] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
						arrayOfButtons[9][3].setBackground(Color.GRAY);}});
						// ---- fifth and final column of the drawing Pad
						arrayOfButtons[0][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[0][4].setBackground(Color.RED);
							   padFeedBack[0][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[0][4].setBackground(Color.GRAY);}});
							arrayOfButtons[1][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[1][4].setBackground(Color.RED);
							   padFeedBack[1][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[1][4].setBackground(Color.GRAY);}});
							arrayOfButtons[2][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[2][4].setBackground(Color.RED);
							   padFeedBack[2][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[2][4].setBackground(Color.GRAY);}});
							arrayOfButtons[3][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[3][4].setBackground(Color.RED);
							   padFeedBack[3][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[3][4].setBackground(Color.GRAY);}});
							arrayOfButtons[4][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[4][4].setBackground(Color.RED);
							   padFeedBack[4][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[4][4].setBackground(Color.GRAY);}});
							arrayOfButtons[5][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[5][4].setBackground(Color.RED);
							   padFeedBack[5][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[5][4].setBackground(Color.GRAY);}});
							arrayOfButtons[6][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[6][4].setBackground(Color.RED);
							   padFeedBack[6][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[6][4].setBackground(Color.GRAY);}});
							arrayOfButtons[7][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[7][4].setBackground(Color.RED);
							   padFeedBack[7][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[7][4].setBackground(Color.GRAY);}});
							arrayOfButtons[8][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[8][4].setBackground(Color.RED);
							   padFeedBack[8][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[8][4].setBackground(Color.GRAY);}});
							arrayOfButtons[9][4].addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
							arrayOfButtons[9][4].setBackground(Color.RED);
							   padFeedBack[9][4] = true;} public void mouseExited(java.awt.event.MouseEvent evt) {
							arrayOfButtons[9][4].setBackground(Color.GRAY);}});
		
		
		//Adding all elements to the frame
		add(drawingPad, BorderLayout.NORTH);
		//To a separator between the pad and the utilities uncomment the line below
        //add(new JSeparator(), BorderLayout.CENTER);
        //Adding the utilities
        add(utilities, BorderLayout.SOUTH);
        
        //Cycle Counter
        cycleCounterTextBox.setEditable(false);
        cycleCounterTextBox.setText(cycleCounterMessage);
        add(cycleCounterTextBox, BorderLayout.WEST); //
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //this is a duplicate I think

        setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Uncomment the line below to set the contents size to be
		//at or above preferred sizes
		//pack();
		
		//Comment the line below if you need the UI to be re-sizable
        setResizable(false);
        setVisible(true);
	} // end of main constructor        
        @Override
    	public void actionPerformed(ActionEvent evt) {
    		try
    		{
    			//-----------------------------------
    			//             SUBMIT
    			//-----------------------------------
    			// For this block we will save the OUPUT at the first column
    			// then after convert padFeedBack value, migrate it to the next 50 next elements of
    			// the array finalCSVdata for that current row
    			if(evt.getSource() == submit)
    			{
    				enteredData = inputField.getText();
    				//The second dimension is zero cause this data entry is for OUTPUT only
    				finalCSVdata[currentCSVrow][0] =Integer.parseInt(enteredData);
    				if(currentCSVrow >= (numberOfEntries -1))
    				{
    					// This will force the program to overflow to avoid out of index fatal error
    					currentCSVrow = 0;
    				}
    				else
    				{
    					currentCSVrow = currentCSVrow + 1;
    				}
    				//Finally assign the INPUT values of padFeedBack to finalCSVdata
    				//CSVcounter starts with the value of one cause column zero is for the OUTPUT values
    				for(int i = 0, CSVcounter = 1; i < padV; i++)
    				{
    					for(int j = 0; j < padH; j++)
    					{
    						finalCSVdata[currentCSVrow][CSVcounter] = (padFeedBack[i][j] ? 1 : 0);
    						CSVcounter = CSVcounter +1;    							
    					}
    				}
    				//Reset all buttons to prepare them for the new cycle
    				for(int i = 0; i < padV; i++)
    				{
    					for(int j = 0; j < padH; j++)
    					{
    						arrayOfButtons[i][j].setBackground(Color.BLUE);
    						padFeedBack[i][j] = false;
    					}
    				}
    				 cycleCounterTextBox.setText("cycle " + currentCSVrow + " was submited!");
    			}
    			//-----------------------------------
    			//             DONE
    			//-----------------------------------
    			// After entering all ten values and having all of finalCSVdata populated
    			// create a comma separated file and save it to be fed to the
    			// neural network library
    			if(evt.getSource() == done)
    			{    				
    				userFilenameInput = JOptionPane.showInputDialog("Enter file name, or leave it empty for default ");
    				if(!(userFilenameInput.equals("")))
    				{
    					fileName = userFilenameInput + ".csv";
    				}
    				file = new File(fileName);
    				file.setWritable(true);
    	            file.setReadable(true);
    				neuralNetworkFeedback =new FileWriter(file);
    				mycsvPath = file.getAbsolutePath();
    				System.out.println(file.getAbsolutePath());
    				
    		        for(int i = 0; i < numberOfEntries; i++)
    		        {
    		        	for(int j = 0; j < (totalPadButtons+1); j++)
    		        	{
    		        		stringBuilderVar.append(Integer.toString(finalCSVdata[i][j]));
    		        		stringBuilderVar.append(',');
    		        	}
    		        	stringBuilderVar.append('\n');
    		        }
    		        
    		        neuralNetworkFeedback.write(stringBuilderVar.toString());
    		        neuralNetworkFeedback.flush();
    		        neuralNetworkFeedback.close();
    				// save file
    				// create a new one 
    		        inputField.setText("File Saved!");
    			}
    			//-----------------------------------
    			//             HELP
    			//-----------------------------------
    			if(evt.getSource() == help)
    			{
    				JOptionPane.showMessageDialog(help_menu, "Please use the pad to draw your number\n\n"
    						+ "Then enter it in the text field and press \"SUBMIT\"\n\nFinally when you're ready, "
    						+ "press \"DONE\" to save your csv file\n\nIf you need to erase your drawing press \"CLEAR\"\n\n"
    						+ "Press \"MY CSV\" to see where the csv file you just saved with \"DONE\" is located\n\n"
    						+ "Have fun :)");
    			}
    			//-----------------------------------
    			//             MY CSV
    			//-----------------------------------
    			if(evt.getSource() == csvPathButton)
    			{
    				if(mycsvPath.equals(""))
    				{
    					JOptionPane.showMessageDialog(path_menu, "Please first, finish your drawing, submit your number then press Done for the file to be saved");
    				}
    				else
    				{
    					JOptionPane.showMessageDialog(path_menu, mycsvPath);
    				}    				
    			}
    			//-----------------------------------
    			//             CLEAR
    			//-----------------------------------
    			if(evt.getSource() == clear)
    			{
    				//Reset the previous submitted entered number
    				finalCSVdata[currentCSVrow][0] = -1;
    				//Then reset pad buttons 
    				for(int i = 0; i < padV; i++)
    				{
    					for(int j = 0; j < padH; j++)
    					{
    						arrayOfButtons[i][j].setBackground(Color.GREEN);
    						arrayOfButtons[i][j].setBackground(Color.BLUE);
    						padFeedBack[i][j] = false;
    					}
    				}
    				//TimeUnit.SECONDS.sleep(2);
    			}
    		} //end of try block
    		catch(NumberFormatException | IOException myException)
    		{
    			// Displaying a friendly message asking the user to reenter their number
    			inputField.setText("Error Occured");
    		} // end of catch block    		
        } // end of actionPerformed block 

        
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Padtwopointo dataEntry = new Padtwopointo();
	} // end of main() block
} // end of PadPointTwoO class
