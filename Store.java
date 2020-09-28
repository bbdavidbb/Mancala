import java.util.ArrayList;

import javax.swing.*;

/**
 * Has a JLabel which uses a ImageIcon to represent itself on the board
 * Has a ArrayList that contains all the stones
 *
 */
public class Store implements Hole
{
	private JLabel label;
	private ImageIcon icon;
	
	private int xPos;
	private int yPos;
	
	private int holeNum;
	
	private ArrayList<Stone> stones;
	

	/**
	 * The ctor
	 */
	public Store()
	{
		stones = new ArrayList<>();
		holeNum =0;
	}
	

	/**
	 * Set the image representing this hole
	 * @param icon the image
	 */
	public void setImage(ImageIcon i)
	{
		label = new JLabel(i);
		this.icon = i;
	}
	
	/**
	 * Set the position and bounds of the image representing this hole
	 * @param x the x position of the hole's top right corner
	 * @param y the y position of the hole's top left corner
	 * @param width the width of the image/JLabel
	 * @param height the height of the image/JLabel
	 */
	public void setBounds(int x, int y, int width, int height)
	{
		this.label.setBounds(x, y, width, height);
		
		xPos = x;
		yPos = y;
	}
	
	/**
	 * Get the label containing the image of the hole
	 * @return the label
	 */
	public JLabel getLabel()
	{
		return this.label;
	}
	
	/**
	 * Get the image representing this hole
	 * @return the image
	 */
	public ImageIcon getIcon()
	{
		return this.icon;
	}

	/**
	 * Get this hole
	 * @return this hole
	 */
	public Hole getHole()
	{
		return this;
	}
	
	/**
	 * True if this hole is a store false otherwise
	 * @return boolean value
	 */
	public boolean isStore()
	{
		return true;
	}
	
	/**
	 * Gets the stones in this hole
	 * @return an ArrayList containing the stones
	 */
	public ArrayList<Stone> getStones()
	{
		return stones;
	}
	
	/**
	 * Add a stone to this hole
	 * @param s the stone to be added
	 */
	public void addStone(Stone s)
	{
		stones.add(s);
	}
	
	/**
	 * Remove a stone from this hole
	 * @return the stone to be removed
	 */
	public Stone removeStone()
	{
		return stones.remove(stones.size()-1);
	}
	
	/**
	 * Get the x coordinate of this image
	 * @return the x coordinate
	 */
	public int getX()
	{
		return xPos;
	}
	
	/**
	 * Get the y coordinate of this image
	 * @return the y coordinate
	 */
	public int getY()
	{
		return yPos;
	}
	
	/**
	 * Set the position of this hole in the data structure
	 * @param num the position
	 */
	public void setHoleNum(int num)
	{
		holeNum = num;
	}

	/**
	 * Get the position of this hole in the data structure
	 * @return the position of the hole
	 */
	public int getHoleNum()
	{
		return holeNum;
	}
}
