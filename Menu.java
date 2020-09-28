import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This is the starting menu of the program that takes user input
 * and creates the board accordingly
 *
 */
public class Menu
{
	private static BoardLayout layout;
	private static int numOfStones;

	private static JFrame menuFrame;

	/**
	 * The ctor. Creates starting menu
	 */
	public Menu()
	{
		menuFrame = new JFrame();
		menuFrame.setSize(800, 800);
		menuFrame.setLayout(new BorderLayout());
		

		JLabel title = new JLabel("Welcome to Mancala",JLabel.CENTER);
		title.setFont(new Font("defaultFont", 1, 40));
		
		JLabel direction = new JLabel("Choose a Board Layout to Begin",JLabel.CENTER);
		direction.setFont(new Font("defaultFont", 1, 40));

		JButton lava = new JButton("Lava Board");
		lava.setFont(new Font("Arial", Font.PLAIN, 40));
		JButton snow = new JButton("Snow Board");
		snow.setFont(new Font("Arial", Font.PLAIN, 40));

		menuFrame.add(title, BorderLayout.NORTH);
		menuFrame.add(direction,BorderLayout.CENTER);

		JPanel buttons = new JPanel(new GridLayout(1, 2));
		buttons.setPreferredSize(new Dimension(100, 100));
		buttons.add(lava, BorderLayout.CENTER);
		buttons.add(snow, BorderLayout.CENTER);
		menuFrame.add(buttons, BorderLayout.SOUTH);

		//menuFrame.pack();// this fixed the not appearing problem but it's small
							// as hell

		final Integer[] stoneNumChoice =
		{ 3, 4 };
		lava.addActionListener(SettingListener(new LavaBoard(), stoneNumChoice));
		snow.addActionListener(SettingListener(new SnowBoard(), stoneNumChoice));
		
		
		menuFrame.setVisible(true);
		menuFrame.setLocationRelativeTo(null);
		menuFrame.setTitle("Mancala Game");
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Factory method that accepts user input and stores the input into the variables
	 * @param lay the layout of the board
	 * @param stoneNumChoice the number of stones in the board
	 * @return the ActionListener
	 */
	public static ActionListener SettingListener(final BoardLayout lay, Integer[] stoneNumChoice)
	{
		return new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int stoneNum;
				try
				{
					stoneNum = (int) JOptionPane.showInputDialog(null, "Select number of stones.", "Stone Selection",
							JOptionPane.QUESTION_MESSAGE, null, stoneNumChoice, stoneNumChoice[0]);
				}
				catch (NullPointerException ex)
				{
					stoneNum = 3;
					return;
				}
				numOfStones = stoneNum;
				layout = lay;// Need to input SnowBoard here!
				Menu.die();
				Menu.start();
			}
		};
	}

	/**
	 * Dispose of this frame after it is done being used
	 */
	public static void die()
	{
		menuFrame.dispose();
	}
	
	/**
	 * Creates the model and board with user input, attaches the board to the model
	 * and starts the game
	 */
	public static void start()
	{
		Mancala game = new Mancala(numOfStones);
		GameBoard board = new GameBoard(layout, game);
		game.attach(board);

		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.pack();
		board.setVisible(true);
	}
}
