package com.jakobniinja;

public class Card {

  private char rank = ' ';

  private char suit = ' ';

  private static final String RANKS = "A23456789TJQK";

  private static final String SUITS = "HDCS";


  public Card(int id) {
    id = id % 52;
    if (id < 0) {
      id = id * -1;
    }

    rank = RANKS.charAt(id % 13);
    suit = SUITS.charAt(id / 13);
  }

  public Card(String rs) {
    if (rs.length() == 2) {
      char r = rs.charAt(0);
      char s = rs.charAt(1);
      int ri = RANKS.indexOf(r);
      int si = SUITS.indexOf(s);
      if (ri > -1 && si > -1) {
        rank = r;
        suit = s;
      }
    }
  }

  public boolean isValid() {
    return suit != ' ';
  }

  public boolean isGreaterThan(Card card) {
    boolean greaterThan = false;
    char cardSuit = card.getSuit();
    char cardRank = card.getRank();
    if (SUITS.indexOf(suit) > SUITS.indexOf(cardSuit)) {
      greaterThan = true;
    } else if (suit == cardSuit) {
      if (RANKS.indexOf(rank) > RANKS.indexOf(cardRank)) {
        greaterThan = true;
      }
    }
    return greaterThan;
  }

  public static boolean isValidSuit(char c) {
    return SUITS.indexOf(c) > -1;
  }

  public char getRank() {
    return rank;
  }

  public char getSuit() {
    return suit;
  }

  public String toString() {
    return "" + rank + suit;
  }

  public boolean equals(Card card) {
    return (card.getSuit() == suit) && (card.getRank() == rank);
  }
}
