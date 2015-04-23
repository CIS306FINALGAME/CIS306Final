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
	private Timer timer;



	
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
			
			if(ball.isLockedToPaddle() == false){
				moveBall();
			}
			
			checkKeyInput();
			
			
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
	public void ballLaunched(){
		ball.setLockedToPaddle(false);
		moveBall();
		
	}
	
	private void checkCollisions()
	{
		
	}
	
	private void removeObjects()
	{
		
	}
	
	private void moveBall()
	{
		
		ball.moveBall();
	}
	
	private void checkKeyInput()
	{
		
	}
	

	public void paintComponent(Graphics g){

		super.paintComponent(g);
		this.requestFocus();
		//Need to paint this first, makes sure the ball is "above" the blocks
		
		for (Block block : blocks) {
				//If block is not broken draw it otherwise do not draw it
				if(!block.isBroken())
				{
					block.draw(g);
				}		
		}
		
		g.setColor(Color.BLACK);   
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
	
	private class keysPressed implements KeyListener {

		 public void keyPressed(KeyEvent e) {

		        int key = e.getKeyCode();
		        
		        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) 
		        {
		            player.moveLeft();
		            if(ball.isLockedToPaddle() == true){
		        		ball.ballOnPaddleLetft();
		        		repaint();
		        	}
		            repaint();
		        }

		        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) 
		        {
		            player.moveRight();
		            player.moveLeft();
		            if(ball.isLockedToPaddle() == true){
		        		ball.ballOnPaddleRight();
		        		repaint();
		        	}
		            repaint();
		        }
		        
		        if(key == KeyEvent.VK_SPACE){
		        	ballLaunched();
		        }
		    }
		 
		 
		 //DO NOT COMMENT OUT, NEED THIS FOR COLLISION RECTANGLE USAGE
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
//				// TODO Auto-generated method stub
//				  int key = e.getKeyCode();
//
//			        if (key == KeyEvent.VK_LEFT) {
//			            player.moveLeft();
//			            repaint();
//			            //System.out.println("Left key typed");
//			        }
//
//			        if (key == KeyEvent.VK_RIGHT) {
//			            player.moveRight();
//			            repaint();
//			        	//System.out.println("Right key typed");
//			        }
				
			}
	}	

	
}
	



