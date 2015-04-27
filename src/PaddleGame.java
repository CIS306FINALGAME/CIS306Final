import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JApplet;


public class PaddleGame extends JFrame{

	private ScorePanel scorePanel;
	private GamePanel gamePanel;
	private ControlPanel controlPanel;
	
	public static int WIDTH = 1000;
	public static int HEIGHT = 600;
	
	public PaddleGame()
	{		
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		//Setup panels
		gamePanel = new GamePanel();
		//scorePanel = new ScorePanel(gamePanel);
		controlPanel = new ControlPanel(gamePanel);
		
		
		//Add panels to frame
		//this.add(scorePanel,BorderLayout.NORTH);
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		
		

		this.setVisible(true);

		
	}
	
	
	public static void main(String[] args)
	{
		PaddleGame game = new PaddleGame();
	
	}
	
	
}
