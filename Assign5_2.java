/**
Team Logistic Solutions:  Danh Huynh, Brenna Eckel, Steven Hunt, Guadalupe Alejo, Norma Sanchez

Phase 2: Encapsulating Layount and Icons into CardTable and GUICard Classes

- CardTable: This class will control the positioning of the panels and cards of the GUI.
- GUICard: This class manages the reading and building of the card image Icons.
- Card, Hand, Deck: Adjusted classes from Assignment 3. 

*/

import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import java.awt.*;

public class Assign5_2 extends JFrame {

	static int NUM_CARDS_PER_HAND = 7;
	static int  NUM_PLAYERS = 2;
	static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
	static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
	static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
	static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 

	static Card generateRandomCard() {
	
		int suitSelector;
		int valSelector;

		// Get random suit and value: 
		suitSelector = (int) (Math.random() * 4);
		valSelector = (int) (Math.random() * 14);

		// Pick suit: 
		return new Card(turnIntIntoVal(valSelector), turnIntIntoSuit(suitSelector));
	}

	static Card.Suit turnIntIntoSuit(int suitSelector) {	
	
		switch (suitSelector)  { 
		
			case 0: return Card.Suit.CLUBS;
			case 1: return Card.Suit.DIAMONDS;
			case 2: return Card.Suit.HEARTS;
			case 3: return Card.Suit.SPADES;
			default: return Card.Suit.CLUBS;
		}
	}

	static char turnIntIntoVal(int valSelector) {
	
		switch (valSelector) {
		
			case 0: return 'A';
			case 1: return '2';
			case 2: return '3';
			case 3: return '4';
			case 4: return '5';
			case 5: return '6';
			case 6: return '7';
			case 7: return '8';
			case 8: return '9';
			case 9: return 'T';
			case 10: return 'J';
			case 11: return 'Q';
			case 12: return 'K';
			case 13: return 'X';
			default: return 'A';
		}
	}

	// Start of main method: 
	public static void main(String[] args) {
	
		int k;
		Icon tempIcon;

		GUICard.loadCardIcons();
		
		// Main frame: 
		CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
		myCardTable.setSize(800, 800);
		myCardTable.setLocationRelativeTo(null);
		myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myCardTable.computerHand.setBorder(BorderFactory.createTitledBorder("Computer Hand"));
		myCardTable.playerHand.setBorder(BorderFactory.createTitledBorder("Your Hand"));
		myCardTable.playing.setBorder(BorderFactory.createTitledBorder("Playing Area"));
		myCardTable.playing.setLayout(new GridLayout(2, 2));

		// Create the labels: 
		for (k = 0; k < NUM_CARDS_PER_HAND; k++)
			computerLabels[k] = new JLabel(GUICard.getBackCardIcon());

		for (k = 0; k < NUM_CARDS_PER_HAND; k++)
			humanLabels[k] = new JLabel(GUICard.getIcon(generateRandomCard()));

		for (k = 0; k < NUM_PLAYERS; k++) {
			playedCardLabels[k] = new JLabel(GUICard.getIcon(generateRandomCard()), JLabel.CENTER);
			
			if (0 == k) {
				playLabelText[k] = new JLabel("Computer", JLabel.CENTER);
			}
			else {
				playLabelText[k] = new JLabel("You", JLabel.CENTER);
			}
		}

		// Add labels to panels: 
		for (k = 0; k < NUM_CARDS_PER_HAND; k++)
			myCardTable.computerHand.add(computerLabels[k]);

		for (k = 0; k < NUM_CARDS_PER_HAND; k++)
			myCardTable.playerHand.add(humanLabels[k]);

		for (k = 0; k < NUM_PLAYERS; k++) {
			myCardTable.playing.add(playedCardLabels[k]);
		}

		for (k = 0; k < NUM_PLAYERS; k++) {
			myCardTable.playing.add(playLabelText[k]);
		}

		// Display window: 
		myCardTable.setVisible(true);
	}

} // End of Assign5_2 class

// ---------------------------------------------------------------------------------------------------------

//This class will control the positioning of the panels and cards of the GUI. 
class CardTable extends JFrame {

	// Class variables: 
	static int MAX_CARDS_PER_HAND = 56;
	static int MAX_PLAYERS = 2;

	// Instance Variables: 
	private int numCardsPerHand;
	private int numPlayers;

	public JPanel playerHand, computerHand, playing;

