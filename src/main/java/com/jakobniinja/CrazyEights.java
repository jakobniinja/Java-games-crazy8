package com.jakobniinja;

import java.util.ArrayList;
import java.util.Random;

public class CrazyEights extends MyWindow {

  private Deck deck = new Deck();

  private Hand myHand = new Hand();

  private Hand computerHand = new Hand();

  private Card discard;

  private ArrayList<Card> discardPile = new ArrayList<Card>();

  private Random rand = new Random();

  private char activeSuit = ' ';

  private int countHearts = 0;

  private int countDiamonds = 0;

  private int countClubs = 0;

  private int countSpades = 0;

  public CrazyEights() {
    // deal 7 cards to each of us
    for (int i = 0; i < 7; i++) {
      Card card1 = deal();
      myHand.add(card1);
      Card card2 = deal();
      computerHand.add(card2);
    }
    // turn up the discard
    discard = deal();
    // if discard is an 8, set the active suit
    if (discard.getRank() == '8') {
      activeSuit = discard.getSuit();
    } else {
      activeSuit = ' ';
    }

    // who goes first?
    int turn = rand.nextInt(2);
    if (turn == 1) {
      print("Computer goes first.");
      playComputerCard();
    } else {
      print("You go first.");
    }

    // play until either of us runs out of cards
    boolean done = false;
    while (!done) {
      playMyCard();
      // are there any cards left in my hand?
      if (myHand.size() == 0) {
        done = true;
      } else {
        playComputerCard();
        // are there any cards left in the computer hand?
        if (computerHand.size() == 0) {
          done = true;
        }
      }
    }
    print();
    // who played all their cards?
    if (myHand.size() == 0) {
      print("Congratulations! You won! The computer still had " + computerHand.size() + " cards.");
    } else {
      print("Sorry, you lost. You still had " + myHand.size() + " cards.");
      print("My Hand: " + myHand);
      print("Discard: " + discard);
    }
  }

  private Card deal() {
    // if end of deck, reuse the discard pile and shuffle it.
    if (deck.size() == 0) {
      deck.reuse(discardPile);
      deck.shuffle();
      discardPile.clear();
      print();
      print("Reshuffled the discard pile.");
    }
    // deal a card from the deck
    Card card = deck.deal();
    return card;
  }

  private void showStatus() {
    print();
    print("Computer has " + computerHand.size() + " cards.");
    print("My Hand: " + myHand);
    print("Discard: " + discard);
    if (discard.getRank() == '8') {
      print("Suit is " + activeSuit);
    }
  }

  private void drawMyCard() {
    Card drewCard = deal();
    print();
    print("You drew " + drewCard);
    myHand.add(drewCard);
    // if I can play my drawn card, play it
    if (isValidPlay(drewCard.toString())) {
      print("You played " + drewCard);
      discardMyCard(drewCard);
    }
  }

  private void discardMyCard(Card myCard) {
    myHand.remove(myCard);
    discardPile.add(discard);
    discard = myCard;
    if (myCard.getRank() == '8') {
      activeSuit = promptForSuit();
    }
  }

  private void playMyCard() {
    showStatus();
    boolean validPlay = false;
    // repeat until a valid play has been entered
    while (!validPlay) {
      String rankSuit = promptForString("Which card do you want to play (or D to draw)?");
      rankSuit = rankSuit.toUpperCase();
      // if draw, draw a card
      if (rankSuit.equals("D")) {
        drawMyCard();
        validPlay = true;
      }
      // if valid play, play the card
      else if (isValidPlay(rankSuit)) {
        Card selectedCard = new Card(rankSuit);
        discardMyCard(selectedCard);
        validPlay = true;
      }
    }
  }

