import javax.swing.*;
import javax.swing.border.*;
import java.util.Random;
import java.awt.*;

public class Assign5 extends JFrame
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 

   
	   static Card generateRandomCard()
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
	     

	      CardGameFramework toyGame = new CardGameFramework();
	          
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
	               generateRandomCard()));

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
   public CardTable(String title, int numCardsPerHand, int numPlayers)  
   {

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

   // ACCESSORS ----------------------------------------------------------------------------------------------------------
   public int getNumPlayers() 
   {
      return numPlayers;
   }

   public int getNumCardsPerHand() 
   {
      return numCardsPerHand;
   }


}

class GUICard
{
   private static Icon[][] iconCards = new ImageIcon[14][4];  // Holds values for icons
   private static Icon iconBack;                              // Holds value for back of card icon
   private static boolean iconsLoaded = false;                // Flag to check if icons are loaded

   static public void loadCardIcons()
   {
      iconBack = new ImageIcon("img/BK.gif");

      for(int col = 0; col < 14; col++)
      {
         for(int row = 0; row < 4; row++)
         {
            iconCards[col][row] = new ImageIcon("img/" + getStrValue(col) + getCharSuit(row) + ".gif");
         }
      }
      iconsLoaded = true;
   }

   static private String getStrValue(int col)
   {
      String valChar = "";
      if(col + 1 > 1 || col + 1 < 10 ){ valChar = Integer.toString(col); }
      else if(col == 0){ valChar = "A";}
      else if(col == 9){ valChar = "T";}
      else if(col == 10){ valChar = "J";}
      else if(col == 11){ valChar = "Q";}
      else if(col == 12){ valChar = "K";}
      else if(col == 13){ valChar = "X";}
      else {valChar = "";}
      return valChar;
   }

   static private char getCharSuit(int row)
   {
      char suitChar = ' ';

      if(row == 0){ suitChar = 'C';}
      else if(row == 1){ suitChar = 'D';}
      else if(row == 2){ suitChar = 'H';}
      else if(row == 3){ suitChar = 'S';}
      else { suitChar = ' ';}

      return suitChar;
   }
   static public Icon getIcon(Card card) 
   {
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   static public Icon getBackCardIcon() 
   {
      return iconBack;
   }

   static public int valueAsInt(Card card)
   {
      int intVal = 0;
      char val = card.getValue();

      if(Character.isDigit(val)){ intVal = Character.getNumericValue(val); }
      else if(val == 'A'){ intVal = 1;}
      else if(val == 'T'){ intVal = 10;}
      else if(val == 'J'){ intVal = 11;}
      else if(val == 'Q'){ intVal = 12;}
      else if(val == 'K'){ intVal = 13;}
      else if(val == 'X'){ intVal = 14;}
      else {intVal = 0;}

      return intVal;

   }

   static public int suitAsInt(Card card)
   {
      int posVal = 0; 

      String suit = card.getSuit().toString();

      if(suit == "CLUBS"){ posVal = 0; }
      else if (suit == "DIAMONDS"){ posVal = 1; }
      else if (suit == "HEARTS"){ posVal = 2; }
      else if (suit == "SPADES"){ posVal = 3; }

      return posVal;
   }
}
//End GUICard Class 
//Simulates a card in a deck of playing cards
class Card
{
   enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES };  
   private char value;
   private Suit suit;
   private boolean errorFlag;

   
   
   //Card values 'X' for a joker 
 public static char[] cardValues = {'A', '2', '3', '4', '5', '6', '7', '8',
 '9', 'T', 'J', 'Q', 'K', 'X'};
 // Order the card values with smallest first
 public static char[] valueRanks = {'2', '3', '4', '5', '6', '7', '8', '9',
 'T', 'J', 'Q', 'K', 'A', 'X'};
   
   
   
   // Default Constructor
   Card()
   {
      set( 'A', Suit.SPADES );
   }

   // Constructor
   Card( char value, Suit suit )
   {
      set( value, suit );
   }

   // Mutators
   public void setValue( char newValue )
   {
      value = newValue;
   }

