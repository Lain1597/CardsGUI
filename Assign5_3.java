/**
Team Logistic Solutions:  Danh Huynh, Brenna Eckel, Steven Hunt, Guadalupe Alejo, Norma Sanchez
Phase 3: Adding CardGameFramework and creating the game "High Card"
*/

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.util.Random; 
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Assign5_3 extends JFrame
{

   public static int NUM_PLAYERS = 2;         // Player 0 will represent Computer and Player 1 will represent user
   public static int NUM_CARDS_PER_HAND = 7;

   static JButton[] humanCardButtons = new JButton[NUM_CARDS_PER_HAND];
   static CardTable myCardTable = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
   static JLabel playedCard[] = new JLabel[NUM_PLAYERS];
   static JLabel compHand[] = new JLabel[NUM_CARDS_PER_HAND];
   static boolean playedIndex[] = new boolean[NUM_CARDS_PER_HAND];
   static int hcIndex = 0;
   static Card computerHighCard = new Card();
   static int compScore = 0;
   static int playerScore = 0;
   static JLabel labelTitle[] = new JLabel[NUM_PLAYERS];

   static int numPacksPerDeck = 1;
   static int numJokersPerPack = 0;
   static int numUnusedCardsPerPack = 0;
   static Card[] unusedCardsPerPack = new Card[numPacksPerDeck*(56 + 4*numJokersPerPack)];

   // Create an instance of a game
   static CardGameFramework highCardGame = new CardGameFramework( 
         numPacksPerDeck, numJokersPerPack,  
         numUnusedCardsPerPack, unusedCardsPerPack, 
         NUM_PLAYERS, NUM_CARDS_PER_HAND);

   public static void main(String[] args)
   {

      int i = 0;

      highCardGame.newGame();

      // Deal cards to computer and player
      highCardGame.deal();

      // Load interface
      GUICard.loadCardIcons();

      // Create window
      myCardTable.setSize(800, 800);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Create panels 
      myCardTable.computerHand.setBorder(
            BorderFactory.createTitledBorder("Computer Hand"));
      myCardTable.playerHand.setBorder(
            BorderFactory.createTitledBorder("Your Hand"));
      myCardTable.playing.setBorder(
            BorderFactory.createTitledBorder("Playing Area"));
      myCardTable.playing.setLayout(new GridLayout(3, 2));

      // Create labels within computerHand panel
      for (i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         compHand[i] = new JLabel(GUICard.getBackCardIcon());
         myCardTable.computerHand.add(compHand[i]);
      }

      computerHighCard.set(highCardGame.getHand(0).inspectCard(0).getValue(), highCardGame.getHand(0).inspectCard(0).getSuit());
      highCardGame.getHand(0).sort();

      // Create buttons within playerHand panel
      int playerNum = 1;

      for (i = 0; i < NUM_CARDS_PER_HAND; i++)
      {
         humanCardButtons[i] = new JButton("", GUICard.getIcon(highCardGame.getHand(playerNum).inspectCard(i)));
         myCardTable.playerHand.add(humanCardButtons[i]);
      }

      //Implement ActionListen to remove buttons and call playCards
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         humanCardButtons[k].addActionListener(new ActionListener()
         {  
            public void actionPerformed(ActionEvent e)
            {
               myCardTable.playerHand.remove((JButton) e.getSource());
               loop:
                  for (int x = 0; x < NUM_CARDS_PER_HAND; x++)
                  {
                     if ((JButton) e.getSource() == humanCardButtons[x])
                     {
                        playCards(highCardGame.getHand(playerNum).inspectCard(x), highCardGame.getHand(0), x);
                        break loop;
                     }
                  }
               //Refresh hand to remove buttons
               myCardTable.playerHand.setVisible(false);
               myCardTable.playerHand.setVisible(true);
            }
         });
      };

      //Display left and right
      labelTitle[0] = new JLabel("Computer Score: " + compScore, JLabel.CENTER);
      myCardTable.playing.add(labelTitle[0]);

      labelTitle[1] = new JLabel("Your Score: " + playerScore, JLabel.CENTER);
      myCardTable.playing.add(labelTitle[1]);
      
      //Place holders for playing area
      playedCard[0] = new JLabel(GUICard.getBackCardIcon());
      playedCard[1] = new JLabel(GUICard.getBackCardIcon());
      myCardTable.playing.add(playedCard[0]);
      myCardTable.playing.add(playedCard[1]);
      
      // Show window
      myCardTable.setVisible(true); 
   }

   public static void playCards(Card playerCard, Hand computerHand, int x)
   {  
      //Determines when to wipe the cards clean
      if(playedCard[0] != null)
      {
         myCardTable.playing.remove(playedCard[0]);
         myCardTable.playing.remove(playedCard[1]);
         myCardTable.playing.remove(labelTitle[0]);
         myCardTable.playing.remove(labelTitle[1]);
      }
      
      // Determine the first highest card for the computer
      // If no high cards are available, selects the lowest card
      generateCard(highCardGame.getHand(0), x);

      //Remove cards from Computer's hand
      myCardTable.computerHand.remove(compHand[hcIndex]);
      
      //Determine the winner of the round
      if(Card.highCard(highCardGame.getHand(0).inspectCard(hcIndex), highCardGame.getHand(1).inspectCard(x)))   
      {
         playerScore++;
      }
      else
      {
         compScore++;
      }

      // Updates scores to player titles
      labelTitle[0] = new JLabel("Computer Score: " + compScore, JLabel.CENTER);
      myCardTable.playing.add(labelTitle[0]);

      labelTitle[1] = new JLabel("Your Score: " + playerScore, JLabel.CENTER);
      myCardTable.playing.add(labelTitle[1]);
      
      //Display new cards
      playedCard[0] = new JLabel(GUICard.getIcon(highCardGame.getHand(0).inspectCard(hcIndex)));
      myCardTable.playing.add(playedCard[0]);

      playedCard[1] = new JLabel(GUICard.getIcon(highCardGame.getHand(1).inspectCard(x)));
      myCardTable.playing.add(playedCard[1]);

      // If all cards have been used, remove played cards and display winning statement
      if (playedIndex[0] == true && playedIndex[1] == true && playedIndex[2] == true && 
            playedIndex[3] == true && playedIndex[4] == true && playedIndex[5] == true && playedIndex[6] == true)
      {
         myCardTable.playing.remove(playedCard[0]);
         myCardTable.playing.remove(playedCard[1]);
         
         JLabel winStatement;
         
         if (playerScore > compScore)
         {
            winStatement = new JLabel("YOU WON!!!!!", JLabel.CENTER);
            myCardTable.playing.add(winStatement);
         }
         else if (playerScore < compScore)
         {
            winStatement = new JLabel("YOU LOST!!!!!", JLabel.CENTER);
            myCardTable.playing.add(winStatement);
         }
         else
         {
            winStatement = new JLabel("DRAW!!!!!", JLabel.CENTER);
         }
      }
   }

   //Determines the next highest card for the Computer to play
   //If no high cards are available, selects the lowest card
   public static void generateCard(Hand computerHand, int playerChoice)
   {
      for (int i = 0; i < NUM_CARDS_PER_HAND; i++)     
      {
         if (Card.highCard(highCardGame.getHand(0).inspectCard(i), highCardGame.getHand(1).inspectCard(playerChoice)) == false && playedIndex[i] != true)
         {
            computerHighCard.set(highCardGame.getHand(0).inspectCard(i).getValue(), highCardGame.getHand(0).inspectCard(i).getSuit());
            hcIndex = i;
            break;
         }
      }

      if (playedIndex[hcIndex] == true)
      {
         int i = 0;

         do 
         {
            hcIndex = i;
         } while (playedIndex[i++] == true);
      }

      playedIndex[hcIndex] = true;
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
      iconBack = new ImageIcon("images/BK.gif");

      for(int col = 0; col < 14; col++)
      {
         for(int row = 0; row < 4; row++)
         {
            iconCards[col][row] = new ImageIcon("images/" + getStrValue(col) + getCharSuit(row) + ".gif");
         }
      }
      iconsLoaded = true;
   }

   static private String getStrValue(int col)
   {
      switch (col)
      {
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

   static private char getCharSuit(int row)
   {
      switch(row)
      {
      case 0: return 'C';
      case 1: return 'D';
      case 2: return 'H';
      case 3: return 'S';
      default: return 'S';
      }
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
      char val = card.getValue();

      switch(val)
      {
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

   // Order the card values with smallest first
   public static char[] valueRanks = {'A', '2', '3', '4', '5', '6', '7', '8',
         '9', 'T', 'J', 'Q', 'K', 'X'};

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

   public static boolean highCard ( Card cardComp, Card cardPlay )
   {
      int cardValComp = 0;
      int cardValPlay = 0;
      int cardSuitComp = 0;
      int cardSuitPlay = 0;
      boolean won = false;


      for(int i = 0; i < 14; i++ )
      {
         if(cardComp.getValue() == Card.valueRanks[i])
         {
            cardValComp = i;
         }
         if(cardPlay.getValue() == Card.valueRanks[i])
         {
            cardValPlay = i;
         }
      }

      if( cardValComp > cardValPlay)
      {
         won = false;
      }
      else if (cardValComp <= cardValPlay)
      {
         won = true;
      }
      else if (cardValComp == cardValPlay)
      {
         for(int k = 0; k<4; k++)
         {
            if(cardComp.getSuit() ==Card.Suit.values()[k] )
            {
               cardSuitComp = k;

            }
            if(cardPlay.getSuit() ==Card.Suit.values()[k] )
            {
               cardSuitPlay = k;
            }
         }
         if(cardSuitComp <= cardSuitPlay)
         {
            won = false;
         }
         else
         {
            won = true;
         }
      }
      return won;
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
            || value == '8' || value == '9' || value == 'T' || value == 'J' || value == 'Q' || value == 'K' || value == 'X')
      {
         return false;
      }
      else
         return true;
   }

   // -------------------NEW STUFF FOR CARD CLASS--------------------
   //Sort incoming array of cards using a bubble sort 
   public static void arraySort(Card[] cards, int arraySize)
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
}

class Hand {

   static public int MAX_CARDS = 100;
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
 } */
   public Card playCard(int cardIndex)
   {
      Card topCard = myCards[cardIndex];
      myCards[cardIndex] = null;
      numCards--;
      return topCard;
   }



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
   public static final int MAX_CARDS = 6*56; 

   // Private static member data: 
   private static Card [] masterPack; // Array, containing exactly 56 card references.

   // Private member data: 
   private Card [] cards; 
   private int topCard; 
   private int numPacks; 

   // Constructor : Populates the arrays and assigns initial values to members. 
   public Deck ( int numPacks ) {
      allocateMasterPack (); // Lupe's Method (needs to be called in constructor).  
      this.cards = masterPack; 
      init ( numPacks );
      topCard = 56*numPacks;
   }

   // Overloaded Constructor : If no parameters are passed, 1 pack is assumed (default). 
   public Deck () {
      this(1); 
   }

   // Re-populates Card array with 56 * numPacks. ( Doesn't re-populate masterPack ). 
   void init ( int numPacks ) {
      this.cards = masterPack;
      this.numPacks = numPacks;
      this.topCard = 56*numPacks;
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

   // Accessor for topCard
   public int getTopCard(){
      return topCard;
   }

   // Accessor for an individual card. Returns a card with errorFlag = true if k is bad. 
   public Card inspectCard ( int k ) {   
      return cards[k];    
   }

   // Defines masterpack
   private static void allocateMasterPack()
   { 
      int x, y;
      Card.Suit cardSuit;
      char cardValue;

      // allocate 
      masterPack = new Card[56];
      for (y = 0; y < 56; y++)
         masterPack[y] = new Card();

      // loop for the suits 
      // set values for suit
      for (y = 0; y < 4; y++)
      {
         cardSuit = Card.Suit.values()[y];
         masterPack[13*y].set( 'A', cardSuit );  

         for ( cardValue='2', x = 1; cardValue<='9'; cardValue++, x++ )
         {
            masterPack[13*y + x].set( cardValue, cardSuit );
         }

         masterPack[13*y+9].set( 'T', cardSuit );
         masterPack[13*y+10].set( 'J', cardSuit );
         masterPack[13*y+11].set( 'Q', cardSuit );
         masterPack[13*y+12].set( 'K', cardSuit );
      }
   }


   //Removes a card from the deck if it is valid
   boolean addCard(Card card)
   {
      boolean isValidCard = false;
      int count = 0;
      int index = 0;

      // Checks to see if there are any occurances of the card in the deck
      for ( int i = this.topCard; i <= this.numPacks*56; i++ )
      {
         if ( cards[i].getValue() == card.getValue() && cards[i].getSuit() == card.getSuit())
         {
            count++;
            index = i;
         }
      }

      // If the card occurs more than zero times and less than or equal to the number of packs, remove card
      if ( count > 0 )
      {
         this.topCard++;
         cards[index] = new Card( cards[this.topCard].getValue(), cards[this.topCard].getSuit() );
         cards[this.topCard] = new Card(card.getValue(), card.getSuit());
         isValidCard = true;
      }
      return isValidCard;
   }


   // Removes a card from the deck if it is valid
   boolean removeCard(Card card)
   {
      boolean isValidCard = false;
      int count = 0;
      int index = 0;

      // Checks to see if there are any occurrences of the card in the deck
      for ( int i = 0; i < this.topCard; i++ )
      {
         if ( cards[i].getValue() == card.getValue() && cards[i].getSuit() == card.getSuit())
         {
            count++;
            index = i;
         }
      }

      // If the card occurs more than zero times and less than or equal to the number of packs, remove card
      if ( count <= numPacks && count > 0 )
      {
         cards[index] = new Card( cards[this.topCard-1].getValue(), cards[this.topCard-1].getSuit() );
         cards[this.topCard-1] = new Card(card.getValue(), card.getSuit());
         this.topCard--;
         isValidCard = true;
      }
      return isValidCard;
   }


   // Returns the deck back to it’s original order
   void sort() 
   {
      this.init(this.numPacks);
   }

   // Accessor for number of cards
   int getNumCards()
   {
      return this.topCard + 1;
   }

   /////////////////////////////////REVISED DEAL CARD/////////////////////////////////////////////////
   // Returns top Card and removes from deck
   public Card dealCard()
   {
      Card cardToDeal = new Card();

      if ( this.topCard > 0 && this.topCard-1 < 56*numPacks )
      {
         cardToDeal.set(cards[this.topCard-1].getValue(), cards[this.topCard-1].getSuit());         
         topCard--;
      }

      return cardToDeal;
   }
}
//class CardGameFramework  ----------------------------------------------------
class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 56-card packs per deck
   // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
   // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
   // in the game.  e.g. pinochle does not
   // use cards 2-8 of any suit

   public CardGameFramework( int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
         int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
            numCardsPerHand >  numPacks * (56 - numUnusedCardsPerPack)
            / numPlayers )
         numCardsPerHand = numPacks * (56 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );

      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
            cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.SPADES);      
      }

      // return the card played
      return hand[playerIndex].playCard(cardIndex);

   }


   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }
}
