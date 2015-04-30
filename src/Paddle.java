/**
 * 		PADDLE CLASS
 * 
 * 	Has a private int for lives left for the player
 * 			Move Left, Move Right methods	
 * 					-Moves our collision rectangle as the player is moved across the screen
 * Over loaded constructor used for the images
 * 
 * has getter/setter for the lives so Game Manager can interact with this when the ball is beyond the reach of the paddle object
 * 
 * @author Chris
 * 
 * 
 */

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Paddle extends GameObject{

	
	//default no argument constructor
	public Paddle()
	{
		super();
		
		width = 100;
		height = 10;
		
		xPos = (GamePanel.WIDTH/2) - (width/2);
		yPos = 460;
		
		collisionRect = new Rectangle(xPos, yPos, width, height);
	}
	
	//overloaded constructor
	public Paddle(String pFileName)
	{
		super (pFileName);
		width = 100;
		height = 10;
		
		xPos = (GamePanel.WIDTH/2) - (width/2);
		yPos = 460;
		
		collisionRect = new Rectangle(xPos, yPos, width, height);
		
		
	}
	
	
	//Called from the Game Manager when the player presses the left arrow key
	public void moveLeft()
	{
		if(getxPos() > 0){
			xPos-=25;	
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
		}
	}
	//Called from the Game Manger when the player presses the right arrow key
	public void moveRight()
	{
		if(getxPos() + width < GamePanel.WIDTH ){
		xPos+=25;

		collisionRect.x = this.xPos;
		collisionRect.y = this.yPos;
		}
	}
	
	// Called to draw our paddle on the GamePanel
	public void draw (Graphics g) {
	// TODO Auto-generated method stub
		   
		g.drawImage(getObjectPic(), xPos, yPos, null);
		
	}	
}
