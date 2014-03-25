// Assignment #: 7
//         Name: Ryan Schachte
//    StudentID: 1206017757
//      Lecture: 1
//      Class Time: T/Th @ 3:00PM

//Description: Takes in the parameters from the WholePanel that constructs the rectangle after it is drawn. Once it is drawn, it gets returned 
//into the whole panel class so that the data can be stored into the array and printed back out using a for-loop


import java.awt.Color;
import java.awt.Graphics;


public class Rect extends WholePanel {


private int x;
private int y;
private Color color;
private int Width;
private int Height;

public Rect(int x1, int y1, int width, int height, Color color)//Take in parameters to build and store rectangle object

{
	/*
	 * The following are the new dimensions passed in through the constructor of this class that reassign the dimensions of the rectangle
	 * and then builds the rectangle to pass back into the array list
	 */
	this.x = x1; 
	this.y = y1;
	this.Width = width;
	this.Height = height;
	this.color = color;
}

	public void draw(Graphics page) 
	{
	page.setColor(color);	//Set color of rectangle for array list
	
	page.fillRect(x, y, Width, Height); //Fill rectangle based on dimensions

	}

	
	

}