   public void setSuit( Suit newSuit )
   {
      suit = newSuit;
   }

   public boolean set( char value, Suit suit )
   {
      setValue( value );
      setSuit( suit );
      errorFlag = isValid( value, suit );

      return true;
   }

   // Accessors
   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getFlag()
   {
      return errorFlag;
   }

   // Public Methods
   // Returns the value and suit of a card object as a single string
   public String toString()
   {
      if ( errorFlag == true )
      {
         return "[ invalid ]";
      }
      else 
      {
         return "[" + value + ", " + suit +"]";
      }
   }

   // Returns whether a card is equivalent to the source card
   public boolean equals( Card card )
   {
      if ( getValue() == card.getValue() && getFlag() == card.getFlag() && getSuit() == card.getSuit() )
      {
         return true;
      }
      else
         return false;
   }

   // Private Methods
   // Returns whether a card has a valid value
   private boolean isValid( char value, Suit suit )
   {
      if (value == 'A' || value == '2' || value == '3' || value == '4' || value == '5' || value == '6' || value == '7'
            || value == '8' || value == '9' || value == 'T' || value == 'J' || value == 'Q' || value == 'K')
      {
         return false;
      }
      else
         return true;
   }
   
   // -------------------NEW STUFF FOR CARD CLASS--------------------
 //Sort incoming array of cards using a bubble sort 
 static void arraySort(Card[] cards, int arraySize)
 {
 Card temp;
 for (int i = 0; i < arraySize; i++)
 {
 for (int j = 1; j < arraySize - i; j++)
 {
 if (cards[j] == null)
    
 {
 return;
 }
 int previousRankIndex = findValueRankIndex(cards[j - 1].getValue());
 int currentRankIndex = findValueRankIndex(cards[j].getValue());
 if (previousRankIndex > currentRankIndex)
 {
 temp = cards[j - 1];
 cards[j - 1] = cards[j];
 cards[j] = temp;
 }
 }
 }
 // Get the rank for the card value, which is the index position of it within the valueRanks array.
 private static int findValueRankIndex(char cardValue)
 {
 for (int i = 0; i < valueRanks.length; i++)
 {
 if (cardValue == valueRanks[i])
 {
 return i;
 }
 }
 return 0;
 }
 //Adjusts for the joker, and checks if card is valid
 private boolean isValid(char value, Suit suit)
 {
 switch (value)
 {
 case '2':
 case '3':
 case '4':
 case '5':
 case '6':
 case '7':
 case '8':
 case '9':
 case 'T':
 case 'J':
 case 'Q':
 case 'K':
 case 'A':
 case 'X': // Joker
 return true;
 default:
 return false;
 }
 }      
 
}

class Hand {

   static public int  MAX_CARDS = 100;
   private Card[] myCards;
   private int numCards;

   // Default constructor for a hand of cards.
   public Hand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   // Remove all cards from the hand
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   // Adds a card to the nest available position in the myCards array.
   public boolean takeCard( Card card )
   {
      boolean valid;

      if ( numCards >= MAX_CARDS )
      {
         valid = false;
      }

      else
      {
         myCards[numCards] = card;
         numCards++;
         valid = true;
      }
      return valid;
   }

   // This method will remove and return the top card in the array
   public Card playCard()
   {      
      Card topCard =  myCards[numCards - 1];
      myCards[numCards - 1] = null; 
      numCards-- ;
      return topCard;
   }

   
  /* suggestion to fix takeCard playCard 
  
   // Adds a card to the nest available position in the myCards array.
 public boolean takeCard( Card card )
 {
 Card receivedCard = new Card();
 if (receivedCard.set(card.getValue(), card.getSuit()))
 {
 myCards[numCards] = receivedCard;
 numCards++;
 return true;
 }
 return false;
 }
 
 // This method will remove and return the top card in the array
 public Card playCard()
 { 
 Card topCard = myCards[numCards - 1];
 myCards[numCards - 1] = null; 
 numCards-- ;
 return topCard;
 }
 public Card playCard(int cardIndex)
 {
 Card topCard = myCards[cardIndex];
 myCards[cardIndex] = null;
 numCards--;
 return topCard;
 }
  */
   
   
   // Returns the string built up by the Stringizer
   public String toString()
   {
      int i;
      String cardInfo = "Hand = ( ";

      for ( i = 0; i < numCards; i++ )
      {
         cardInfo += myCards[i].toString();
         if ( i < numCards - 1 )
            cardInfo += ", ";
      }

      cardInfo +=" )";
      return cardInfo;
   }

