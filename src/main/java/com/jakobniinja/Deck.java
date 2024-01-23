package com.jakobniinja;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

  private ArrayList<Card> cards = new ArrayList<>();

  private final Random rand = new Random();

  public Deck() {
    for (int i = 0; i < 52; i++) {
      Card card = new Card(i);
      cards.add(card);
    }
    shuffle();
  }

  public void shuffle() {
    ArrayList<Card> shuffled = new ArrayList<>();
    int numberOfTimes = cards.size();
    for (int i = 0; i < numberOfTimes; i++) {
      int deckSize = cards.size();
      int pick = rand.nextInt(deckSize);
      Card card = cards.remove(pick);
      shuffled.add(card);
    }
    cards = shuffled;
  }

  public Card deal(){
    return cards.remove(0);
  }
  public void reuse(ArrayList<Card> newCards) {
    cards = newCards;
  }
  public int size() {
    return cards.size();
  }

  public String toString() {
    StringBuilder deckString = new StringBuilder();
    for (Card card : cards) {
      deckString.append(card).append(" ");
    }
    return deckString.toString();
  }
}
