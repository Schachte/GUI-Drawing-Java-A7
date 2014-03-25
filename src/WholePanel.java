// Assignment #: 7
//         Name: Ryan Schachte
//    StudentID: 1206017757
//      Lecture: 1
//      Class Time: T/Th @ 3:00PM
//  Description: The whole panel creates components for the whole panel
//  of this applet. It also contains CanvasPanel, ButtonListener, ColorListener,
//  and PointListner classes.

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WholePanel extends JPanel
{
   private Color currentColor;
   private CanvasPanel canvas;
   private JPanel primary, leftPanel;
   private JButton erase, undo, setOp;
   private ArrayList <Rect> rectList;
   private JRadioButton[] colorRButtons;
   private Color[] colors;
   private boolean mouseDragged = false;
   private int startX;
   private int startY;
   private int rectWidth;
   private int rectHeight;
   private int xLoc;
   private int yLoc;   
   private Color decodeHexCode;

   private int colorOpAmt = 50;
   
   int numTimesEntered;
   
   int currentX;
   int currentY;   
   JLabel numRects;
   
   boolean releaseMouse = false;
   
   private Color defaultBg = Color.white;
   private JButton changeBackground;

   //Constructor to instantiate components
   public WholePanel()
   
   {
	   
	   numRects = new JLabel("Rectangle Count: ");
	   numRects.setHorizontalAlignment(SwingConstants.CENTER);
	  
	  //default color to draw rectangles is black
	  //store 5 colors in an array
      currentColor = Color.black;
      colors = new Color[5];
      
      colors[0] = Color.black;
      colors[1] = Color.red;
      colors[2] = Color.blue;
      colors[3] = Color.green;
      colors[4] = Color.orange;
      
	  rectList = new ArrayList<Rect>(); //Array list to store all new rectangle objects 

	  setLayout(new BorderLayout()); //Layout
	  leftPanel = new JPanel(new BorderLayout()); //Layout
	  add(leftPanel);//Add left panel to JFrame
	  leftPanel.setPreferredSize(new Dimension(70,400));//Panel dimension reset
	  
	  //Buttons for erase and undo
	  erase = new JButton("Erase");
	  
	  erase.addActionListener(new ButtonListener()); //Add action to button
	  
	  //Button to undo latest rectangle added to array list 
	  undo = new JButton("Undo");
	  
	  undo.addActionListener(new ButtonListener()); //Add action to button
	  
	  setOp = new JButton("Opacity Change");
	  
	  setOp.addActionListener(new ButtonListener());
	  
	  changeBackground = new JButton("Background Change");
	  
	  changeBackground.addActionListener(new ButtonListener());


      //create radio buttons for 5 colors
      //black will be chosen by default
	  colorRButtons = new JRadioButton[5];
	  colorRButtons[0] = new JRadioButton("black", true);
	  colorRButtons[1] = new JRadioButton("red", true);
	  colorRButtons[2] = new JRadioButton("blue", true);
	  colorRButtons[3] = new JRadioButton("green", true);
	  colorRButtons[4] = new JRadioButton("orange", true);
	  
	  for (int i = 0 ; i < 5 ; i ++)
	  {
		  colorRButtons[i].setHorizontalAlignment(JRadioButton.CENTER);
	  }


      //group radio buttons so that when one is selected,
      //others will be unselected.
	  ButtonGroup group = new ButtonGroup();
	  for (int i=0; i<colorRButtons.length; i++)
	    group.add(colorRButtons[i]);

      //add ColorListener to radio buttons
      ColorListener listener = new ColorListener();
      for (int i=0; i<colorRButtons.length; i++)
        colorRButtons[i].addActionListener(listener);

      //primary panel contains all radiobuttons
      primary = new JPanel(new GridLayout(9,1));
      for (int i=0; i<colorRButtons.length; i++)
      {
    	  primary.add(colorRButtons[i]);
      }
      
      

      /*
       * Add all to main JFrame (visible GUI)
       */
      leftPanel.add(primary);
      primary.add(undo);
      primary.add(erase);
      primary.add(setOp);
      primary.add(changeBackground);

      //canvas panel is where rectangles will be drawn, thus
      //it will be listening to a mouse.
      canvas = new CanvasPanel(); //Drawing panel
      canvas.setBackground(defaultBg); //Background of drawing panel
      canvas.addMouseListener(new PointListener()); //Add user action when interacting with panel
      canvas.addMouseMotionListener(new PointListener()); //Add user action when interacting with panel
      
      JPanel rightPane = new JPanel(new BorderLayout()); //Layout to adjust dimensions
      rightPane.setPreferredSize(new Dimension(350,400)); //New dimension
      rightPane.add(canvas);//Add to JFrame
      rightPane.add(numRects, BorderLayout.NORTH);


      JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPane); //Organize panels

	  
      add(sp); //Add horizonal split-pane
    }

   //ButtonListener defined actions to take in case "Create",
   //"Undo", or "Erase" is chosed.

   private class ButtonListener implements ActionListener
    {
	   
	   
      public void actionPerformed (ActionEvent event)
      {

    	 if ( event.getSource() == erase && rectList.size()!=0) //If erase is hit then....
    	 {
    		 rectList.clear(); //Clear Arraylist storing all the rectangle...
    		 
    		 canvas.repaint(); //Repaint the canvas...
    		 
    		 numRects.setText("Rectangle Count: " + rectList.size());

    	 }
    	 else if (event.getSource() == erase && rectList.size() == 0)
    	 {
    		 JOptionPane.showMessageDialog(null, "Nothing on screen to erase!", "CSE 205 Rectangle", JOptionPane.DEFAULT_OPTION, null); //Handle exception by error message if no more to erase
    	 }
    	 
    	 else if (event.getSource() == undo) //If undo is hit..
    	 {    		 
    		if (rectList.size() != 0) //Only perform this if the list isn't empty
    		{
    			rectList.remove(rectList.size()-1); //Remove latest entry (ie, largest number in array list storing rectangle object)
       		    canvas.repaint();// Update canvas with repaint method 
       		    
       		    numRects.setText("Rectangle Count: " + rectList.size());

    		}
    		
    		else
    		{
    			JOptionPane.showMessageDialog(null, "There are no more rectangles to undo!", "CSE 205 Rectangle", JOptionPane.DEFAULT_OPTION, null); //Handle exception by error message if no more to undo
    		}

    	 }
    	 
 		else if (event.getSource() == setOp)
 		{
 			colorOpAmt =  Integer.parseInt(JOptionPane.showInputDialog("Enter Opacity Amount: ", 255));
 		}
    	 
 		else if (event.getSource() == changeBackground)
 		{
 			
 			String userInputColor = JOptionPane.showInputDialog("Enter Color to Change");
 			
 			if (userInputColor.equalsIgnoreCase("red"))
 			{
 				canvas.setBackground(Color.red);
 				canvas.repaint();
 			}
 			else if(userInputColor.equalsIgnoreCase("blue"))
 			{
 				canvas.setBackground(Color.blue);
 				canvas.repaint();
 			}
 			else if(userInputColor.equalsIgnoreCase("orange"))
 			{
 				canvas.setBackground(Color.orange);
 				canvas.repaint();
 			}
 			else if(userInputColor.equalsIgnoreCase("green"))
 			{
 				canvas.setBackground(Color.green);
 				canvas.repaint();
 			}
 			else if(userInputColor.equalsIgnoreCase("black"))
 			{
 				canvas.setBackground(Color.black);
 				canvas.repaint();
 			}
 			
 			else if (userInputColor.equalsIgnoreCase("white"))
 			{
 				canvas.setBackground(Color.white);
 				canvas.repaint();
 			}
 			
 			else if (userInputColor.equalsIgnoreCase("pink"))
 			{
 				canvas.setBackground(Color.pink);
 				canvas.repaint();
 			}
 			else
 			{
 				JOptionPane.showMessageDialog(null, "Blake Is Gay");
 			}
 			
 		}

      }
   } // end of ButtonListener

   // listener class to set the color chosen by a user using
   // the radio buttons.
   private class ColorListener implements ActionListener
    {
		public void actionPerformed(ActionEvent event)
		 {

			//Simple for-loop to set colors to corresponding value of array of colors. (USER INPUT Required)
			for (int i = 0; i < 5; i++)
			{
				if (event.getSource() == colorRButtons[i])
		             currentColor = colors[i];
			}

	     }
    }


 //CanvasPanel is the panel where rectangles will be drawn