	// Constructor: filters input, adds any panels to the JFrame, and establishes layouts
	public CardTable(String title, int numCardsPerHand, int numPlayers) {

		this.numPlayers = numPlayers;
		this.numCardsPerHand = numCardsPerHand;

		// Creating new JPanels and using gridLayout to place components in a grid of cells : (row, col, hor gap, vert gap)
		playerHand = new JPanel(new GridLayout(1,1,10,10));
		computerHand = new JPanel(new GridLayout(1,1,10,10));
		playing = new JPanel (new GridLayout (2,2,20,20));

		// Setting layout manager: 
		setLayout (new BorderLayout(10,10));

		// Adding components: 
		add(computerHand,BorderLayout.NORTH);
		add(playing,BorderLayout.CENTER);
		add(playerHand,BorderLayout.SOUTH);

		// Adding arbitrary borders with a String titles: 
		playerHand.setBorder(new TitledBorder("Player"));
		computerHand.setBorder(new TitledBorder("Computer"));
		playing.setBorder(new TitledBorder("Playing"));

	}

	// Accessors ----------------------------------------------------------------
	public int getNumPlayers(){
		return numPlayers;
	}

	public int getNumCardsPerHand() {
		return numCardsPerHand;
	}
	
} // End of CardTable class

// ----------------------------------------------------------------------------------------------------------

/** 
- Read the image files and store them in a static Icon 2-D array to facilitate addressing the value and suit of a Card in order get its Icon.
- Convert from chars and suits to ints, and back again, in order to find the Icon for any given Card object.
*/
class GUICard{

	 // Member Variables: 
   private static Icon[][] iconCards = new ImageIcon[14][4];  // Holds values for icons
   private static Icon iconBack;                              // Holds value for back of card icon
   private static boolean iconsLoaded = false;                // Flag to check if icons are loaded
 
   // Generates the image Icon array from files:
   static public void loadCardIcons() {
   
      iconBack = new ImageIcon("images/BK.gif");
 
 			// Storing Icons in a 2-D array. 
      for(int col = 0; col < 14; col++) {
         for(int row = 0; row < 4; row++) {
            iconCards[col][row] = new ImageIcon("images/" + getStrValue(col) + getCharSuit(row) + ".gif");
         }
      }
      
      iconsLoaded = true;
   }
 
	 // Accessors -------------------------------------------------
   static private String getStrValue(int col) {
      
      switch (col) {
      
         case 0: return "A";
         case 1: return "2";
         case 2: return "3";
         case 3: return "4";
         case 4: return "5";
         case 5: return "6";
         case 6: return "7";
         case 7: return "8";
         case 8: return "9";
         case 9: return "T";
         case 10: return "J";
         case 11: return "Q";
         case 12: return "K";
         case 13: return "X";
         default : return "A";
      }
   }
 
   static private char getCharSuit(int row) {
    
      switch(row) {
      
		    case 0: return 'C';
		    case 1: return 'D';
		    case 2: return 'H';
		    case 3: return 'S';
		    default: return 'S';
      }
   }
    
   // Takes a Card object from the client, and returns the Icon for that card.  
   static public Icon getIcon(Card card) {
   
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }
   
   // Returns the card back image.
   static public Icon getBackCardIcon(){
   
      return iconBack;
   }
 
   // -------------------------------------------------------------
   static public int valueAsInt(Card card) {
      
      char val = card.getValue();
      
      switch(val) {
		   
		    case 'A': return 0;
		    case '2': return 1;
		    case '3': return 2;
		    case '4': return 3;
		    case '5': return 4;
		    case '6': return 5;
		    case '7': return 6;
		    case '8': return 7;
		    case '9': return 8;
		    case 'T': return 9;
		    case 'J': return 10;
		    case 'Q': return 11;
		    case 'K': return 12;
		    case 'X': return 13;
		    default: return 0;   
      }
   }
 
   static public int suitAsInt(Card card) {
   
      int posVal = 0; 
 
      String suit = card.getSuit().toString();
 
      if(suit == "CLUBS"){ posVal = 0; }
      else if (suit == "DIAMONDS"){ posVal = 1; }
      else if (suit == "HEARTS"){ posVal = 2; }
      else if (suit == "SPADES"){ posVal = 3; }
 
      return posVal;
   }

} //End GUICard Class 

// --------------------------------------------------------------------------------------------------
class Card {

	enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };  
	
	private char value;
	private Suit suit;
	private boolean errorFlag;

	// Order the card values with smallest first
	public static char[] valueRanks = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};

	// Constructors ---------------------------------------------
	Card() {
	
		set( 'A', Suit.SPADES );
	}

	Card( char value, Suit suit ) {
	
		set( value, suit );
	}

	// Mutators --------------------------------------------------
	public void setValue( char newValue ) {
	
		value = newValue;
	}

	public void setSuit( Suit newSuit ) {
	
		suit = newSuit;
	}

	public boolean set( char value, Suit suit ) {
	
		setValue( value );
		setSuit( suit );
		errorFlag = isValid( value, suit );

		return true;
	}

	// Accessors --------------------------------------------------
	public char getValue() {
	
		return value;
	}

	public Suit getSuit() {
	
		return suit;
	}

	public boolean getFlag() {
	
		return errorFlag;
	}

	// Public Methods ---------------------------------------------
	
	// Returns the value and suit of a card object as a single string
	public String toString() {
	
		if ( errorFlag == true ) {
			return "[ invalid ]";
		}
		else {
			return "[" + value + ", " + suit +"]";
		}
	}

	// Returns whether a card is equivalent to the source card
	public boolean equals( Card card ) {
	
		if ( getValue() == card.getValue() && getFlag() == card.getFlag() && getSuit() == card.getSuit() ) {
			return true;
		}
		else
			return false;
	}

	// Private Methods --------------------------------------------
	
	// Returns whether a card has a valid value
	private boolean isValid( char value, Suit suit ) {
	
		if (value == 'A' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7'
				|| value == '8' || value == '9' || value == 'T' || value == 'J' || value == 'Q' || value == 'K' || value == 'X')
		{
			return false;
		}
		else
			return true;
	}

	// -------------------NEW STUFF FOR CARD CLASS------------------
	
	//Sort incoming array of cards using a bubble sort 
	public static void arraySort(Card[] cards, int arraySize) {
	
		Card temp;
		
		for (int i = 0; i < arraySize; i++) {
		
			for (int j = 1; j < arraySize - i; j++) {
			
				if (cards[j] == null) {
					return;
				}
				
				int previousRankIndex = findValueRankIndex(cards[j - 1].getValue());
				int currentRankIndex = findValueRankIndex(cards[j].getValue());
				
				if (previousRankIndex > currentRankIndex) {
				
					temp = cards[j - 1];
					cards[j - 1] = cards[j];
					cards[j] = temp;
				}
			}
		}
	}
	
	// Get the rank for the card value, which is the index position of it within the valueRanks array.
	private static int findValueRankIndex(char cardValue) {
	
		for (int i = 0; i < valueRanks.length; i++) {
		
			if (cardValue == valueRanks[i]) {
			
				return i;
			}
		}
		
		return 0;
	}
	
} // End of Card class 

// --------------------------------------------------------------------------------------------------
class Hand {

	static public int MAX_CARDS = 100;
	private Card[] myCards;
	private int numCards;

	// Default constructor for a hand of cards.
	public Hand() {
	
		myCards = new Card[MAX_CARDS];
		numCards = 0;
	}

	// Remove all cards from the hand
	public void resetHand() {
	
		myCards = new Card[MAX_CARDS];
		numCards = 0;
	}

	// Adds a card to the nest available position in the myCards array.
	public boolean takeCard( Card card ) {
	
		boolean valid;

		if ( numCards >= MAX_CARDS ) {
		
			valid = false;
		}

		else {
		
			myCards[numCards] = card;
			numCards++;
			valid = true;
		}
		
		return valid;
	}

	// This method will remove and return the top card in the array
	public Card playCard() {
	      
		Card topCard =  myCards[numCards - 1];
		myCards[numCards - 1] = null; 
		numCards-- ;
		return topCard;
	}

	// Returns the string built up by the Stringizer
	public String toString() {
	
		int i;
		String cardInfo = "Hand = ( ";

		for ( i = 0; i < numCards; i++ ) {
		
			cardInfo += myCards[i].toString();
			if ( i < numCards - 1 )
				cardInfo += ", ";
		}

		cardInfo +=" )";
		return cardInfo;
	}

	//Returns number of cards
	public int getNumCards() {
	
		return numCards;
	}

	//Returns a card at a given index
	public Card inspectCard( int k ) {
	
		return myCards[k];
	}   
	
	//---------------------NEW STUFF FOR HAND-------------------
	