  private boolean isValidPlay(String rankSuit) {
    boolean validPlay = true;
    Card card = new Card(rankSuit);
    // is it a valid card?
    if (!card.isValid()) {
      print(rankSuit + " is not a valid card");
      validPlay = false;
    }
    // is that card in my hand?
    else if (!myHand.contains(card)) {
      print(rankSuit + " is not in your hand");
      validPlay = false;
    }
    // does the discard match the rank or suit?
    else if ((card.getSuit() != discard.getSuit()) && (card.getRank() != discard.getRank())) {
      print(rankSuit + " cannot be played on " + discard + ".");
      validPlay = false;
    } else if (!myHand.contains(card)) {
      print(rankSuit + " is not in your hand.");
      validPlay = false;
    }

    // 8s are always valid. If the card is not an 8...
    else if (card.getRank() != '8') {
      // is the discard an 8?
      if (discard.getRank() == '8') {
        // does the card match the active suit?
        if (card.getSuit() != activeSuit) {
          print(rankSuit + " cannot be played on " + discard + " because the suit was set to " + activeSuit);
          validPlay = false;
        }
      }
      // if the discard is not an 8,
      // does the discard match the rank or suit?
      else if (card.getSuit() != discard.getSuit() && card.getRank() != discard.getRank()) {
        print(rankSuit + " cannot be played on " + discard);
        validPlay = false;
      }
    }

    return validPlay;
  }

  private void discardComputerCard(Card computerCard) {
    computerHand.remove(computerCard);
    discardPile.add(discard);
    discard = computerCard;
    if (discard.getRank() == '8') {
      int highestCount = countHearts;
      activeSuit = 'H';
      if (countDiamonds > highestCount) {
        highestCount = countDiamonds;
        activeSuit = 'D';
      }
      if (countClubs > highestCount) {
        highestCount = countClubs;
        activeSuit = 'C';
      }
      if (countSpades > highestCount) {
        highestCount = countSpades;
        activeSuit = 'S';
      }
    }
  }

  private void playComputerCard() {
    System.out.println("Computer hand: " + computerHand);
    ArrayList<Card> playableCards = new ArrayList<Card>();
    ArrayList<Card> eights = new ArrayList<Card>();
    countHearts = 0;
    countDiamonds = 0;
    countClubs = 0;
    countSpades = 0;
    // count eights and number of each suit
    for (int i = 0; i < computerHand.size(); i++) {
      Card card = computerHand.cardAt(i);
      // if it's an eight, save it
      if (card.getRank() == '8') {
        eights.add(card);
      }
      // otherwise, count the number of each suit
      else {
        switch (card.getSuit()) {
          case 'H':
            countHearts++;
            break;
          case 'D':
            countDiamonds++;
            break;
          case 'C':
            countClubs++;
            break;
          case 'S':
            countSpades++;
            break;
        }
      }
    }
    // make list of playable cards
    for (int i = 0; i < computerHand.size(); i++) {
      Card card = computerHand.cardAt(i);
      // if discard is an 8, all cards of active suit are playable
      if (discard.getRank() == '8') {
        if (card.getSuit() == activeSuit) {
          playableCards.add(card);
        }
      }
      // else, if discard is not an 8,
      // only cards of the same suit or rank are playable
      else if (card.getSuit() == discard.getSuit() || card.getRank() == discard.getRank()) {
        playableCards.add(card);
      }
    }
    // pick a random playable card
    int numberOfPlayableCards = playableCards.size();
    if (numberOfPlayableCards > 0) {
      int pick = rand.nextInt(numberOfPlayableCards);
      Card playedCard = playableCards.get(pick);
      discardComputerCard(playedCard);
    }
    // otherwise, if have an eight, play an eight
    else if (eights.size() > 0) {
      Card playedCard = eights.get(0);
      discardComputerCard(playedCard);
    }

    // if nothing could play, draw a card
    else {
      Card drewCard = deal();
      computerHand.add(drewCard);
      print();
      print("Computer drew a card.");
      // if it plays, play it
      if (drewCard.getSuit() == discard.getSuit() || drewCard.getRank() == discard.getRank()) {
        discardComputerCard(drewCard);
      }
    }
  }

  private char promptForSuit() {
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


  public static void main(String[] args) {
    new CrazyEights();
  }
}

