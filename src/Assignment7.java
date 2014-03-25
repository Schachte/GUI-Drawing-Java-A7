// Assignment #: 7
//         Name: Ryan Schachte
//    StudentID: 1206017757
//      Lecture: 1
//      Class Time: T/Th @ 3:00PM
//  Description: The Assignment 7 creates a WholePanel that is
//  an extension of JPanel, add it to its content, and set
//  a size for the applet.

//Description: Creates the java applet that houses the panels and information that is transferred from the wholepanel class.


import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Assignment7 extends JApplet
{
	

public void init()
  {
	 UIn(); //Creating default user interface (set look and feel)
	 
    // create a WholePanel object and add it to the applet
    WholePanel wholePanel = new WholePanel();
    getContentPane().add(wholePanel);

    //set applet size to 400 X 400
    setSize (400, 400);
    
    
    
   
  }
 
 public void UIn() //Make default interface style.
 {
	 try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
 }
}