	//Sorts the hand
	public void sort() {
	
		Card.arraySort(myCards, myCards.length);
	}

} // End of Hand class

// --------------------------------------------------------------------------------------------------
class Deck {

	// Final constant ( 6 packs maximum ): 
	public static final int MAX_CARDS = 6*52; 

	// Private static member data: 
	private static Card [] masterPack; // Array, containing exactly 52 card references.

	// Private member data: 
	private Card [] cards; 
	private int topCard; 
	private int numPacks; 

	// Constructor : Populates the arrays and assigns initial values to members. 
	public Deck ( int numPacks ) {
		allocateMasterPack (); // Lupe's Method (needs to be called in constructor).  
		this.cards = masterPack; 
		init ( numPacks );
		topCard = 52*numPacks;
	}

	// Overloaded Constructor : If no parameters are passed, 1 pack is assumed (default). 
	public Deck () {
		this(1); 
	}

	// Re-populates Card array with 52 * numPacks. ( Doesn't re-populate masterPack ). 
	void init ( int numPacks ) {
		this.cards = masterPack;
		this.numPacks = numPacks;
		this.topCard = 52*numPacks;
	}
  
	// Mix up cards with the help of standard random num generator: 
	public void shuffle () {
	   
		Random randObj = new Random(); 
		int a; 

		for (a = cards.length -1 ; a > 0; a--) {

			// nextInt() method to get a pseudorandom value from 0 to cards.length. 
			int randNum = randObj.nextInt( cards.length - 1);

			// Changing the cards value: 
			Card temp = cards [randNum];
			cards [randNum] = cards [a]; 
			cards [a] = temp; 
		}
	} 

	boolean removeCard(Card card) {
	
		boolean isValidCard = false;
		int count = 0;
		int index = 0;

		// Checks to see if there are any occurances of the card in the deck
		for (int i = 0; i <= this.topCard; i++ ) {
		
			if ( cards[i].getValue() == card.getValue() && cards[i].getSuit() == card.getSuit()) {
			
				count++;
				index = i;
			}
		}

		// If the card occurs more than zero times and less than or equal to the number of packs, remove card
		if ( count <= numPacks && count > 0 ) {
		
			cards[index] = new Card( cards[this.topCard].getValue(), cards[this.topCard].getSuit() );
			cards[this.topCard] = new Card();
			this.topCard--;
			isValidCard = true;
		}
		
		return isValidCard;
	}

	// Returns the deck back to it’s original order
	void sort() {
	
		this.init(this.numPacks);
	}

	// Accessor for number of cards
	int getNumCards() {
	
		return topCard;
	}

	// Decrements card from the deck
	public Card dealCard() {
	
		Card cardToDeal = new Card();
		
		if ( this.topCard > 0 && this.topCard <= MAX_CARDS ) {
			cardToDeal.set(cards[this.topCard].getValue(), cards[this.topCard].getSuit());
			this.removeCard(cardToDeal);
		}

		return cardToDeal;
	}

	// Accessor for topCard
	public int getTopCard(){
	
		return topCard;
	}

	// Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
	public Card inspectCard ( int k ) {   
	
		return cards[k];    
	}

	//--------------------NEW STUFF FOR DECK----------------------
	
	// Defines masterpack
	private static void allocateMasterPack() {
	 
		if (masterPack != null) {
		
			return; 
		}
		
		Card.Suit[] cardSuits = new Card.Suit[]{Card.Suit.SPADES, Card.Suit.CLUBS, Card.Suit.DIAMONDS, Card.Suit.HEARTS};
		char[] cardValues = new char[]{'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};
		int insertPosition = 0;
		
		masterPack = new Card[cardSuits.length * cardValues.length];
		for (Card.Suit cardSuit : cardSuits) {
		
			for (char cardValue : cardValues) {
			
				masterPack[insertPosition] = new Card(cardValue, cardSuit);
				insertPosition++;
			}
		}
	}
	
	// Method returns true if card was added to the deck, else it returns false 
	public boolean addCard(Card card) {
	
		int occurrences = 0;
		
		for (Card cardInHand : cards) {
		
			if (cardInHand != null && cardInHand.equals(card)) {
			
				occurrences++;
			}
		}
		
		if (occurrences >= numPacks || topCard >= cards.length) {
		
			return false;
		}
		
		cards[topCard] = card;
		
		return true;
	}
} // End of Deck Class
