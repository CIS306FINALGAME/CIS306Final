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
import java.util.Random;


public class Ball extends GameObject{
	
	public static final int ballWidth = 30;
	public static final int ballHeight = 30;
	
	private int xVelocity;
	private int yVelocity;
	private Color ballColor;
	

	//Lets See if this works
	private int currentX, currentY;


	private boolean lockedToPaddle;


	//default no argument constructor
		public Ball()
		{
			super();
			xPos = 0;

			yPos = 0;
			
			currentX =GamePanel.WIDTH/4;
			currentY = 430;
			
			ballColor = new Color(0,200,0);
			//generator = new Random();
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = 3;
			lockedToPaddle = false;
			
		}
		
		//overloaded constructor
		public Ball(Image pImage)
		{
			super (pImage);
			xPos = 50;

			yPos = GamePanel.HEIGHT - 100;

			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 2;
			yVelocity = 3;
			
		}
		
	
		public void draw(Graphics g)
		{
			g.setColor(ballColor);
			g.fillOval(currentX, currentY, ballWidth, ballHeight);
		}

		public void moveBall()
		{


			if (getCurrentX()+ ballWidth>GamePanel.WIDTH || getCurrentX()<0 )
			{
				setxVelocity(-1* getxVelocity());
			}
			
		
			if(getCurrentY()+Ball.ballHeight >GamePanel.HEIGHT-100 || getCurrentY()<0)
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
			
			
			currentX = currentX + xVelocity;
			currentY = currentY + yVelocity;
			
		}	

		//Accessor and Mutator Methods
		
		
		public int getxVelocity() {
			return xVelocity;
		}

		public int getCurrentX() {
			return currentX;
		}

		public void setCurrentX(int currentX) {
			this.currentX = currentX;
		}
		
		public int getCurrentY() {
			return currentY;
		}
		
		public void setCurrentY(int currentY) {
			this.currentY = currentY;
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