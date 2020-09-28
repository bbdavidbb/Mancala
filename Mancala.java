import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

@SuppressWarnings("unused")
/**
 * This class represents the model
 * contains data structure,array, of the holes
 *
 */
public class Mancala
{
	public static final int NUM_HOLES = 14;
	public static final int MAX_UNDOS = 3;

	private ArrayList<ChangeListener> listeners;

	private Hole[] holes;
	private Hole[] undoHoles;
	
	private int numberOfStones;
	private int undoCount;

	private Store p1;
	private Store p2;

	
	private boolean p1Turn;
	private boolean extraTurn;
	private boolean endGame;
	
	/**
	 * The ctor represents the Model in MVC
	 * has data 
	 * @param numStones the number of initial stones in each pit
	 */
	public Mancala(int numStones)
	{
		p1Turn = true; // player 1 goes first
		numberOfStones = numStones;
		
		extraTurn = false;
		endGame = false;

		p1 = new Store();
		p2 = new Store();

		// holes = new ArrayList<>();
		holes = new Hole[NUM_HOLES];
		undoHoles = new Hole[NUM_HOLES];
		listeners = new ArrayList<>();
		
		undoCount = 0;

		//
		// This part initializes the data structure which is for now an
		// arrayList
		// Rewrite this part if you want to use a different data structure
		// This start from bottom left at the first pit and goes counter
		// clockwise
		// ending at player 2's store
		// Images will be drawn in that order so if you change the data
		// structure
		// remember to change this same loop structure in GameBoard as well
		//
		for (int i = 0; i < holes.length; i++)
		{
			if (i == (NUM_HOLES / 2) - 1)
			{
				holes[i] = p1;
			}
			else if (i == (NUM_HOLES - 1))
			{
				holes[i] = p2;
			}
			else
			{
				// each pit gets a specific amount of initial stones
				Pit pit = new Pit();
				for (int j = 0; j < numStones; j++)
				{
					pit.addStone(new Stone());
				}
				holes[i] = pit;
			}
		}
		copy(holes, undoHoles);
	}

	/**
	 * Gets the data of this model
	 * @return the data
	 */
	public Hole[] getHoles()
	{
		return holes;
	}
	
	/**
	 * The number of initial stones
	 * @return the stones
	 */
	public int getNumberOfStones()
	{
		return numberOfStones;
	}

	/**
	 * Get the amount of stones in player 1's store
	 * @return the amount
	 */
	public int getP1Score()
	{
		return p1.getStones().size();
	}
	
	/**
	 * Gets the amount of stones in player 2's store
	 * @return the amount
	 */
	public int getP2Score()
	{
		return p2.getStones().size();
	}
	
	/**
	 * Returns true if at the game's end false otherwise
	 * @return game ended
	 */
	public boolean end()
	{
		return endGame;
	}
	
	/**
	 * Returns which player's turn it currently is
	 * @return true if player 1's turn false if player 2's turn
	 */
	public boolean isP1Turn()
	{
		return p1Turn;
	}
	
	/**
	 * Change turns p1 to p2 and vice versa
	 */
	public void changeTurn()
	{
		p1Turn = !p1Turn;
	}
	
	/**
	 * Attach a listener to the Model
	 * @param c the listener
	 */
	public void attach(ChangeListener c)
	{
		listeners.add(c);
	}
	
	/**
	 * Helper method that copy's the current data into copy
	 * @param original that data to be copied
	 * @param copy where the data is copied
	 */
	private void copy(Hole[] original, Hole[] copy)
	{
		for (int i = 0; i < original.length; i++)
		{
			if (i == (NUM_HOLES / 2) - 1)
			{
				copy[i] = new Store();//copy was here
				for (int r = 0; r < original[i].getStones().size(); r++)
				{
					copy[i].addStone(new Stone());
				}
			}
			else if (i == (NUM_HOLES - 1))
			{
				copy[i] = new Store();//copy was here
				for (int r = 0; r < original[i].getStones().size(); r++)
				{
					copy[i].addStone(new Stone());
				}
			}
			else
			{
				Pit pit = new Pit();
				for (int j = 0; j < original[i].getStones().size(); j++)
				{
					pit.addStone(new Stone());
				}
				copy[i] = pit;
			}
		}
	}
	