   //Returns number of cards
   public int getNumCards(){
      return numCards;
   }

   //Returns a card at a given index
   public Card inspectCard( int k ){
      return myCards[k];
   }   
   
   /* suggestion to fix inspectCard
   
   //Returns a card at a given index
 public Card inspectCard( int k ){
if (k >= 0 && k < myCards.length && myCards[k] != null)
 {
 return myCards[k];
 }
 Card errorCard = new Card();
 errorCard.set(' ', null); 
 return errorCard;
 }
 //Returns number of cards
 public int getNumCards(){
 return numCards;
 }
 */
   //---------------------NEW STUFF FOR HAND-------------------
 //Sorts the hand
 public void sort()
 {
 Card.arraySort(myCards, myCards.length);
 }
   
}

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

   
   /* suggestion to fix init
   
   // Constructor : Populates the arrays and assigns initial values to members. 
 public Deck ( int numPacks ) {
 allocateMasterPack (); // Lupe's Method (needs to be called in constructor). 
 init(numPacks);
 }
 // Overloaded Constructor : If no parameters are passed, 1 pack is assumed (default).
 
 
 public Deck () {
 allocateMasterPack();
 init(1); 
 }
 // Re-populates Card array with 52 * numPacks. ( Doesn't re-populate masterPack ). 
 void init ( int numPacks ) {
 this.numPacks = numPacks;
 topCard = -1;
 // Re-populate cards[] with the standard 56 numPacks cards; adjusted for
 // jokers.
 cards = new Card[numPacks * 56];
 for (int i = 0; i < numPacks; i++)
 {
 for (Card card : masterPack)
 {
 topCard++;
 cards[topCard] = card;
 }
 }
 }
 
*/   
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

   // Decrements card from the deck
   public Card dealCard(){
      if ( topCard != 0 )
      {
         topCard--;
      }

      return cards[topCard];
   }

   // Accessor for topCard
   public int getTopCard(){
      return topCard;
   }

   // Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
   public Card inspectCard ( int k ) {   
      return cards[k];    
   }
   
  /* suggestion to fix inspectCard
  
  // Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
 public Card inspectCard ( int k ) { 
 if (k >= 0 && k < myCards.length && myCards[k] != null)
 {
 return myCards[k];
 }
 Card errorCard = new Card();
 errorCard.set(' ', null); 
 return errorCard; 
 }
 */

   
   //--------------------NEW STUFF FOR DECK----------------------
 // Defines masterpack
 private static void allocateMasterPack()
 { 
 if (masterPack != null)
 {
 return; 
 }
 Card.Suit[] cardSuits = new Card.Suit[]{Card.Suit.SPADES, Card.Suit.CLUBS,
 Card.Suit.DIAMONDS, Card.Suit.HEARTS};
 char[] cardValues = new char[]{'A', '2', '3', '4', '5', '6', '7', '8',
 '9', 'T', 'J', 'Q', 'K', 'X'};
 int insertPosition = 0;
 masterPack = new Card[cardSuits.length * cardValues.length];
 for (Card.Suit cardSuit : cardSuits)
 {
 for (char cardValue : cardValues)
 {
 masterPack[insertPosition] = new Card(cardValue, cardSuit);
    
 insertPosition++;
 }
 }
 // Method returns true if card was added to the deck, else it returns false 
 public boolean addCard(Card card)
 {
 int occurrences = 0;
 for (Card cardInHand : cards)
 {
 if (cardInHand != null && cardInHand.equals(card))
 {
 occurrences++;
 }
 }
 if (occurrences >= numPacks || topCard >= cards.length)
 {
 return false;
 }
 cards[topCard] = card;
 return true;
 }
 }
 }
}
