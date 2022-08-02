package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * 
 * @author Luke Gauvin
 * Creates the blocks and the block levels
 */
public class Blocks {
	private Rectangle block;//Creates the block as a rectangle
	private int blockLevel;//Creates the block level int
	
	/**
	 * @author Luke Gauvin
	 * Block Constructor
	 * Creates the basic parameters of how to blocks look
	 */
	Blocks()
	{
		this.block = new Rectangle();
		this.block.setFill(Color.DARKRED);
		this.block.setWidth(145);
		this.block.setHeight(45);
		this.blockLevel = 4;
		this.block.setStroke(Color.ANTIQUEWHITE);
		
	}
	
	/**
	 * @author Luke Gauvin
	 * @param inputLevel this is the input for the block level as to which row they should be created on
	 */
	public Blocks(int inputLevel)
	{
		//Limits the block level so it can't be bigger or smaller than it's suppose to 
		if(inputLevel > 4)
		{
			inputLevel = 4;
		}
		
		else if(inputLevel < 0)
		{
			inputLevel = 0;
		}
		
		this.block = new Rectangle();
		this.block.setWidth(145);
		this.block.setHeight(45);
		this.blockLevel = inputLevel;
		
		//This series of if statements changes the color of the block that correlates the the blocks level
		if(inputLevel == 4)
		{
			this.block.setFill(Color.DARKRED);
		}
		
		else if(inputLevel == 3)
		{
			this.block.setFill(Color.ORANGE);
		}
		
		else if(inputLevel == 2)
		{
			this.block.setFill(Color.YELLOW);
		}
		
		else if(inputLevel == 1)
		{
			this.block.setFill(Color.GREEN);
		}
		
		else if(inputLevel == 0)
		{
			this.block.setFill(Color.BLUE);
		}
		
	}
	/**
	 * @author Luke Gauvin
	 * Method that changes the level of the block if it is hit
	 */
	protected void destroyBlock()
	{
		//This series of if statements changes the color of the block that correlates the the blocks level
		if(this.blockLevel == 4)
		{
			this.block.setFill(Color.ORANGE);
			
		}
		if(this.blockLevel == 3)
		{
			this.block.setFill(Color.YELLOW);

		}
		if(this.blockLevel == 2)
		{
			this.block.setFill(Color.GREEN);

		}
		if(this.blockLevel == 1)
		{
			this.block.setFill(Color.BLUE);

		}

		this.blockLevel -= 1;
	}
	
	/**
	 * @author Luke Gauvin
	 * @return the block that is the rectangle
	 */
	protected Rectangle getRectangle()
    {
        return this.block;
    }
	/**
	 * @author Luke Gauvin
	 * @return the level at which the block resides
	 */
	protected int getBlockLevel()
	{
		return this.blockLevel;
	}
	
}