	/**
	 * Primary move function that is called when move is clicked 
	 * Multiple checks and operations take place here that manipulate the data
	 * after done notifies all ChangeListeners
	 * @param h the hole that was clicked 
	 */
	public void play(Hole h)
	{
		copy(holes, undoHoles);// used for undo operations
		
		if (p1Turn && !(h.getHoleNum() < (holes.length / 2) - 1))// 
		{
			return;
		}
		if (!p1Turn && !(h.getHoleNum() > (holes.length / 2) - 1))//p2 can't click on p1
		{
			return;
		}

		ArrayList<Stone> holeStones = h.getStones(); // get the stones of h

		int index = h.getHoleNum() + 1; // the next pit after
		int i = 0;// loop purposes
		int limit = holeStones.size();// limit is how many stones currently in
										// // the hole
		if (limit == 0)// prevent player from clicking on an empty circle and wasting
						// turn
		{
			return;
		}
		
		//
		// Shift for however many number of stones
		//
		while (i < limit)
		{
			Hole next = holes[index++];

			//
			// Skip the opposing player's store
			// When moving
			if (p1Turn && next.getHoleNum() == holes.length - 1)
			{
				next = holes[0];
			}
			if (!p1Turn && next.getHoleNum() == (holes.length / 2) - 1)
			{
				next = holes[index + 1];
			}

			next.addStone(h.removeStone());

			//
			// for the last stone placed
			// check whether this player gets addition turn
			// or capture the opposing player's pit
			//
			if (i == limit - 1)
			{

				if (next.isStore())
				{
					extraTurn = true;
				}
				else if (next.getStones().size() == 1)
				{
					//
					// first if and else if are to prevent capturing your own
					// stones
					//
					if (p1Turn && next.getHoleNum() > (holes.length / 2) - 1)
					{
						// do nothing
					}
					else if (!p1Turn && next.getHoleNum() < (holes.length / 2) - 1)
					{
						// do nothing
					}
					else
					{
						//
						// Capture stones logic
						//
						Hole temp;
						Hole pStore;
						if (next.getHoleNum() < holes.length / 2)// p1 pits
						{
							temp = holes[12 - next.getHoleNum()];// opposing
						}
						else// p2 pits
						{
							temp = holes[-(next.getHoleNum() - 12)];// opposing
						}

						if (p1Turn)// which store gets the stones p1 or p2
						{
							pStore = holes[(holes.length / 2) - 1];
						}
						else
						{
							pStore = holes[holes.length - 1];
						}

						//
						// loop through take all stones
						//
						int numOfCaptured = temp.getStones().size();
						for (int a = 0; a < numOfCaptured; a++)
						{
							pStore.addStone(temp.removeStone());
						}
						if (numOfCaptured != 0)
						{
							pStore.addStone(next.removeStone());// capture your
																// last stone as
																// well
						}
					}
				}

			}
			
			//
			// if at end of the board reset back to the start
			//
			if (index == holes.length)
			{
				index = 0;
			}
			i++;
		}
		//
		// end giant loop
		//
		
		this.hasGameEnded();
		
		for (ChangeListener c : listeners)
		{
			c.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Set current data with the data from the backup
	 * then notify all listeners
	 */
	public void undo()
	{
		copy(undoHoles, holes);
		//undoCount = undoCount +1;
		for (ChangeListener c : listeners)
		{
			c.stateChanged(new ChangeEvent(this));
		}
	}
	
	/**
	 * Return a String representation of the player whose turn it is 
	 * @return the String representation
	 */
	public String getCurrentPlayer()
	{
		if(p1Turn)
		{
			return "Player 1";
		}
		return "Player 2";
	}
	
	/**
	 * True if player has extra turn false otherwise
	 * @return extraTurn
	 */
	public boolean hasExtraTurn()
	{
		return extraTurn;
	}
	
	/**
	 * Reset extra turn value to false
	 */
	public void noExtraTurn()
	{
		extraTurn = false;
	}
	
	/**
	 * Helper method that cycles through the array of holes and checks if one side of the board is empty
	 * if it is game is over
	 */
	private void hasGameEnded()
	{
		//
		// Check when to end game totally inefficient
		//
		int p1Total = 0;
		int p2Total = 0;
		for (int x = 0; x < holes.length / 2; x++)
		{
			if (x != 6)// skip the store
			{
				p1Total += holes[x].getStones().size();
			}
			if (x + 7 != 13)// skip the store
			{
				p2Total += holes[7 + x].getStones().size();
			}
		}
		
		//
		// if game has ended do this
		//
		if (p1Total == 0 || p2Total == 0)
		{
			//
			// Take all remaining stones of the board
			//
			int oneSidesLength = (holes.length / 2)-1;
			if (p1Total == 0)
			{
				for (int k = 0; k < oneSidesLength; k++)
				{
					Hole temp = holes[7 + k];
					int stoneSize = temp.getStones().size();
					for (int z = 0; z < stoneSize; z++)
					{
						p2.addStone(temp.removeStone());
					}
				}
			}
			if (p2Total == 0)
			{
				for (int k = 0; k < oneSidesLength; k++)
				{
					Hole temp = holes[k];
					int stoneSize = temp.getStones().size();
					for (int z = 0; z < stoneSize; z++)
					{
						p1.addStone(temp.removeStone());
					}
				}
			}
			// YOU WIN
			endGame = true;
			System.out.println("THE END");
		}
	}
	
}
