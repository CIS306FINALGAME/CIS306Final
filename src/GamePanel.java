import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

// All kinds of stuff going on in this
			// Have a rectangle able to move back and forth across the screen for every time you press an arrow key

// A ball appears in a location but does not start moving yet
			
		//NEED TO DO
			// =Get the ball to move
			// Set up Collision Nonsense
public class GamePanel extends JPanel implements Runnable{
	
	protected static int  WIDTH = 1000;
	protected static int  HEIGHT = 600;
	
	private Paddle player;
	private Ball ball;



	
	ArrayList<Block> blocks;
	
	private int lives;
	private int score;
	
	private boolean run;


	public GamePanel() 
	{
		blocks = new ArrayList<Block>();
		setupBlocks();
		ball = new Ball();
		player = new Paddle();

		
		this.setSize(WIDTH,HEIGHT);
	
		
		lives = 3;
		score = 0;
	
		run = false;
		

		this.setBackground(Color.WHITE);		
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

			//Only if the ball has been launched do we allow the ball to keep moving
			if(ball.isLockedToPaddle() == false){
				moveBall();
			}
			
			repaint();
			
			try{
				 Thread.sleep(40);
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
	
	private void checkCollisions()
	{
		for (Block block : blocks) {
			
			if(ball.collisionRect.intersects(block.collisionRect))
			{
				block.setBroken(true);
			}
			
		}
		
		
	}
	
	//TBC : TODO : Get index of block before removing..remove throws exception
	private void removeObjects()
	{
		
		
//		for (Block block : blocks) {
//			if(block.isBroken())
//			{
//				blocks.remove(block);
//			}
//		}
	}
	
	//Not sure if we really need this right now.
	private void moveBall()
	{
		
		ball.moveBall();
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
		g.setColor(Color.BLACK);   
		//Draw our player and ball
		player.draw(g);
		ball.draw(g);
		
	}

	private void setupBlocks()
	{
		//Use this block as template for measurements
		Block tempBlock = new Block(0, 0);
		
		for(int i = 0; i < 12; i++)
			for(int j = 0; j < 5; j++)
			{
				blocks.add(new Block( i * 76 + 40, j * 26 + 40));
			}
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
	



