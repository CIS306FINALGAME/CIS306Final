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
	
	public static final int ballWidth = 20;
	public static final int ballHeight = 20;
	
	private int xVelocity;
	private int yVelocity;
	private Color ballColor;

	private boolean lockedToPaddle;

	private Random generator;

	//default no argument constructor
		public Ball()
		{
			super();
			
			generator = new Random(10);
			
			xPos = GamePanel.WIDTH/2;
			yPos = 440;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			ballColor = new Color(0,200,0);
			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = -1;
			
		//Set to true means that we haven't launched the ball
			lockedToPaddle = true;
			
		}
		
		//overloaded constructor
		public Ball(Image pImage)
		{
			super (pImage);
		

			yPos = GamePanel.HEIGHT - 100;
			
			collisionRect = new Rectangle(xPos, yPos, ballWidth, ballHeight);
			
			// Set a constant velocity of  2:-3 to create a nice steep angle
			xVelocity = 12;
			yVelocity = -18;
			
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
			g.setColor(ballColor);
			g.fillOval(xPos, yPos, ballWidth, ballHeight);
			
			
			g.setColor(Color.RED);
			g.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
		}

		public void moveBall()
		{
			//Set the CollisionRectangle x,y to the postition of the ball
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;

			//Always check to make sure we don't hit the edges of the panel
			//If we do, change the x, or y velocity depending of which panel piece we hit
			if (getxPos()+ ballWidth>GamePanel.WIDTH-10 || getxPos()<3 )
			{
				setxVelocity(-1* getyVelocity());
			}
			if(getyPos()+Ball.ballHeight >GamePanel.HEIGHT-110 || getyPos()<3)
			{
				setyVelocity(-1* getyVelocity());
			}
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
		}

		/**	Checks to see if the ball is crashing into the edges of the panel
		 * 
		 * @author Chris
		 */
		private void panelCrashes() {
			//Always check to make sure we don't hit the edges of the panel
			//If we do, change the x, or y velocity depending of which panel piece we hit
			if (getxPos()+ ballWidth>GamePanel.WIDTH-10 || getxPos()<3 )
			{
				setxVelocity(-1* getyVelocity());
			}
			if(getyPos()+Ball.ballHeight >GamePanel.HEIGHT-110 || getyPos()<3)
			{
				setyVelocity(-1* getyVelocity());
			}
		}	
		
		/**
		 * Reset the Ball Location after it dies
		 * Gets called from the GamePanel.
		 * @author Chris
		 */
		public void ballReset(int resetX){
			setLockedToPaddle(true);
			xPos = resetX;		
			yPos = 440;						//	CHANGE ME WHEN WE START USING PICTURES
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
			//setxVelocity(-1* getxVelocity());
			
			
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
		}
		public void crashedXPos(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;
			
			//int tempVelocity = 1 + generator.nextInt(6);
			setxVelocity(-1*getxVelocity());
			
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
		}
		
		public void crashedYPos(){
			collisionRect.x = this.xPos;
			collisionRect.y = this.yPos;			
			
			//int tempVelocity = 1 + generator.nextInt(18);
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