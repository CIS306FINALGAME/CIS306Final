/**
 * 			BALL CLASS THAT EXTENDS GAMEOBJECT
 * 
 * 	Purpose: To move the ball around the screen at a set velocity, allows the ball to change the velocity based on if
 * 			a collision occurs.
 * 
 * 
 * Attributes:		x and y velocity, set ball width and height for non argument consturctor.
 * 
 * Line 97: Starts the reset for when we die, quick if statement within the moveBall() 
 * 
 * @author Chris
 * 
 * 
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


public class Ball extends GameObject{
	
	public static final int ballWidth = 20;
	public static final int ballHeight = 20;
	
	private int xVelocity;
	private int yVelocity;
	private Color ballColor;

	private boolean lockedToPaddle;


	//default no argument constructor
		public Ball()
		{
			super();
			
			xPos =GamePanel.WIDTH/4;
			yPos = 440;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			ballColor = new Color(0,200,0);
			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = -3;
			
		//Set to true means that we haven't launched the ball
			lockedToPaddle = true;
			
		}
		
		//overloaded constructor
		public Ball(Image pImage)
		{
			super (pImage);
		

			yPos = GamePanel.HEIGHT - 100;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 2;
			yVelocity = -3;
			
		}
		
	//Paddle Left and Right Methods
			//IF the ball hasn't been launched yet
				// Keep moving the ball with the paddle until we have launched it
		public void ballOnPaddleRight(){
			setxPos(xPos + 30);
			
		}
		
		public void ballOnPaddleLetft(){
			setxPos(xPos - 30);
		}	

		/**
		 * Draw method, sets the color to what we predetermine
		 * Fill the oval, at the X,Y Pos and using the width and height
		 */
		public void draw(Graphics g)
		{
			g.setColor(ballColor);
			g.fillOval(xPos, yPos, ballWidth, ballHeight);
		}

		public void moveBall()
		{
			
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;

			if (getxPos()+ ballWidth>GamePanel.WIDTH || getxPos()<0 )
			{
				setxVelocity(-1* getyVelocity());
			}
			
		
			if(getyPos()+Ball.ballHeight >GamePanel.HEIGHT-100 || getyPos()<0)
			{
				setyVelocity(-1* getyVelocity());
			}
			// NEED THIS FOR LIFE COUNTS, ONCE WE DO COLLISIONS WE CAN ADD THIS BACK IN
			if(getyPos()+Ball.ballHeight > 480){
				setLockedToPaddle(true);
				xPos =GamePanel.WIDTH/4;		
				yPos = 440;						//	CHANGE ME WHEN WE START USING PICTURES
				xVelocity = 2;
				yVelocity = -3;	
			}	
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
		}	
		
		//CRASHED INTO SOMETHING 
		/**
		 * Methods set up to change our velocity depending on if we crashed on the x or y planes
		 * 
		 * Will call these methods from the gamePanel, when we check for logic and figure out the collision
		 */
		public void crashedXPos(){
			
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
			
			setxVelocity(-1* getxVelocity());
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
		}
		
		public void crashedYPos(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;			
			
			setyVelocity(-1* getyVelocity());
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
			
		}
		

		//Accessor and Mutator Methods
		public boolean isLockedToPaddle() {
			return lockedToPaddle;
		}

		public void setLockedToPaddle(boolean lockedToPaddle) {
			this.lockedToPaddle = lockedToPaddle;
		}
		
		int getxVelocity() {
			return xVelocity;
		}


		public void setxVelocity(int xVelocity) {
			this.xVelocity = xVelocity;
		}

		public int getyVelocity() {
			return yVelocity;
		}

		public void setyVelocity(int yVelocity) {
			this.yVelocity = yVelocity;
		}
}