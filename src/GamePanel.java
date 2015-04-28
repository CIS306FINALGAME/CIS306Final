import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.omg.CORBA.PRIVATE_MEMBER;

// All kinds of stuff going on in this
			// Have a rectangle able to move back and forth across the screen for every time you press an arrow key

// A ball appears in a location but does not start moving yet
			
		//NEED TO DO
			// =Get the ball to move
			// Set up Collision Nonsense
public class GamePanel extends JPanel implements Runnable{
	
	protected final static int  WIDTH = 1000;
	protected final static int  HEIGHT = 600;
	
	private Paddle player;
	private Ball ball;

	ArrayList<Block> blocks;
	
	
	private boolean run;
	

	public GamePanel() 
	{
		blocks = new ArrayList<Block>();
		setupBlocks();
		ball = new Ball();
		player = new Paddle();

		
		this.setSize(WIDTH,HEIGHT);

	
		run = false;
		

		this.setBackground(Color.BLACK);		
		this.addKeyListener(new keysPressed());
		this.setVisible(true);
		this.setFocusable(true);	
		
	}
	
	
	
	
	
	
	
	
	
	@Override
	public void run() 
	{	
		runGame();
	}
	
	public void startGame()
	{
		run = true;
	}
	
	public void pauseGame()
	{
		run = false;
		this.setFocusable(true);
	}
	
	public void exitGame()
	{
		System.exit(ABORT);
	}
	
	
	
	
	
	
	
	//********MAIN THREAD LOOP********
	
	private void runGame()
	{
		
		while(run) 
		{
			
			checkCollisions();
			removeObjects();
			moveBall();
			
			repaint();
			
			try{
				 Thread.sleep(10);
			 }
			 catch (InterruptedException exception){
				 System.out.println("Thread exited due to interruption");
			 }
			
		}

	}
	
	//********END MAIN THREAD LOOP********
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Ball Launched Method
	 * Is called when the "player" hits the space bar
	 * 			Set our Bool Value to false
	 * 			Calls the moveBall Function
	 * 
	 * @Author Chris
	 */
	public void ballLaunched(){
		ball.setLockedToPaddle(false);
		moveBall();
		
	}
	
	//Checks for collisions across all gameObjects
		//Blocks
		//Player Paddle
	private void checkCollisions()
	{
		for (Block block : blocks) {
			
			
			if(ball.collisionRect.intersects(block.collisionRect))
			{
				block.setBroken(true);
				
				//Need if statments to adjust based on the intersections points
				
				if(	ball.collisionRect.x + ball.collisionRect.width >= block.collisionRect.x || 
					ball.collisionRect.x <= block.collisionRect.x + block.collisionRect.width)
				{
					ball.crashedXPos();
				}
				
				if(	ball.collisionRect.y + ball.collisionRect.height >= block.collisionRect.y ||
					ball.collisionRect.y <= block.collisionRect.y + block.collisionRect.height)
				{
					ball.crashedYPos();
				}
			}
		}
		// CEG: For Paddle Collisions, We are in business now boys
		if(ball.collisionRect.intersects(player.collisionRect)){
			//ball.crashedPaddle();
//			if(	ball.collisionRect.x + ball.collisionRect.width >= player.collisionRect.x || 
//					ball.collisionRect.x <= player.collisionRect.x + player.collisionRect.width)
//			{
//				ball.crashedXPos();
//			}
//				
//			if(	ball.collisionRect.y + ball.collisionRect.height >= player.collisionRect.y ||
//				ball.collisionRect.y <= player.collisionRect.y + player.collisionRect.height)
//			{
//				ball.crashedYPos();
//			}
			
			ball.crashedYPos();
		}
		
		//If ball moves to any constaint of panel, crash with panel and bounce
		if(	ball.xPos + ball.getWidth() >= GamePanel.WIDTH || 
			ball.xPos <= 0)
		{
			ball.crashedXPos();
		}
		
		if(	ball.yPos + ball.getHeight() >= GamePanel.HEIGHT ||
			ball.yPos <= 0)
		{
			ball.crashedYPos();
		}
		
	}

	private void removeObjects()
	{
		
//		//CEG : Possible fix for index out of bounds error
			//Went for a simple for loop approach, just so we can grab the index we are sitting on
		
		for(int i=0; i< blocks.size(); i++){
			
			if(blocks.get(i).isBroken()){

				blocks.remove(blocks.get(i));
			}
			
		}
		
	}
	
	/**
	 * Move ball method,
	 * 			-Checks to see if the player dies or not
	 * 			-If they do, call the ballReset Function, and pass in the postion of the paddle, so we put the ball back on it
	 * Subtract one life from the player
	 * 
	 * Calls the move ball Function from the ball class
	 * 
	 * @author Chris
	 */	
	private void moveBall()
	{
		
		//Only if the ball has been launched do we allow the ball to keep moving
		if(ball.isLockedToPaddle() == false)
		{
			if(ball.getyPos()+Ball.ballHeight > 480)
			{
				ball.ballReset(player.getxPos());
			}
			
			ball.moveBall();
		}
		
		repaint();
		
	}
		
	private void setupBlocks()
	{
		
		final int blockWidth = 75;
		final int blockHeight = 25;
		final int offset = 40;
		
		
		for(int i = 0; i < 12; i++)
			for(int j = 0; j < 5; j++)
			{
				blocks.add(new Block( i * (blockWidth + 1) + offset, j * (blockHeight + 1) + offset));
			}
	}
	
	
	

	public void paintComponent(Graphics g){
		//Call Super paint function
		super.paintComponent(g);
		//Make sure focus is set to panel so we can move the paddle
		this.requestFocus();

		//paint the blocks on the screen
		for (Block block : blocks) {
				//If block is not broken draw it otherwise do not draw it
				if(!block.isBroken())
				{
					block.draw(g);
				}		
		}
		//Set color to draw the paddle
		g.setColor(Color.WHITE);   
		//Draw our player and ball
		player.draw(g);
		ball.draw(g);
		
	}

	
/**
 * Private class for the KeyListners
 * 
 *	-Set up for left and right arrow key as well as the "A" and "D" keys
 *			-If its left or A move left
 *			-If its right or D move right
 *	- Set up space bar event handler
 *			- Actually allows the player to launch the ball when they are ready to
 *
 * @author Chris
 *
 */
	private class keysPressed implements KeyListener {

		 public void keyPressed(KeyEvent e) {

		        int key = e.getKeyCode();
		        
		        //Only allow key input to be executed if game is started
		        if(run == true)
		        {
			        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) 
			        {
			            player.moveLeft();
			            if(ball.isLockedToPaddle() == true){
			        		ball.ballOnPaddleLetft();
			        	}
			            repaint();
			        }
	
			        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) 
			        {
			            player.moveRight();
			            if(ball.isLockedToPaddle() == true){
			        		ball.ballOnPaddleRight();
			        	}
			            repaint();
			        }
			        
			        if(key == KeyEvent.VK_SPACE){
			        	ballLaunched();
			        }
		        }
		    }
		 
		 
		 //DO NOT COMMENT OUT, NEED THIS FOR COLLISION RECTANGLE USAGE
		 //Not really sure why, but Im not asking any questions -CEG
		    public void keyReleased(KeyEvent e) {
		        int key = e.getKeyCode();
		        
		        
		        if (key == KeyEvent.VK_LEFT) {
		        	repaint();
		        }

		        if (key == KeyEvent.VK_RIGHT) {
		        	repaint();
		        }
		    }

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
	}	

	
}
	



