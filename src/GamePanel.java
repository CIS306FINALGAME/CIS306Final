
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	
	private Clip launch;
	private Clip blip;
	
	private Paddle player;
	private Ball ball;
	
	private boolean revertVelocity;
	private boolean blockCrushed;
	private boolean run;
	private boolean godmode;

	ArrayList<Block> blocks;
	
	
	
	private PaddleGame padGameRef;
	
	private JFrame endFrame;
	private JButton playAgainButton;
	private JButton exitButton;
	

	public GamePanel(PaddleGame pPadGameRef) 
	{
		padGameRef = pPadGameRef;
		
		setupGame();
		
		this.setSize(WIDTH,HEIGHT);
		this.setBackground(Color.BLACK);		
		this.addKeyListener(new keysPressed());
		this.setVisible(true);
		this.setFocusable(true);	
		

		// load sounds and set currentSound
		loadLaunchSound();
		loadBlipSound();
		
		
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
	
	public void setGodMode(boolean pEnable)
	{
		godmode = pEnable;
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
			checkEnd();
			
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
		launch.setFramePosition(0);
		launch.start();
		moveBall();
		
	}
	
	/**
	 * Check for ending condition of victory or loss, unless godmode is enabled, in which case the ball is just reset and the game continues (i.e. cannot lose) 
	 * 
	 * @author Travis
	 */
	private void checkEnd()
	{
		if(blocks.isEmpty())
		{
			endgameDialogBox("You Win!");
		}
		
		else if (ball.getyPos()+Ball.ballHeight > 480)
		{
			if(godmode)
			{
				ball.ballReset(player.getxPos() + player.getWidth()/2 - ball.getWidth()/2);
			}
			else 
			{
				endgameDialogBox("You Lose!");
			}			
		}
		
	}
	
	//Checks for collisions across all gameObjects
		//Blocks
		//Player Paddle
	private void checkCollisions()
	{
		
		for (Block block : blocks) {	
			if (blockCrushed == false)
			{
				// Set frame equal to 0 to ensure we are at the beginning of the audio clip
				// Start the audio clip
				if(ball.collisionRect.intersects(block.collisionRect)){
					blip.setFramePosition(0);
					blip.start();
					block.setBroken(true);
					ball.crashedBlock();
					blockCrushed = true;

				}
				blockCrushed = false;
			}
			
		}
		// CEG: For Paddle Collisions, We are in business now boys
			// Set frame equal to 0 to ensure we are at the beginning of the audio clip
			// Start the audio clip
		if(ball.collisionRect.intersects(player.collisionRect)){
				blip.setFramePosition(0);
				blip.start();
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
	
	/**
	 * Remove all broken blocks from list to approach end condition
	 * @author Travis
	 */
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
			
			
			ball.moveBall();
		}
		repaint();	
	}
	
	
	
	private void endgameDialogBox(String pMessage)
	{
		run = false;
		
		endFrame = new JFrame();
		JPanel endButtonPanel = new JPanel();
		
		JLabel endLabel = new JLabel(pMessage);
		endLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		playAgainButton = new JButton("Play Again"); 
		exitButton = new JButton("Exit");
		
		//Set location to center of GamePanel
		endFrame.setSize(300, 100);
		endFrame.setLocation(padGameRef.getX() + this.getWidth()/2 - endFrame.getWidth()/2, padGameRef.getY() + this.getHeight()/2 - endFrame.getHeight()/2);
		endFrame.setLayout(new BorderLayout());
		endFrame.setTitle("Game Over!");
		
		playAgainButton.addActionListener(new buttonListener());
		exitButton.addActionListener(new buttonListener());
		
		endButtonPanel.add(playAgainButton);
		endButtonPanel.add(exitButton);
		
		endFrame.add(endLabel,BorderLayout.CENTER);
		endFrame.add(endButtonPanel, BorderLayout.SOUTH);
		
		
		endFrame.setVisible(true);
		endFrame.requestFocus();
	}
	
	/**
	 * Setup game method to setup game settings for each time game is played
	 * @author Travis
	 */
	private void setupGame()
	{
		blocks = new ArrayList<Block>();
		setupBlocks();
		ball = new Ball();
		player = new Paddle();
		
		padGameRef.setStart(true);
		padGameRef.setPause(false);
		
		revertVelocity = false;
		blockCrushed = false;
		run = false;
		godmode = false;
	}
	
	/**
	 * Setup main block level layout
	 * @author Travis
	 */
	private void setupBlocks()
	{
		final int blockWidth = 75;
		final int blockHeight = 15;
		final int offset = 40;
		
		for(int i = 0; i < 12; i++)
			for(int j = 0; j < 5; j++)
			{
				blocks.add(new Block( i * (blockWidth + 2) + offset, j * (blockHeight + 2) + offset));
			}
		
		//Testing for end condition --> One Block
		
//		for(int i = 0; i < 1; i++)
//			for(int j = 0; j < 1; j++)
//			{
//				blocks.add(new Block( i * (blockWidth + 1) + offset, j * (blockHeight + 1) + offset));
//			}
		
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
	 * 	Audio Clip Load Methods
	 * 		Two different ones
	 * 			~LuanchLoad
	 * 			~Blip Load
	 * 
	 */
	public void loadLaunchSound()
	{
		 try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("launch.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         launch = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         launch.open(audioIn);
	         
	      } 
		 catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } 
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      } 
		 catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      }
	public void loadBlipSound()
	{
		 try {
	         // Open an audio input stream.
	         URL url = this.getClass().getClassLoader().getResource("collisionBlip.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
	         // Get a sound clip resource.
	         blip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         blip.open(audioIn);
	         
	      } 
		 catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } 
		 catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      } 
		 catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			        
			        if(key == KeyEvent.VK_G)
			        {
			        	if(godmode == true)
			        	{
			        		setGodMode(false);
			        		padGameRef.setGodModeLabel(false);
			        	}
			        	else 
			        	{
			        		setGodMode(true);
			        		padGameRef.setGodModeLabel(true);
						}
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
	
	/**
	 * 
	 * Button listener to be applied to dialog box shown when end game condition is met.
	 * @author Travis
	 *
	 */
	private class buttonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == playAgainButton)
			{
				endFrame.dispose();		//Delete the dialog box
				setupGame();			//Setup a fresh game
				repaint();				//Repaint the screen
				
			}
			else if(e.getSource() == exitButton)
			{
				System.exit(ABORT);
			}
				
		}
	}

	
}
	



