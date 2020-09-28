import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Represents the pits and stores on the board
 *
 */
public interface Hole
{
	
	/**
	 * Gets this hole
	 * @return the hole
	 */
	Hole getHole();// returns this specific hole
	
	/**
	 * Gets the stones in this hole
	 * @return an ArrayList containing the stones
	 */
	ArrayList<Stone> getStones();// get All the stones in this hole
	
	/**
	 * True if this hole is a store false otherwise
	 * @return boolean value
	 */
	boolean isStore();// Tell the difference between a Pit and a Store
	
	/**
	 * Set the position of this hole in the data structure
	 * @param num the position
	 */
	void setHoleNum(int num);//set the number/location of this hole
	
	/**
	 * Get the position of this hole in the data structure
	 * @return the position of the hole
	 */
	int getHoleNum();//get the number/location of this hole
	
	/**
	 * Add a stone to this hole
	 * @param s the stone to be added
	 */
	void addStone(Stone s);//add a stone to this hole
	
	/**
	 * Remove a stone from this hole
	 * @return the stone to be removed
	 */
	Stone removeStone();//remove the stone from this hole
	
	//
	// This part is to help with image positioning and stuff
	//
	
	/**
	 * Set the image representing this hole
	 * @param icon the image
	 */
	void setImage(ImageIcon icon);
	
	/**
	 * Get the label containing the image of the hole
	 * @return the label
	 */
	JLabel getLabel();
	
	/**
	 * Get the image representing this hole
	 * @return the image
	 */
	ImageIcon getIcon();
	
	/**
	 * Set the position and bounds of the image representing this hole
	 * @param x the x position of the hole's top right corner
	 * @param y the y position of the hole's top left corner
	 * @param width the width of the image/JLabel
	 * @param height the height of the image/JLabel
	 */
	void setBounds(int x, int y, int width, int height);
	
	/**
	 * Get the x coordinate of this image
	 * @return the x coordinate
	 */
	int getX();
	
	/**
	 * Get the y coordinate of this image
	 * @return the y coordinate
	 */
	int getY();
	
}
