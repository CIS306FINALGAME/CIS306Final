import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


public class Block extends GameObject{

	public final int WIDTH = 75;
	public final int HEIGHT = 25;
	
	private int pointValue;
	private Random generator;
	
	public Block()
	{
		pointValue = 10;
		
		
	}
	
	public Block(Image pImage)
	{
		
		
		
	}
	
	
	public int getPoints()
	{
		return pointValue;
	}
	
	public void draw()
	{
		
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	
}
