import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;


/**
 * Class Name: Block
 * Purpose: 	
 * 		The block class is used to create the breakable blocks to serve as the objective of the game. Blocks are simply rectangular objects
 * 		with GameObject properties as well as a broken setting that determines whether or not it should be removed.
 * 
 * @author Travis
 *
 */

public class Block extends GameObject{

	
	private boolean broken;	
	
	//Constructor
	public Block(int pXPos, int pYPos)
	{
		xPos = pXPos;
		yPos = pYPos;
		
		width = 75;
		height = 15; 
		
		broken = false;
		collisionRect = new Rectangle(xPos,yPos,width,height);
	}
	
	//Determine whether the block is broken
	public boolean isBroken()
	{
		return broken;
	}
	
	//Set the block broken or not
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
