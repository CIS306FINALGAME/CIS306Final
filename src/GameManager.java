import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GameManager extends JPanel {
	
	private Timer timer;

	//private Paddle thePlayer;  //Player objects
	
	private GamePanel game;
	
	private Toolkit tk; //toolkit used to load images
	private URL url;  //URL used to load images
	
	
	
	
	private int score;  //Score of the game
	
	public GameManager()
	{
//		tk = Toolkit.getDefaultToolkit();
//	
//		//Load the an image to represent the player 
//		
//		
//		//Instantiate a player and pass the image
//		thePlayer= new Paddle(tk.getImage(url));
//		//Instantiate an "enemy object" or arrays of falling objects
//		
//		
//		//Instantiate timer
//
//
//		//Set focusable to be true so the user doesn't have to click on the screen
//		this.setFocusable(true);
//		//Add the keyboard listener to the class
//		
		game = new GamePanel();
//		
		this.setBackground(Color.GRAY);
		this.setVisible(true);
		
	
	}
	
	//Start the timer object
	public void startTimer()
	{
		timer.start();
	}
	
	
	
	
	//Draw the background, all of the falling objects in the list
	//and all of the bullets that are active in the list
	public void paintComponent(Graphics g)
	{
	
		
	}
	
	
	//Because this is only one thread, I needed the timer to run faster
	//to check for collisions, but I didn't want the objects to move 
	//as fast a second time check was placed in the code
	private class TimerListener implements ActionListener
	{
	
		private int time;
		
		//The listener that triggered when the timer goes off
		public void actionPerformed(ActionEvent arg0) {
		
		
		
		}
	
		
		//Check each falling object against each live bullet
		private void checkCollisions()
		{
	
			

				
				
			
		}
		
		
		//Go through the removeList and remove each object from
		//the falling object array list and the bullet list respectively
		private void removeObjects()
		{
			
		}
		
		//Move all of the falling objects.  Called from the 
		//action listener
		private void moveFallingObjects()
		{
			
		}
		
		//Move the bullets
		private void moveBullets()
		{
			
			
		}
	}

	private class MyKeyListener implements KeyListener{
	@Override
	public void keyPressed(KeyEvent e) {
		
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	}


}
