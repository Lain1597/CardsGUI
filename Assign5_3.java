import javax.swing.*;
import javax.swing.border.*;
import java.util.Random; 
import java.awt.*;

public class assign8 extends JFrame
{

	 static int NUM_CARDS_PER_HAND = 7;
	   static int NUM_PLAYERS = 2;
	   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
	   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
	   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
	   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];

	   static Card InspectCard()
	   {
	      char suit;
	      char val;

	      int suitSelector;
	      int valSelector;

	      // get random suit and value
	      suitSelector = (int) (Math.random() * 4);
	      valSelector = (int) (Math.random() * 13);

	      // pick suit
	      suit = turnIntIntoSuit(suitSelector);
	      val = turnIntIntoVal(valSelector);

	      return new Card(val, suit);
	   }

	   static char turnIntIntoSuit(int suitSelector)
	   {
	      if(suitSelector >= 0 && suitSelector <= 3)
	         return Card.cardValues[suitSelector];
	      return Card.cardValues[0];//returning default suit clubs.
	   }

	   static char turnIntIntoVal(int valSelector)
	   {
	      if(valSelector >= 0 && valSelector <= 13)
	         return Card.valueRanks[valSelector];
	      return Card.valueRanks[12];//returning default value A.
	   }

	   public static void main(String[] args)
	   {
	      int k;
	      Icon tempIcon;
	      
	      int numPacksPerDeck = 1;
	      int numJokersPerPack = 0;
	      int numUnusedCarsPerPack = 0;
	      Card[] unusedCardsPerPack = null;

	      CardGameFramework toyGame = new CardGameFramework(
	            numPacksPerDeck, numJokersPerPack,
	            numUnusedCarsPerPack, unusedCardsPerPack,
	            NUM_PLAYERS, NUM_CARDS_PER_HAND);
	      toyGame.deal();

	      // establish main frame in which program will run
	      CardTable myCardTable
	      = new CardTable("CS 1B CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
	      myCardTable.setSize(800, 600);
	      myCardTable.setLocationRelativeTo(null);
	      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // show everything to the user
	      myCardTable.setVisible(true);


	      // CREATE LABELS ----------------------------------------------------
	      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
	         humanLabels[k] = new JLabel(GUICard.getIcon(
	               toyGame.getHand(1).inspectCard(k)));

	      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
	         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());


	      // ADD LABELS TO PANELS -----------------------------------------
	      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
	         myCardTable.playerHand.add(humanLabels[k]);

	      for(k = 0; k < NUM_CARDS_PER_HAND; k++)
	         myCardTable.computerHand.add(computerLabels[k]);

	      // and two random cards in the play region (simulating a computer/hum ply)
	      for(k = 0; k < NUM_PLAYERS; k++)
	         playedCardLabels[k] = new JLabel(GUICard.getIcon(
	               InspectCard()));

	      playLabelText[0] = new JLabel("Computer", 0);
	      playLabelText[1] = new JLabel("You", 0);

	      //Adding cards to the play area panel.
	      for(k = 0; k < NUM_PLAYERS; k++)
	      {
	         myCardTable.playing.add(playedCardLabels[k]);
	         myCardTable.playing.add(playLabelText[k]);
	      }
	      //Adding lables to the play area panel under the cards.
	      myCardTable.playing.add(playLabelText[0]);
	      myCardTable.playing.add(playLabelText[1]);

	      // show everything to the user
	      myCardTable.setVisible(true);
	   }
	   }
