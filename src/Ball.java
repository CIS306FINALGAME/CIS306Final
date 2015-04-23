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
			collisionRect = new Rectangle(xPos, yPos, 0, 0);

			
			ballColor = new Color(0,200,0);
			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = 3;
			
		//Set to true means that we haven't launched the ball
			lockedToPaddle = true;
			
		}
		
		//overloaded constructor
		public Ball(Image pImage)
		{
			super (pImage);
		

			yPos = GamePanel.HEIGHT - 100;
			
			collisionRect = new Rectangle(xPos, yPos, 0, 0);
			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 2;
			yVelocity = 3;
			
		}
		
	
		public void ballOnPaddleRight(){
			setxPos(xPos + 30);
			
		}
		
		public void ballOnPaddleLetft(){
			setxPos(xPos - 30);
		}
		
		public boolean isLockedToPaddle() {
			return lockedToPaddle;
		}

		public void setLockedToPaddle(boolean lockedToPaddle) {
			this.lockedToPaddle = lockedToPaddle;
		}

		public void draw(Graphics g)
		{
			g.setColor(ballColor);
			g.fillOval(xPos, yPos, ballWidth, ballHeight);
			g.drawRect(xPos, yPos, ballWidth, ballHeight);
		}

		public void moveBall()
		{


			if (getxPos()+ ballWidth>GamePanel.WIDTH || getxPos()<0 )
			{
				setxVelocity(-1* getyVelocity());
			}
			
		
			if(getyPos()+Ball.ballHeight >GamePanel.HEIGHT-100 || getyPos()<0)
			{
				setyVelocity(-1* getyVelocity());
			}
	
			// NEED THIS FOR LIFE COUNTS, ONCE WE DO COLLISIONS WE CAN ADD THIS BACK IN
//			if(getCurrentY() + Ball.ballHeight < 460){
//				currentX =GamePanel.WIDTH/4;
//				currentY = 430;
//				xVelocity = 1;
//				yVelocity = 3;	
//			}
			
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
		}	

		//Accessor and Mutator Methods

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