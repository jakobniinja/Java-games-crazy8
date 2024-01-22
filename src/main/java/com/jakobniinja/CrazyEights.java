package com.jakobniinja;

import com.godtsoft.diyjava.DIYWindow;

public class CrazyEights extends DIYWindow {

  public CrazyEights() {

    Hand hand = new Hand();
    Card card1 = new Card("KC");
    Card card2 = new Card("7H");
    Card card3 = new Card("JC");
    Card card4 = new Card("2D");
    hand.add(card1);
    hand.add(card2);
    hand.add(card3);
    print(hand.toString());
    print(hand.size());
    if (hand.contains(card3)) {
      print("Found Jack of Clubs");
    }
    if (hand.contains(card4)) {
      print("Found 2 of Diamond");
    }
    hand.remove(card2);
    hand.remove(card4);
    print(hand.toString());
    print(hand.size());
  }

  public static void main(String[] args) {
    new CrazyEights();
  }

}
