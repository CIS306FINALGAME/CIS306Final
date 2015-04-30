/**
 * 			BALL CLASS THAT EXTENDS GAMEOBJECT
 * 
 * 	Purpose: To move the ball around the screen at a set velocity, allows the ball to change the velocity based on if
 * 			a collision occurs.
 * 
 * 
 * Attributes:		x and y velocity, set ball width and height for non argument consturctor.
 * 
 *  
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
	
	public static final int ballWidth = 15;
	public static final int ballHeight = 15;
		
	private int xVelocity;
	private int yVelocity;
	private Color ballColor;

	private boolean lockedToPaddle;

	//default no argument constructor
		public Ball()
		{
			super();
			
			xPos = (GamePanel.WIDTH/2) - (ballWidth/2);
			yPos = 445;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			ballColor = new Color(150,150,250);
			
			xVelocity = 1;
			yVelocity = -1;
			
		//Set to true means that we haven't launched the ball
			lockedToPaddle = true;
			
		}
		
		//overloaded constructor
		public Ball(String pFileName)
		{
			super (pFileName);
		
			xPos = (GamePanel.WIDTH/2) - (ballWidth/2);
			yPos = 445;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			ballColor = new Color(150,150,250);
			
			xVelocity = 1;
			yVelocity = -1;
			
		//Set to true means that we haven't launched the ball
			lockedToPaddle = true;
			
		}
		
		/**Paddle Left and Right Methods
		* IF the ball hasn't been launched yet
		*
		* Keep moving the ball with the paddle until we have launched it
		* @author Chris
		* 
		* Update -
		* Only move ball if the movement won't move it out of bounds on the paddle
		* 
		*/
		public void ballOnPaddleRight(){
			
			boolean outOfBounds = false;
			
			if(xPos + 25 + ballWidth > GamePanel.WIDTH)
			{
				outOfBounds = true;
			}
				
			if (outOfBounds == false) {
				setxPos(xPos + 25);
			}
				
		}
		
		public void ballOnPaddleLetft(){
			
			boolean outOfBounds = false;
			
			if(xPos - 25 < 0)
			{
				outOfBounds = true;
			}
				
			if (outOfBounds == false) {
				setxPos(xPos - 25);
			}
		}	

		/**
		 * Draw method, sets the color to what we predetermine
		 * Fill the oval, at the X,Y Pos and using the width and height
		 * @author Chris
		 */
		public void draw(Graphics g)
		{			
			g.drawImage(getObjectPic(), xPos, yPos, null);
		}

		/**
		 * Move Ball Function
		 * 		Is Called from GamePanel
		 * 		Moves the collision Rectangle with the ball
		 * 		Keeps moving the X and Y Pos for each Velocity
		 */
		public void moveBall()
		{
			//Set the CollisionRectangle x,y to the position of the ball
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
		
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
		}	
		
		/**
		 * Reset the Ball Location after it dies
		 * Gets called from the GamePanel.
		 * @author Chris
		 */
		public void ballReset(int resetX){
			setLockedToPaddle(true);
			xPos = resetX;		
			yPos = 445;						//	CHANGE ME WHEN WE START USING PICTURES
			xVelocity = 1;
			yVelocity = -1;	
		}
		
		//CRASHED INTO SOMETHING 
		/**
		 * Methods set up to change our velocity depending on if we crashed on the x or y planes
		 * 
		 * Will call these methods from the gamePanel, when we check for logic and figure out the collision
		 * @author Chris
		 */
		public void crashedBlock(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
			
			//int tempVelocity = 1 + generator.nextInt(5);
			
			setyVelocity(-1*getyVelocity());

			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
		}
		
		/**
		 * Simple method, that will get called from Game Panel On Collision with the Player
		 * 	Calls a Crashed X and Y Function
		 */
		public void crashedPaddle(){
				crashedYPos();
				crashedXPos();
		}
		/**
		 * Crashed X Pos Function
		 * 			Sets the collision rectangle to current x,y position
		 * 			change our velocity
		 * 			Change x,y positions
		 */
		private void crashedXPos(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
			setxVelocity(1*getxVelocity());
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
		}
		
		/**
		 * Crashed Y Pos Function
		 * 			Sets the collision rectangle to current x,y position
		 * 			change our velocity
		 * 			Change x,y positions
		 */
		private void crashedYPos(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;			
			
			setyVelocity(-1*getyVelocity());
			
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