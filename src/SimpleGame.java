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
import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JApplet;


public class SimpleGame extends JFrame{


	private GameManager panel;
	public static int WIDTH = 1000;
	public static int HEIGHT = 600;
	public SimpleGame()
	{
		
		
	}
	
	
	public static void main(String[] args)
	{
		SimpleGame game = new SimpleGame();
	
	}
	
	
}
