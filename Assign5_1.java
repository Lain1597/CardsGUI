/**
Team Logistic Solutions:  Danh Huynh, Brenna Eckel, Steven Hunt, Guadalupe Alejo, Norma Sanchez

Description: Phase 1

- Read and display Card pictures - Read .gif image files as Icons, and attach them to JLabels that we can display on a JFrame.

*/

import javax.swing.*;
import java.awt.*;

public class Assign5_1 {

   static final int NUM_CARD_IMAGES = 57; // Total cards (Standard deck, 2 Jokers, and 1 face down)
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES]; // Image label array

   // Creates filenames in loop.
   // Reads in the filenames and instantiate icons in the array. 
   static void loadCardIcons() {
]
      short index = 0;

      for (short j = 0; j < 4; j++) {
         for (short k = 0; k < 14; k++) {

            icon[index++] = new ImageIcon("images/" + turnIntIntoCardValue(k) + turnIntIntoCardSuit(j) + ".gif");
         }
      }
      
      icon[index++] = new ImageIcon("images/BK.gif");
   }

   // Change int value into card suits.
   static String turnIntIntoCardSuit(int j) {

      String cardSuit;
      String[] imageSuit = {"C", "D", "H", "S"};

      if (j < 0 || j > 3) return "Invalid";

      cardSuit = imageSuit[j];
      return cardSuit;
   }

   // Change int value into card value. 
   static String turnIntIntoCardValue(int k) {

      String cardValue;
      String[] imageValue = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "X"};

      if (k < 0 || k > 13) return "Invalid";
      
      cardValue = imageValue[k];
      return cardValue;
   }

   public static void main(String[] args) {

      int k;

      loadCardIcons();

       // JFrame Window: 
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // Button Layout: 
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      frmMyWindow.setLayout(layout);

      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];

      for (k = 0; k < NUM_CARD_IMAGES; k++)

         labels[k] = new JLabel(icon[k]);

      for (k = 0; k < NUM_CARD_IMAGES; k++)

         frmMyWindow.add(labels[k]);

      // Display Window: 
      frmMyWindow.setVisible(true);
   }
}
