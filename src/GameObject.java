/*
 * Purpose: Top level class which contains all of the attributes and behaviors
 * of the game objects.
 * Attributes:  x and y position of the object (int)
 * 				Image (picture) of the object (Image)
 * 				length and width of the image (ints)
 * 				should this object be drawn (boolean)
 * 				Collision Rectagle -- bounding rectangle for each image (Rectangle)
 */ 


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public abstract class GameObject {
	protected int xPos;
	protected int yPos; //x and y position of the object
	private Image objectPic;  //the picture of the object
	//used to load the image
	protected int width;   //width of the image
	protected int height; //height of the image
	private boolean shouldDraw; //should the object be drawn
	
	protected Rectangle collisionRect; //collision rectangle
	
	
	//Default no argument constructor for the GameObject (not called in this example)
	public  GameObject()
	{
		objectPic = null;
		xPos = -100;
		yPos = -100;
		width = 0;
		height = 0;
		shouldDraw = true;
		collisionRect = null;
		
	}
	
	
	//Overloaded constructor which takes a reference to the image of the object being created
	public GameObject(String pFileName)
	{
		
		loadImage(pFileName);
		xPos = 0;
		yPos = 0;
		shouldDraw = true;
		//Instantiate the bounding rectangle for the object
		collisionRect = new Rectangle(xPos, yPos, 0, 0);
		
		
	}


	//Accessor and Mutator Methods
	
	public int getxPos() {
		return xPos;
	}


	public void setxPos(int xPos) {
		this.xPos = xPos;
	}


	public int getyPos() {
		return yPos;
	}


	public void setyPos(int yPos) {
		this.yPos = yPos;
	}


	public Image getObjectPic() {
		return objectPic;
	}


	public void setObjectPic(Image objectPic) {
		this.objectPic = objectPic;
	}

	public int getWidth()
	{
		if(objectPic!=null)
		{
			return objectPic.getWidth(null);
		}
		else 
		{
			return width;
		}
	}
	
	public int getHeight()
	{
		if(objectPic!=null)
		{
			return objectPic.getHeight(null);
		}
		else 
		{
			return height;
		}
	}
	
	public void setShouldDraw(boolean pDelete)
	{
		shouldDraw = pDelete;
	}
	
	public boolean getShouldDraw()
	{
		return shouldDraw;
	}
	
	public Rectangle getCollisionRect()
	{
		return collisionRect;
	}
	
	//Load image
	public void loadImage(String pFileName) {
    	URL url = null;
    	Toolkit tk = Toolkit.getDefaultToolkit();
        url = this.getClass().getResource(pFileName);
        objectPic = tk.getImage(url);
    }

	abstract public void draw(Graphics g);
	

}