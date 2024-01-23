package com.jakobniinja;

import com.godtsoft.diyjava.DIYWindow;

public class MyWindow extends DIYWindow {

  protected char promptForSuit() {
    char suit = ' ';
    boolean validSuit = false;
    while (!validSuit) {
      suit = promptForChar("Change the suit to H, D, C, or S?");
      suit = Character.toUpperCase(suit);
      if (Card.isValidSuit(suit)) {
        validSuit = true;
      }
    }
    return suit;
  }
  protected char promptForChar(String prompt) {
    char c = ' ';
    boolean valid = false;
    while (!valid) {
      print(prompt);
      String s = input();
      if (s.length() == 1) {
        c = s.charAt(0);
        valid = true;
      } else {
        c = promptForChar(prompt);
      }
    }
    return c;
  }

  protected String promptForString() {
    print("Which card do you want to play (or D to draw)?");
    return input();
  }
}
