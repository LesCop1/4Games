package fr.bcecb.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Deck is made to represent a real deck of cards
 */
public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new ArrayList<>();
    }

    public Deck(Deck deck1, Deck deck2) {
        this.cards = deck1.cards;
        this.cards.addAll(deck2.cards);
    }

    /**
     * Initialize the deck to have 52 cards.
     */
    public void init() {
        for (Type type : Type.values()) {
            for (int i = 2; i <= 14; i++) {
                cards.add(new Card(i, type));
            }
        }
        shuffle();
    }

    public void push(Card card) {
        cards.add(card);
    }

    public void pushAll(Deck deck) {
        cards.addAll(deck.getCards());
    }

    public Card pop() {
        Card card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return card;
    }

    private void shuffle() {
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }

    public enum Type {
        HEART,
        DIAMOND,
        SPADE,
        CLUB
    }

    public static class Card {
        private int num;
        private Type type;

        public Card(int num, Type type) {
            this.num = num;
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public Type getType() {
            return type;
        }
    }
}
