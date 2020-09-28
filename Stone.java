import java.util.ArrayList;

import javax.swing.*;

//
// Players collect stones to get points
//
public class Stone
{
	private JLabel label;
	private ImageIcon icon;
	
	private int xPos;
	private int yPos;
	
	public Stone()
	{
		
	}
	
	public void setImage(ImageIcon i)
	{
		label = new JLabel(i);
		this.icon = i;
	}
	
	public void setBounds(int x, int y, int width, int height)
	{
		this.label.setBounds(x, y, width, height);
		
		xPos = x;
		yPos = y;
	}
	
	public JLabel getLabel()
	{
		return this.label;
	}
	
	public ImageIcon getIcon()
	{
		return icon;
	}
	
	public int getX()
	{
		return xPos;
	}
	
	public int getY()
	{
		return yPos;
	}
	
}
