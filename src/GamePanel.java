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
			// Get the ball to move
			// Set up Collision Nonsense
public class GamePanel extends JPanel implements Runnable{
	
	protected final static int  WIDTH = 1000;
	protected final static int  HEIGHT = 600;
	
	private Paddle player;
	private Ball ball;
	
	private boolean revertVelocity;
	private boolean blockCrushed;

	ArrayList<Block> blocks;
	
	
	private boolean run;
	

	public GamePanel() 
	{
		blocks = new ArrayList<Block>();
		setupBlocks();
		ball = new Ball();
		player = new Paddle();
		
		revertVelocity = false;
		blockCrushed = false;
		
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
			//Calls Check collision
			//Remove Object
			//and Move Ball Functions for every iteration of the thread
			checkCollisions();
			removeObjects();
			moveBall();
			
			/**
			 * If statement to see if the ball is back on the screen
			 * 		if its between the max gamePanel width and the gamePanel height, then revert the bool to false
			 * 		Also if its greater then 10 to make sure if came on from the left side
			 * 	Is always checked during the running of the thread
			 */
			if((ball.getxPos() <= GamePanel.WIDTH && ball.getyPos() <= GamePanel.HEIGHT) || ball.getxPos() >= 0){
				revertVelocity = false;
			}	
			
			if(ball.getyPos() >= GamePanel.HEIGHT/2){
				blockCrushed = false;
			}
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
			if (blockCrushed == false)
			{
				if(ball.collisionRect.intersects(block.collisionRect)){
					block.setBroken(true);
					ball.crashedBlock();
					blockCrushed = true;
				}
				blockCrushed = false;
			}
			
		}
		// CEG: For Paddle Collisions, We are in business now boys
		if(ball.collisionRect.intersects(player.collisionRect)){
			
				ball.crashedPaddle();
		}
		
		//If ball moves to any constraint of panel, crash with panel and bounce
				// uses a bool value to make sure the ball gets back on the screen
				// checks to see if its the max,min width, or the max min height
		
		if((revertVelocity==false) && (ball.getxPos() + ball.getWidth() >= GamePanel.WIDTH || ball.getxPos() <= 0))
			
		{
			//Sets the bool to true, and waits to make sure its back on the "screen"
			if(ball.getxPos()>GamePanel.WIDTH || ball.getxPos() <= 0) {
				revertVelocity=true;
				ball.setxVelocity(-1* ball.getxVelocity());
			}
			blockCrushed = false;
		}
			//If ball moves to any constraint of panel, crash with panel and bounce
			// uses a bool value to make sure the ball gets back on the screen
			// checks to see if its the max,min widht or max,min height
		if((revertVelocity==false) && (ball.getyPos() + ball.getHeight() >= GamePanel.HEIGHT || ball.getyPos() <= 0))
		{
			//Sets the bool to true, and waits to make sure its back on the "screen"
			if(ball.getyPos()>GamePanel.WIDTH || ball.getyPos() <= 0){
				revertVelocity=true;	
				ball.setyVelocity(-1* ball.getyVelocity());
			}
			blockCrushed = false;
		}
		
	}

	private void removeObjects()
	{
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
		
		final int blockWidth = 76;
		final int blockHeight = 16;
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
	



