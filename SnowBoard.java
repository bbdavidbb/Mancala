import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * SnowBoard style has 4 images 
 * backGround Image, storeImage, pitImage, and stoneImage
 */
public class SnowBoard implements BoardLayout
{
	private ImageIcon backGround;
	private ImageIcon storeImage;
	private ImageIcon pitImage;
	private ImageIcon stoneImage;
	
	/**
	 *  The ctor that sets the images according to the theme
	 */
	public SnowBoard()
	{
		backGround = new ImageIcon("images/snowBackground.jpg");
		storeImage = new ImageIcon("images/snowStore.png");
		pitImage = new ImageIcon("images/snowPit.png");
		stoneImage = new ImageIcon("images/snowStone.png");
	}
	
	/**
	 * Returns the backGround image associated with this board layout
	 * @return the backGround image
	 */
	public ImageIcon getBackGroundImage()
	{
		return backGround;
	}
	
	/**
	 * Returns the storeImage 
	 * @return the image
	 */
	public ImageIcon getStoreImage()
	{
		return storeImage;
	}
	
	/**
	 * Gets the image representing a Pit on this board
	 * @return the image
	 */
	public ImageIcon getPitImage()
	{
		return pitImage;
	}
	
	/**
	 * Gets the image representing a Stone on this board
	 * @return the image
	 */
	public ImageIcon getStoneImage()
	{
		return stoneImage;
	}
}
