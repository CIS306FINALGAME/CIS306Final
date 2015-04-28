import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


public class Block extends GameObject{

	
	private boolean broken;
	
	
	public Block(int pXPos, int pYPos)
	{
		//Need to add functionality to add images
		xPos = pXPos;
		yPos = pYPos;
		
		width = 75;
		height = 15; 
		
		broken = false;
		collisionRect = new Rectangle(xPos,yPos,width,height);
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
		g.setColor(Color.WHITE);
		g.fillRect(xPos, yPos, width, height);
		
		
//		g.setColor(Color.RED);
//		g.drawRect(collisionRect.x, collisionRect.y, collisionRect.width, collisionRect.height);
		

	}
	
	
}