public  class CanvasPanel extends JPanel
  {


	//this method draws all rectangles specified by a user
	 public void paintComponent(Graphics page)
      {
   	   super.paintComponent(page);

   	   //draw all rectangles

 		  for (int i=0; i < rectList.size(); i++)
	   	    {
		      ((Rect) rectList.get(i)).draw(page);
		    }
 		  
          if (releaseMouse)
          {
        	  page.setColor(Color.black);
        	  page.drawString("Size:" + rectWidth + "px X " + rectHeight + "px" , startX, startY);
        	  canvas.repaint();
          }
 		  
          //draw an outline of the rectangle that is currently being drawn.
          if (mouseDragged == true)

           {
			//Assume that a user will move a mouse only to right and down from
			//the first point that was pushed.

     				/*
     				 * COLOR SELECTION EXCEPTION HANDLING
     				*/ 
        	  
        	  	if (currentColor == Color.black)
        	  		decodeHexCode = new Color(0,0,0, colorOpAmt);
        	  	else if (currentColor == Color.blue)
        	  		decodeHexCode = new Color(0,0,255, colorOpAmt);
        	  	else if (currentColor == Color.orange)
        	  		decodeHexCode = new Color(255,145,0, colorOpAmt);
        	  	else if (currentColor == Color.green)
        	  		decodeHexCode = new Color(0,255,0, colorOpAmt);
        	  	else if (currentColor == Color.red)
        	  		decodeHexCode = new Color(255,0,0, colorOpAmt);
        	  	
        	  	
        	  		page.setColor(currentColor); //Setrectangle color based on user choice (JRadioButton)
        	  		page.drawRect(startX, startY, rectWidth, rectHeight); //Draw rectangle using updating dimensions in mouse listener
         			
         			page.setColor(decodeHexCode);
         			page.fillRect(startX, startY, rectWidth, rectHeight);
         			
         			page.setColor(Color.MAGENTA);
         			page.setFont(new Font("Helvetica", Font.PLAIN, 12)); 
         			page.drawString("DRAWING RECTANGLE (" + currentX + ", " + currentY + ")", currentX-120, currentY+30);
         			

     			}

          

          
	  }
	 
	 

    } //end of CanvasPanel class

   // listener class that listens to the mouse
   public class PointListener implements MouseListener, MouseMotionListener
    {
	 //in case that a user presses using a mouse,
	 //record the point where it was pressed.
     public void mousePressed (MouseEvent event)
      {
		//after "create" button is pushed.

    	 startX = event.getX(); //Beginning x-coordinate (top-left)
    	 startY = event.getY(); //Beginning y-coordinate (top-left)
    	 
      }

     //mouseReleased method takes the point where a mouse is released,
     //using the point and the pressed point to create a rectangle,
     //add it to the ArrayList "rectList", and call paintComponent method.
     public void mouseReleased (MouseEvent event)
      {
    	 
    	 releaseMouse = true;
    	 mouseDragged = false; //change boolean to false so it can add ending dimensions to array list.
    	 canvas.repaint(); //Repaint canvas
    	 rectList.add(new Rect(startX, startY, rectWidth, rectHeight, currentColor));  //Store rectangle of new dimensions into array list 
    	 numRects.setText("Rectangle Count: " + rectList.size());

	  }

     //mouseDragged method takes the point where a mouse is dragged
     //and call paintComponent method
	 public void mouseDragged(MouseEvent event)
	  { 
		 
		 releaseMouse = false;
		 
		 rectWidth = event.getX() - startX; //vWidth = current point - initial point X
		 rectHeight = event.getY() - startY; //Height = current point - initial point Y
		 
 		 mouseDragged = true; //Boolean true, so redraw rectangle on drag. 
 
 		 xLoc = event.getXOnScreen();
 		 yLoc = event.getYOnScreen();
 		 
 		 currentX = event.getX();
 		 currentY = event.getY();
 		 
		 canvas.repaint(); //Repaint to update new coordinates
	  }
	 
     public void mouseClicked (MouseEvent event) {}
     public void mouseEntered (MouseEvent event) {}
     public void mouseExited (MouseEvent event) {}
     public void mouseMoved(MouseEvent event)  {}
      
     

    } // end of PointListener

} // end of Whole Panel Class