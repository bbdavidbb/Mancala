import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

//
// This will act as the View and Controller and accept the Strategy layout 
//
@SuppressWarnings("serial")
/**
 * This class acts as the view and controller
 * 
 */
public class GameBoard extends JFrame implements ChangeListener
{
	private Mancala mancala;

	private JPanel panel;
	private JLayeredPane pane;

	private JButton undo;
	private JButton endTurn;

	private Hole[] holes; // change if change data structure

	private int undoCount;
	private boolean clickable;

	private ImageIcon backGround;
	private ImageIcon storeImage;
	private ImageIcon pitImage;
	private ImageIcon stoneImage;

	private JLabel scoreLabel;
	private JPanel scorePanel;

	/**
	 * The ctor
	 * @param layout takes a board layout part of the strategy pattern
	 * @param data takes a model and the data from the model
	 */
	public GameBoard(BoardLayout layout, Mancala data)
	{
		mancala = data;
		holes = mancala.getHoles(); // change if changed data structure

		// frame = new JFrame();// Frame the contains Panel
		panel = new JPanel();// Panel that contains the pane
		pane = new JLayeredPane();// Pane contains in order of layer
									// Background, Pits and Stores on top of
									// background, then Stones on top of
									// everything
		undo = new JButton("Undo");
		endTurn = new JButton("End Turn");
		undo.setEnabled(false);
		endTurn.setEnabled(false);
		undoCount = Mancala.MAX_UNDOS;

		backGround = layout.getBackGroundImage();
		storeImage = layout.getStoreImage();
		pitImage = layout.getPitImage();
		stoneImage = layout.getStoneImage();

		clickable = true;

		mancala.attach(new ChangeListener()
		{
			public void stateChanged(ChangeEvent event)
			{
				if (undoCount > 0)
				{
					undo.setEnabled(true);
				}
				endTurn.setEnabled(true);
			}
		});

		undo.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				undoCount--;
				mancala.undo();
//				scoreLabel.setText("<html>Player 1 Score: " + mancala.getP1Score() + " | " + "Player 2 Score: "
//						+ mancala.getP2Score() + "<br>Current Player: " + mancala.getCurrentPlayer() + " | "
//						+ "Undos Left: " + undoCount + "</html>");
				scoreLabel.setText("<html>" +  "Current Player: " + mancala.getCurrentPlayer() + " | "
						+ "Undos Left: " + undoCount + "</html>");
				clickable = true;
				undo.setEnabled(false);
				endTurn.setEnabled(false);

			}
		});

		endTurn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mancala.changeTurn();
				undoCount = Mancala.MAX_UNDOS;
				scoreLabel.setText("<html>" + "Current Player: " + mancala.getCurrentPlayer() + " | "
						+ "Undos Left: " + undoCount + "</html>");
				clickable = true;
				undo.setEnabled(false);
				endTurn.setEnabled(false);
			}
		});

		drawBoard();
	}
	
	/**
	 * Draws the board and counters.
	 * Due to the use of the JLayeredPane this takes the place of paintComponent
	 * and repaint() methods.
	 */
	public void drawBoard()
	{

		//
		// Sets the pane to be the size of the background
		// Then adds the background to the Pane
		//
		JLabel bLabel = new JLabel(backGround);
		pane.setPreferredSize(new Dimension(backGround.getIconWidth(), backGround.getIconHeight()));
		bLabel.setBounds(0, 0, backGround.getIconWidth(), backGround.getIconHeight());
		pane.add(bLabel, JLayeredPane.DEFAULT_LAYER);

		// add the undo button
		undo.setFont(new Font("Arial", Font.PLAIN, 20));
		undo.setBounds(30, 30, 100, 50);
		undo.setPreferredSize(new Dimension(100, 100));
		pane.add(undo, JLayeredPane.MODAL_LAYER);

		// add the endturn
		endTurn.setFont(new Font("Arial", Font.PLAIN, 20));
		endTurn.setBounds(150, 30, 150, 50);
		endTurn.setPreferredSize(new Dimension(100, 100));
		pane.add(endTurn, JLayeredPane.MODAL_LAYER);

		// ScoreBoard
		scoreLabel = new JLabel();
		scoreLabel.setText("<html>" + "Current Player: " + mancala.getCurrentPlayer() + " | "
				+ "Undos Left: " + undoCount + "</html>");
		scorePanel = new JPanel();
		scoreLabel.setLayout(new BorderLayout());
		scorePanel.add(scoreLabel, BorderLayout.CENTER);
		scorePanel.setBounds((backGround.getIconWidth() / 2) - 200, 30, 300, 30);
		pane.add(scorePanel, JLayeredPane.PALETTE_LAYER);

		int numHoles = holes.length; // change data structure
		int backGroundWidth = backGround.getIconWidth();
		int backGroundHeight = backGround.getIconHeight();

		//
		// This will need to be changed if a different data structure is used
		//
		for (int i = 0; i < holes.length; i++)
		{
			Hole h = holes[i];
			h.setHoleNum(i);// set to help with logic work

			//
			// Bottom row left to right represents P1 pits
			// side
			// Add changeListeners here
			//
			if (i < (numHoles / 2) - 1)
			{
				h.setImage(pitImage);
				pane.add(h.getLabel(), JLayeredPane.PALETTE_LAYER);
				// int h2X = backGround.getIconWidth() / 2 + 300 - (i * 175); //
				// the reverse direction
				int h2X = backGroundWidth / 2 - 575 + (i * 175);
				int h2Y = backGroundHeight / 2 + 200;
				h.setBounds(h2X, h2Y, h.getIcon().getIconWidth(), h.getIcon().getIconHeight());
				
				// This is the number of stones + label
				JLabel pitLabel = new JLabel();
				int numTemp = h.getHoleNum()+1;
				pitLabel.setText("<html> A" + numTemp + "<br>"+ "<center>"+ h.getStones().size() +"</center>"+ "</html>");
				JPanel pitPanel = new JPanel();
				pitLabel.setLayout(new BorderLayout());
				pitPanel.add(pitLabel, BorderLayout.CENTER);
				pitPanel.setBounds((h2X + h.getIcon().getIconWidth()/2)-15,h2Y+h.getIcon().getIconHeight()+5, 30, 40);
				pane.add(pitPanel, JLayeredPane.MODAL_LAYER);
				
				h.getLabel().addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event)
					{
						if (clickable && mancala.isP1Turn()&& h.getStones().size()!=0)
						{
							clickable = false;
							mancala.play(h);
						}
					}
				});
			}
			//
			// Add player 1's Store to the RIGHT side of the Board
			//
			if (i == ((numHoles / 2) - 1))
			{
				h.setImage(storeImage);
				pane.add(h.getLabel(), JLayeredPane.PALETTE_LAYER);
				int p1X = backGroundWidth - (h.getIcon().getIconWidth()) - 200;
				int p1Y = (backGroundHeight / 2) - (h.getIcon().getIconHeight() / 2);
				h.setBounds(p1X, p1Y, h.getIcon().getIconWidth(), h.getIcon().getIconHeight());
				
				JLabel playerLabel = new JLabel();
				playerLabel.setText("<html> A" + "<br>"+ "<center>" + h.getStones().size()+"</center>" + "</html>");
				JPanel playerPanel = new JPanel();
				playerLabel.setLayout(new BorderLayout());
				playerPanel.add(playerLabel, BorderLayout.CENTER);
				playerPanel.setBounds((p1X + h.getIcon().getIconWidth()/2)-15,p1Y+h.getIcon().getIconHeight()+5, 30, 50);
				pane.add(playerPanel, JLayeredPane.MODAL_LAYER);
			}
			//
			// Top row right to left represents P2 sides
			//
			if (i < numHoles - 1 && i > (numHoles / 2) - 1)
			{
				
				h.setImage(pitImage);
				pane.add(h.getLabel(), JLayeredPane.PALETTE_LAYER);
				// int h1X = backGround.getIconWidth() / 2 - 600 + (i * 175); //
				// the reverse direction
				int h1X = backGroundWidth / 2 + 300 - ((i - 7) * 175);
				int h1Y = backGroundHeight / 2 - 300;
				h.setBounds(h1X, h1Y, h.getIcon().getIconWidth(), h.getIcon().getIconHeight());
				
				// This is the number of stones + label
				JLabel pitLabel = new JLabel();
				int numTemp = h.getHoleNum()-6;
				pitLabel.setText("<html> B" + numTemp + "<br>"+ "<center>"+ h.getStones().size() +"</center>"+ "</html>");
				JPanel pitPanel = new JPanel();
				pitLabel.setLayout(new BorderLayout());
				pitPanel.add(pitLabel, BorderLayout.CENTER);
				pitPanel.setBounds((h1X + h.getIcon().getIconWidth()/2)-15,h1Y-45, 30, 40);
				pane.add(pitPanel, JLayeredPane.MODAL_LAYER);

				h.getLabel().addMouseListener(new MouseAdapter()
				{
					public void mousePressed(MouseEvent event)
					{
						if (clickable && (!mancala.isP1Turn())&&h.getStones().size()!=0)
						{
							clickable = false;
							mancala.play(h);
						}
					}
				});
			}
			//
			// Add player 2's Store to the LEFT side of the Board
			//
			if (i == (numHoles - 1))
			{
				h.setImage(storeImage);
				pane.add(h.getLabel(), JLayeredPane.PALETTE_LAYER);
				int p2X = (h.getIcon().getIconWidth() / 2);
				int p2Y = (backGroundHeight / 2) - (h.getIcon().getIconHeight() / 2);
				h.setBounds(p2X, p2Y, h.getIcon().getIconWidth(), h.getIcon().getIconHeight());
				
				JLabel playerLabel = new JLabel();
				playerLabel.setText("<html>B " + "<br>"+ "<center>"+ h.getStones().size() +"</center>"+ "</html>");
				JPanel playerPanel = new JPanel();
				playerLabel.setLayout(new BorderLayout());
				playerPanel.add(playerLabel, BorderLayout.CENTER);
				playerPanel.setBounds((p2X + h.getIcon().getIconWidth()/2)-15,p2Y-45, 30, 40);
				pane.add(playerPanel, JLayeredPane.MODAL_LAYER);
			}

		}

		// add the final pane to panel then frame
		drawStones();
		panel.add(pane);
		this.add(panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	/**
	 * Draws the number of stones in each Hole
	 */
	public void drawStones()
	{
		Random r = new Random();
		for (int i = 0; i < holes.length; i++)
		{
			Hole h = holes[i];
			ArrayList<Stone> dStones = h.getStones();
			for (Stone stone : dStones)
			{
				stone.setImage(stoneImage);
				pane.add(stone.getLabel(), JLayeredPane.MODAL_LAYER);

				int xMin = h.getX() + stone.getIcon().getIconWidth();
				int xMax = (h.getX() + h.getIcon().getIconWidth()) - (stone.getIcon().getIconWidth() + 20);

				int yMin = h.getY() + stone.getIcon().getIconHeight();
				int yMax = h.getY() + h.getIcon().getIconHeight() - (stone.getIcon().getIconHeight() + 20);

				int x = r.nextInt(xMax - xMin) + xMin;
				int y = r.nextInt(yMax - yMin) + yMin;

				stone.setBounds(x, y, stone.getIcon().getIconWidth(), stone.getIcon().getIconHeight());
			}
		}
	}

	@Override
	/**
	 * Each time the model is changed update the board by drawing it again
	 */
	public void stateChanged(ChangeEvent e)
	{
		holes = mancala.getHoles();
		pane.removeAll();
		drawBoard();
		
		//
		// Notify the player they have an extra turn
		//
		if (mancala.hasExtraTurn())
		{
			clickable = true;
			endTurn.setEnabled(false);
			mancala.noExtraTurn();
			
			//
			// Notify player they have an extra turn
			//
			JLabel extraTurnLabel = new JLabel();
			extraTurnLabel.setText("<html>" + mancala.getCurrentPlayer() + " has an extra turn</html>");
			JPanel extraTurnPanel = new JPanel();
			extraTurnLabel.setLayout(new BorderLayout());
			extraTurnPanel.add(extraTurnLabel, BorderLayout.CENTER);
			extraTurnPanel.setBounds((backGround.getIconWidth() / 2)+200, 30, 200, 30);
			pane.add(extraTurnPanel, JLayeredPane.PALETTE_LAYER);
		}
		
		//
		// Game is over display a pop up of who won
		//
		if (mancala.end())
		{
			String winner = "";
			if (mancala.getP1Score() == mancala.getP2Score())
			{
				winner = "It's a tie";
			}
			else if (mancala.getP1Score() > mancala.getP2Score())
			{
				winner = "Player 1 is the winner with " + mancala.getP1Score() + " stones ";
			}
			else
			{
				winner = "Player 2 is the winner " + mancala.getP2Score() + " stones ";
			}
			JOptionPane.showMessageDialog(null, "GAME OVER " + winner);
		}
	}
}
