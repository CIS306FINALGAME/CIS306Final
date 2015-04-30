import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
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
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JApplet;

/**
 * Class Name: PaddleGame
 * Purpose: 	
 * 		PaddleGame serves as the overall container. It is where the panels are housed as well as where the main[] of the application is
 * 		executed. 
 *
 */
public class PaddleGame extends JFrame{

	//Panels to be added to frame
	private GamePanel gamePanel;
	private ControlPanel controlPanel;
	
	public static int WIDTH = 1000;
	public static int HEIGHT = 600;
	
	//Constructor
	public PaddleGame()
	{		
		//Setup GUI Layout and position
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Paddle Game");
		this.setLocation(200,100);
		
		//Setup panels
		gamePanel = new GamePanel(this);
		controlPanel = new ControlPanel(gamePanel);
		
		
		//Add panels to frame
		this.add(gamePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);

		this.setVisible(true);
	}
	
	//Getters and setters for components on the Control Panel
	public void setStart(boolean pEnabled)
	{
		controlPanel.startButton.setEnabled(pEnabled);
	}
	
	public void setPause(boolean pEnabled)
	{
		controlPanel.pauseButton.setEnabled(pEnabled);
	}
	
	public void setGodModeLabel(boolean pEnabled)
	{
		controlPanel.setGodModeLabel(pEnabled);
	}
	
	
	
	//Main
	public static void main(String[] args)
	{
		PaddleGame game = new PaddleGame();
	}
	
	
}
