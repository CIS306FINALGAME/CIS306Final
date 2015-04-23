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


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class Paddle extends GameObject{

	private int lives = 3;


	
	//default no argument constructor
	public Paddle()
	{
		super();
		xPos = GamePanel.WIDTH/4;
		yPos = 460;
		collisionRect = new Rectangle(xPos, yPos, xPos+100, yPos+10);
	
		
	}
	
	//overloaded constructor
	public Paddle(Image pImage)
	{
		super (pImage);
		xPos = GamePanel.WIDTH/4;
		yPos = 460;
		collisionRect = new Rectangle(xPos, yPos, xPos+100, yPos+10);
		
		
	}
	
	
	//Called from the Game Manager when the player presses the left arrow key
	public void moveLeft()
	{
		if(getxPos() > 0){
			xPos-=30;	
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
		}

		

	}
	//Called from the Game Manger when the player presses the right arrow key
	public void moveRight()
	{
		if(getxPos() + 110 < GamePanel.WIDTH ){
		xPos+=30;

		collisionRect.x = this.xPos;
		collisionRect.y = this.yPos;
		}

	}
		
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
   // draw rectangle
		   
public void draw (Graphics g) {
	// TODO Auto-generated method stub
	   {  
		   
		   g.fillRect( xPos, yPos, 100, 10);
		   g.setColor(Color.RED);
		   g.drawRect(collisionRect.x, collisionRect.y,100, 10);
		
	   } // end method draw
	
}	
}
