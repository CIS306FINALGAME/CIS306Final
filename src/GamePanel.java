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
			//- Protect Sides with the rectangle,cant move beyond the edges of the panel
			// =Get the ball to move
			// Set up Collision Nonsense
public class GamePanel extends JPanel implements ActionListener{
	
	protected static int  WIDTH = 1000;
	protected static int  HEIGHT = 600;
	
	private Paddle player;
	private Ball ball;
	private Timer timer;
	
	ArrayList<Block> blocks;
	
	private boolean run;
	
	public GamePanel() 
	{
		ball = new Ball();
		player = new Paddle();
		timer = new Timer(90, this);
		
		blocks = new ArrayList<Block>();
		setupBlocks();
		
		
		this.setBackground(Color.WHITE);		
		this.addKeyListener(new keysPressed());
	
		this.setFocusable(true);
		repaint();
	
		
	}
	

	public void paintComponent(Graphics g){
		super.paintComponent( g );
		g.setColor(Color.BLACK);   
		player.draw(g);
		ball.draw(g);
		
		for (Block block : blocks) {
			
			//If block is not broken draw it otherwise do not draw it
			block.draw(g);
			
		}
		
		
		timer.start();
	}
	
	
	private class keysPressed implements KeyListener {

		 public void keyPressed(KeyEvent e) {

		        int key = e.getKeyCode();

		        if (key == KeyEvent.VK_LEFT) {
		            player.moveLeft();
		            repaint();
		            //System.out.println("Left key typed");
		        }

		        if (key == KeyEvent.VK_RIGHT) {
		            player.moveRight();
		            repaint();
		        	//System.out.println("Right key typed");
		        }
		    }

		    public void keyReleased(KeyEvent e) {
		        int key = e.getKeyCode();

		        if (key == KeyEvent.VK_LEFT) {
		        	repaint();
		        	
		        	//System.out.println("LEFt key typed");
		        }

		        if (key == KeyEvent.VK_RIGHT) {
		        	repaint();
		        	//System.out.println("Right key typed");
		        }
		    }

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				  int key = e.getKeyCode();

			        if (key == KeyEvent.VK_LEFT) {
			            player.moveLeft();
			            repaint();
			            //System.out.println("Left key typed");
			        }

			        if (key == KeyEvent.VK_RIGHT) {
			            player.moveRight();
			            repaint();
			        	//System.out.println("Right key typed");
			        }
				
			}
	}	
	
	public void actionPerformed(ActionEvent e) {
			
			ball.moveBall();
			
			repaint();
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
	
}
	



