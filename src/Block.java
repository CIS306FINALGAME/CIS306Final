import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


public class Block extends GameObject{

	public final int WIDTH = 75;
	public final int HEIGHT = 25;
	
	private int pointValue;
	private boolean broken;
	
	
	public Block(int pXPos, int pYPos)
	{
		//Need to add functionality to add images
		xPos = pXPos;
		yPos = pYPos;
		pointValue = 10;
		broken = false;
		collisionRect = new Rectangle(xPos,yPos,WIDTH,HEIGHT);
	}
	
	
	public int getPoints()
	{
		return pointValue;
	}
	
	public boolean isBroken()
	{
		return broken;
	}
	
	public void setBroken(boolean pBroken) {
		this.broken = pBroken;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		g.setColor(Color.BLUE);

		g.fillRect(xPos, yPos, WIDTH, HEIGHT);
		
		
		
		g.setColor(Color.RED);
		
		g.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
	}
	
	
}
