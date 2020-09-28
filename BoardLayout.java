import javax.swing.ImageIcon;

/**
 * This is the interface for the Strategy pattern
 */
public interface BoardLayout
{
	/**
	 * Gets the image representing the background of the board
	 * @return the image
	 */
	ImageIcon getBackGroundImage();
	
	/**
	 * Gets the image representing a Store on this board
	 * @return the image
	 */
	ImageIcon getStoreImage();
	
	/**
	 * Gets the image representing a Pit on this board
	 * @return the image
	 */
	ImageIcon getPitImage();
	
	/**
	 * Gets the image representing a Stone on this board
	 * @return the image
	 */
	ImageIcon getStoneImage();
}
