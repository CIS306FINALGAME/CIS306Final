
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

/**
 * Class Name: GamePanel
 * Purpose:
 * 		This is location of the majority of the game's logic. Inside the game panel is the main thread execution and the functions 
 * 		that go along with it. The game panel is the main drawing panel on the center of the window. This is where the Paddle, Ball,
 * 		and blocks will be drawn. 
 * 		
 * @author Travis
 *
 */
public class GamePanel extends JPanel implements Runnable{
	
	//Panel dimensions
	protected final static int  WIDTH = 1000; 
	protected final static int  HEIGHT = 600;
	
	//Audio
	private Clip launch;
	private Clip blip;
	
	//Game Objects
	private Paddle player;
	private Ball ball;
	
	//For if the ball leaves the GamePanel, to make invert the direction and stop inverting until the ball is back in the panel
	private boolean revertVelocity;		
	private boolean blockCrushed;		
	private boolean run;				
	private boolean godmode;

	//Arraylist of Blocks
	ArrayList<Block> blocks;
	
	//Reference to PaddleGame
	private PaddleGame padGameRef;
	
	//GUI components for End Condition Dialog
	private JFrame endFrame;
	private JButton playAgainButton;
	private JButton exitButton;
	
	//Constructor
	public GamePanel(PaddleGame pPadGameRef) 
	{
		padGameRef = pPadGameRef;
		
		blocks = new ArrayList<Block>();
		setupBlocks();
		ball = new Ball();
		player = new Paddle();
		
		revertVelocity = false;
		blockCrushed = false;
		run = false;
		godmode = false;
		
		this.setSize(WIDTH,HEIGHT);
		this.setBackground(Color.BLACK);		
		this.addKeyListener(new keysPressed());
		this.setVisible(true);
		this.setFocusable(true);	
		

		// load sounds and set currentSound
		loadLaunchSound();
		loadBlipSound();
		
		
	}
	
	
	//...Pretty self documenting I think
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

	//********START MAIN THREAD LOOP********
	
	private void runGame()
	{
		
		while(run) 
		{
				
			//For every iteration of thread, check to see what has collided. Then remove any broken blocks. Finally move the ball.
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
			
			//Now update the screen and check to see if the game winning or losing condition has been met
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
		//If player has destroyed all blocks they win
		if(blocks.isEmpty())
		{
			endgameDialogBox("You Win!");
		}
		
		//If ball falls below the paddle, they lose unless godmode
		else if (ball.getyPos()+Ball.ballHeight > 480)
		{
			//If godMode is turned on, the ball just resets to the paddle and the game continues.
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
			
	}
	
	
	/**
	 * Setup the endGame Dialog box. Prompt the user to play again or exit the game.
	 * @author Travis
	 */
	private void endgameDialogBox(String pMessage)
	{
		//Stop the thread
		run = false;
		
		//Setup the dialog box
		endFrame = new JFrame();
		JPanel endButtonPanel = new JPanel();
		
		//Show message based on ending condition. i.e whether they won or lost
		JLabel endLabel = new JLabel(pMessage);
		endLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Create buttons
		playAgainButton = new JButton("Play Again"); 
		exitButton = new JButton("Exit");
		
		//Set location to center of GamePanel
		endFrame.setSize(300, 100);
		endFrame.setLocation(padGameRef.getX() + this.getWidth()/2 - endFrame.getWidth()/2, padGameRef.getY() + this.getHeight()/2 - endFrame.getHeight()/2);
		endFrame.setLayout(new BorderLayout());
		endFrame.setTitle("Game Over!");
		
		//Add listeners to buttons
		playAgainButton.addActionListener(new buttonListener());
		exitButton.addActionListener(new buttonListener());
		
		//Add buttons to panel 
		endButtonPanel.add(playAgainButton);
		endButtonPanel.add(exitButton);
		
		//Add label and panel to dialogBox
		endFrame.add(endLabel,BorderLayout.CENTER);
		endFrame.add(endButtonPanel, BorderLayout.SOUTH);
		
		//Show and focus
		endFrame.setVisible(true);
		endFrame.requestFocus();
	}
	
	/**
	 * Setup game method to setup game settings for consecutive time game is played
	 * @author Travis
	 */
	private void setupGame()
	{
		//Setup the blocks 
		blocks = new ArrayList<Block>();
		setupBlocks();
		
		//Setup Game Objects
		ball = new Ball();
		player = new Paddle();
		
		//Enable/Disable the buttons appropriately
		padGameRef.setStart(true);
		padGameRef.setPause(false);
		
		//Reset to default conditions
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
		final int columns = 12;
		final int rows = 5;
		final int spacing = 2;
		
		for(int i = 0; i < columns; i++)
			for(int j = 0; j < rows; j++)
			{
				blocks.add(new Block( i * (blockWidth + spacing) + offset, j * (blockHeight + spacing) + offset));
			}
	}
	
	
	
	/**
	 * Method called on repaint. Request focus, then paint all the objects to the panel based on their positions and statuses.
	 * @author Travis
	 */
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
 *  - Set up "G" key as cheat code to enable godMode
 *  		- God mode will ensure it is impossible to lose the game or die
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
 
		    public void keyReleased(KeyEvent e) {
		        int key = e.getKeyCode();
		        
		        
		        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
		        	repaint();
		        }

		        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
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
			
			//If user chooses to play again, dispose of the dialog and setup a new game
			if(e.getSource() == playAgainButton)
			{
				endFrame.dispose();		//Delete the dialog box
				setupGame();			//Setup a fresh game
				repaint();				//Repaint the screen
				
			}
			
			//If the user chooses to exit, exit the application
			else if(e.getSource() == exitButton)
			{
				System.exit(ABORT);
			}
				
		}
	}

	
}
	



