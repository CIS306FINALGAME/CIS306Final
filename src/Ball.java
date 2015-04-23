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
import java.util.Random;


public class Ball extends GameObject{
	
	public static final int ballWidth = 10;
	public static final int ballHeight = 10;
	private int xVelocity;
	private int yVelocity;
	private Color ballColor;
	private Random generator;
	
	private boolean lockedToPaddle;

	//default no argument constructor
		public Ball()
		{
			super();
			xPos = 0;

			yPos = 12;
			
			ballColor = new Color(0,200,0);
			generator = new Random();
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = 3;
			//xVelocity = generator.nextInt(10);
			//yVelocity = generator.nextInt(10);
			lockedToPaddle = false;
			
		}
		
		//overloaded constructor
		public Ball(Image pImage)
		{
			super (pImage);
			xPos = 0;

			yPos = GamePanel.HEIGHT - 100;

			
			// Set a constant velocity of  1:3 to create a nice steep angle
			xVelocity = 1;
			yVelocity = 3;
			
		}
	
		//Movement for when the ball is moving around the screen
		//Always called to keep collision rectangle on track tied with the velocity
		/* public void move()
		{
			xPos+=4;
			yPos+=4;
			this.getCollisionRect().x=xPos;
			this.getCollisionRect().y=yPos;
			this.getCollisionRect().height = getHeight();
			this.getCollisionRect().width = this.getWidth();
		}*/
		
	
		public void draw(Graphics g)
		{
			g.setColor(ballColor);
			g.drawOval(xPos, yPos, ballWidth, ballHeight);
		}

		public void moveBall()
		{
			if (getxPos()+ ballWidth > 600)
			{
				setxVelocity(-1* getxPos());
			}
			
		
			if(getyPos()+ Ball.ballHeight > 440)
			{
				setyVelocity(-1* getyPos());
			}
			xPos = xPos + xVelocity;
			yPos = yPos + yVelocity;
			
//			this.getCollisionRect().x=xPos;
//			this.getCollisionRect().y=yPos;
//			this.getCollisionRect().height = getHeight();
//			this.getCollisionRect().width = this.getWidth();
//			
//			xPos = xPos + xVelocity;
//			yPos = yPos + yVelocity;
//			
//			wallCrash();
			
		}

		// Check and see if the we crashed into a wall of the panel
			// will look and see if it was on the top/bottom or left/right side
				// manipulate the velocity for which one
		private void wallCrash(){
			if (getxPos()+ ballWidth>GamePanel.WIDTH)
			{
				setxVelocity(-1* getxPos());
			}
			
		
			if(getyPos()+Ball.ballHeight >GamePanel.HEIGHT)
			{
				setyVelocity(-1* getyPos());
			}
			
		}
//
//		// Check and see if the we crashed into a block
//			// will look and see if it was on the x or y side
//				// manipulate the velocity for which one
//		private void blockCrash(){
//			
//			if (getxPos()+ ballWidth > Block.WIDTH)
//			{
//				setxVelocity(-1* getxPos());
//			}
//			
//		
//			if(getyPos()+Ball.ballHeight >Block.HEIGHT)
//			{
//				setyVelocity(-1* getyPos());
//			}
//			
//		}
		
		// Check and see if we crashed into the paddle
			// will look and see if it was on the x or y side
				// manipulate the velocity for which one
//		private void paddleCrash(){
//			
//			if (getxPos()+ ballWidth > Paddle.WIDTH)
//			{
//				setxVelocity(-1* getxPos());
//			}
//			
//		
//			if(getyPos()+Ball.ballHeight >Paddle.HEIGHT)
//			{
//				setyVelocity(-1* getyPos());
//			}
//			
//		}
		
		
//Accessor and Mutator Methods
		
		
		public int getxVelocity() {
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