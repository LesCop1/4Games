package fr.bcecb.poker;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * The main poker class which includes all the logic of the game
 */
public class Poker {
    private static final int DEFAULT_BANKROLL = 500;
    private static final int START_NUM_CARD = 2;
    private static final int STARTING_SMALL_BLIND = DEFAULT_BANKROLL / 100;
    private static final int BLIND_GAME_INCREASE = 5;

    private int playerAmount;
    private HashMap<Integer, Player> players = new HashMap<>();
    private Deck deck;
    private Deck table;
    private boolean isGameInit;
    private int numGame;
    private int startingGamePlayer;
    private int currentSmallBlind;
    private int currentPlayer;
    private int currentHighestBet;
    private int numTurns;

    public Poker(int playerAmount) {
        this.playerAmount = playerAmount;
        this.table = new Deck();
        for (int i = 0; i < playerAmount; i++) {
            players.put(i, new Player());
        }
        this.isGameInit = false;
        this.startingGamePlayer = 0;
        this.numGame = 0;
        this.currentSmallBlind = STARTING_SMALL_BLIND;
    }

    /**
     * This function is called every event and update the game
     */
    public void update() {
        if (!this.isGameInit) {
            initGame();
        }
        updateGame();
    }

    /**
     * Initialize the poker game
     */
    private void initGame() {
        // Create a new deck, with the right numbers of cards and shuffle it.
        this.deck = new Deck();
        this.deck.init();

        // Init all the players.
        for (Player player : this.players.values()) {
            player.init();
        }

        // Give START_NUM_CARD to each players.
        for (int i = 0; i < START_NUM_CARD; i++) {
            for (Player player : this.players.values()) {
                player.addCard(this.deck.pop());
            }
        }

        // Set basic var for the turn.
        this.numTurns = 0;
        this.currentPlayer = this.startingGamePlayer;

        // Subtract the blind to each players.
        this.players.get(this.currentPlayer).addToTable(this.currentSmallBlind);
        for (int i = 0; i < this.playerAmount; i++) {
            if (!(i == this.currentPlayer)) {
                this.players.get(i).addToTable(this.currentSmallBlind * 2);
            }
        }
        this.currentHighestBet = currentSmallBlind * 2;

        // Add the first 3 cards.
        for (int i = 0; i < 3; i++) {
            this.table.push(this.deck.pop());
        }

        // Increment numTurns
        this.numTurns++;

        // Game is now init
        this.isGameInit = true;
    }

    /**
     * Update the game logic
     */
    private void updateGame() {
        // Let the players play and switch to the next one
        this.players.get(this.currentPlayer).play(this);
        this.currentPlayer = this.currentPlayer++ % this.playerAmount;
        updateTurn();

        // Increase blind
        if (this.numGame % BLIND_GAME_INCREASE == 0) this.currentSmallBlind *= 2;

        // Last turns check
        if (this.numTurns == 3) {
            // Evaluate the winner.
            Player winner = findWinner();
            // Add currentBet on the winner account.
            for (Player player : this.players.values()) {
                if (player != winner) {
                    winner.addToBankroll(player.getOnTable());
                }
            }
            endGame();
        }
    }

    private void updateTurn() {
        boolean nextTurn = true;
        for (int i = 1; i < this.players.size(); i++) {
            if (this.players.get(i - 1).getLastBet() != this.players.get(i).getLastBet()) {
                nextTurn = false;
                break;
            }
        }
        if (nextTurn) {
            this.currentPlayer = this.startingGamePlayer;
            this.table.push(this.deck.pop());
            this.numTurns++;
        }
    }

    /**
     * Check if the poker ends or just the game
     */
    private void endGame() {
        this.startingGamePlayer = this.startingGamePlayer++ % 4;
        this.isGameInit = false;
        boolean doPokerEnds = false;
        for (Player player : this.players.values()) {
            if (player.getBankroll() == (playerAmount - 1) * DEFAULT_BANKROLL) {
                doPokerEnds = true;
                break;
            }
        }
        if (doPokerEnds) {
            endPoker();
        }
    }

    private void endPoker() {
        System.out.println("Poker ends");
    }

    private Player findWinner() {
        Player winner = null;
        int winnerPoints = 0;
        for (Player player : players.values()) {
            player.calculatePoints(this.table);
        }
        for (Player player : players.values()) {
            if (player.getPoints() > winnerPoints) {
                winnerPoints = player.getPoints();
                winner = player;
            }
        }
        return winner;
    }

    private int getCurrentHighestBet() {
        return this.currentHighestBet;
    }

    private void setCurrentHighestBet(int currentHighestBet) {
        this.currentHighestBet = currentHighestBet;
    }

    private static class Player {
        // Combination points
        private static final int COMBINATION_POINTS_ROYAL_FLUSH = 10000;
        private static final int COMBINATION_POINTS_STRAIGHT_FLUSH = 9000;
        private static final int COMBINATION_POINTS_FOUR_OF_A_KIND = 8000;
        private static final int COMBINATION_POINTS_FULL = 7000;
        private static final int COMBINATION_POINTS_FLUSH = 6000;
        private static final int COMBINATION_POINTS_STRAIGHT = 5000;
        private static final int COMBINATION_POINTS_SET = 4000;
        private static final int COMBINATION_POINTS_DOUBLE_PAIR = 3000;
        private static final int COMBINATION_POINTS_PAIR = 2000;
        private static final int COMBINATION_POINTS_HIGH_CARD = 1000;
        private static final int COMBINATION_POINTS_NONE = 0;

        private boolean playing;
        private int bankroll;
        private Deck hand;
        private int onTable;
        private int lastBet;
        private int points;

        /**
         * Initialize a player, reset every variable
         */
        private void init() {
            this.playing = true;
            this.bankroll = DEFAULT_BANKROLL;
            this.hand = new Deck();
            this.onTable = 0;
            this.lastBet = -1;
            this.points = 0;
        }

        /**
         * Let the current player choose an action
         *
         * @param pokerInstance The current poker Instance
         */
        // donc la ta la derniere version de dev sur la branche poker. et genre ce qui est en dessous faut juste le relier a des btns
        //dacco:)
        private void play(Poker pokerInstance) {
            if (this.playing) {
//                if (Button.Bet.onclick) {
//                    actionBet(pokerInstance, 50);
//                } else if (Button.Follow.onclick) {
//                    actionFollow(pokerInstance);
//                } else if (Button.check.onclick) {
//                    actionCheck();
//                } else if (Button.Leave.onclick) {
//                    actionLeave();
//                }
            }
        }

        private void actionCheck() {
            this.lastBet = 0;
        }

        private void actionBet(Poker pokerInstance, int amount) {
            if (this.bankroll > 0) {
                if (this.bankroll >= amount && amount > pokerInstance.getCurrentHighestBet()) {
                    this.addToTable(amount);
                    pokerInstance.setCurrentHighestBet(amount);
                    this.lastBet = amount;
                } else {
                    this.addToTable(this.bankroll);
                    this.lastBet = this.bankroll;
                }
            }
        }

        private void actionFollow(Poker pokerInstance) {
            actionBet(pokerInstance, pokerInstance.getCurrentHighestBet());
        }

        private void actionLeave() {
            this.playing = false;
        }

        private void calculatePoints(Deck table) {
            Deck fullHand = new Deck(this.hand, table);
            fullHand.getCards().sort(Comparator.comparingInt(Deck.Card::getNum).reversed());

            int royalFlush = combinationRoyalFlush(fullHand);
            int straightFlush = combinationStraightFlush(fullHand);
            int fourOfaKind = combinationFourOfaKind(fullHand);
            int full = combinationFull(fullHand);
            int flush = combinationFlush(fullHand);
            int straight = combinationStraight(fullHand);
            int set = combinationSet(fullHand);
            int twoPair = combinationTwoPair(fullHand);
            int onePair = combinationOnePair(fullHand);
            int highCard = combinationHighCard(fullHand);

            if (royalFlush > 0) {
                this.points = royalFlush;
            } else if (straightFlush > 0) {
                this.points = straightFlush;
            } else if (fourOfaKind > 0) {
                this.points = fourOfaKind;
            } else if (full > 0) {
                this.points = full;
            } else if (flush > 0) {
                this.points = flush;
            } else if (straight > 0) {
                this.points = straight;
            } else if (set > 0) {
                this.points = set;
            } else if (twoPair > 0) {
                this.points = twoPair;
            } else if (onePair > 0) {
                this.points = onePair;
            } else {
                this.points = highCard;
            }
        }

        private int combinationRoyalFlush(Deck hand) {
            for (Deck.Type type : Deck.Type.values()) {
                List<Deck.Card> royalFlushHand = new ArrayList<>();
                royalFlushHand.add(new Deck.Card(14, type));
                royalFlushHand.add(new Deck.Card(13, type));
                royalFlushHand.add(new Deck.Card(12, type));
                royalFlushHand.add(new Deck.Card(11, type));
                royalFlushHand.add(new Deck.Card(10, type));
                if (hand.getCards().containsAll(royalFlushHand)) {
                    return COMBINATION_POINTS_ROYAL_FLUSH;  // Returns highest card.
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationStraightFlush(Deck hand) {
            for (Deck.Type type : Deck.Type.values()) {
                for (int i = 8; i >= 0; i--) {
                    List<Deck.Card> straightFlushHand = new ArrayList<>();
                    straightFlushHand.add(new Deck.Card(2 + i, type));
                    straightFlushHand.add(new Deck.Card(3 + i, type));
                    straightFlushHand.add(new Deck.Card(4 + i, type));
                    straightFlushHand.add(new Deck.Card(5 + i, type));
                    straightFlushHand.add(new Deck.Card(6 + i, type));
                    if (hand.getCards().containsAll(straightFlushHand)) {
                        return COMBINATION_POINTS_STRAIGHT_FLUSH + i;
                    }
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationFourOfaKind(Deck hand) {
            for (int i = 14; i >= 2; i--) {
                List<Deck.Card> fourOfaKind = new ArrayList<>();
                for (Deck.Type type : Deck.Type.values()) {
                    fourOfaKind.add(new Deck.Card(i, type));
                }
                if (hand.getCards().containsAll(fourOfaKind)) {
                    hand.getCards().remove(fourOfaKind);
                    Deck.Card acolyte = hand.getCards().get(0);
                    return COMBINATION_POINTS_FOUR_OF_A_KIND + (i * 2) + acolyte.getNum();
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationFull(Deck hand) {
            for (int i = 14; i >= 2; i--) {
                int comparisionSet = 0;
                for (int j = 0; j < 5; j++) {
                    if (hand.getCards().get(j).getNum() == i) {
                        comparisionSet++;
                    }
                }
                if (comparisionSet == 3) {
                    for (int j = 14; j >= 2; j--) {
                        int comparisionPair = 0;
                        for (int k = 0; k < 5; k++) {
                            if (j != i && hand.getCards().get(i).getNum() == j) {
                                comparisionPair++;
                            }
                        }
                        if (comparisionPair == 2) {
                            return COMBINATION_POINTS_FULL + i;
                        }
                    }
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationFlush(Deck hand) {
            for (Deck.Type type : Deck.Type.values()) {
                int comparision = 0;
                for (int i = 0; i < 5; i++) {
                    if (hand.getCards().get(i).getType() == type) {
                        comparision++;
                    }
                }
                if (comparision == 5) {
                    int handValue = 0;
                    for (int i = 0; i < 5; i++) {
                        handValue += hand.getCards().get(i).getNum() * (5 - i);
                    }
                    return COMBINATION_POINTS_FLUSH + handValue;
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationStraight(Deck hand) {
            int accumulator = 0;
            int multiplicator = 100000;
            for (int j = 0; j < 5; j++) {
                accumulator += hand.getCards().get(j).getNum() * multiplicator;
                multiplicator /= 10;
            }
            for (int i = 10; i >= 2; i--) {
                int accumulatorDefault = 0;
                int multiplicatorDefault = 100000;
                for (int j = 0; j < 5; j++) {
                    accumulatorDefault += (i + j) * multiplicatorDefault;
                    multiplicatorDefault /= 10;
                }
                if (accumulator == accumulatorDefault) {
                    return COMBINATION_POINTS_STRAIGHT + i * 10;
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationSet(Deck hand) {
            for (int i = 14; i >= 2; i--) {
                int comparisionSet = 0;
                for (int j = 0; j < 5; j++) {
                    if (hand.getCards().get(j).getNum() == j) {
                        comparisionSet++;
                    }
                }
                if (comparisionSet == 3) {
                    return COMBINATION_POINTS_SET + i * 10;
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationTwoPair(Deck hand) {
            for (int i = 14; i >= 2; i--) {
                int comparisionFirstPair = 0;
                for (int j = 0; j < 5; j++) {
                    if (hand.getCards().get(j).getNum() == i) {
                        comparisionFirstPair++;
                    }
                }
                if (comparisionFirstPair == 2) {
                    for (int j = 14; j >= 2; j--) {
                        int comparisionSecondPair = 0;
                        for (int k = 0; k < 5; k++) {
                            if (i != j && hand.getCards().get(k).getNum() == j) {
                                comparisionSecondPair++;
                            }
                        }
                        if (comparisionSecondPair == 2) {
                            int acolyte = 0;
                            for (int k = 0; k < 5; k++) {
                                if (!(hand.getCards().get(k).getNum() == i || hand.getCards().get(k).getNum() == j)) {
                                    acolyte = hand.getCards().get(k).getNum();
                                }
                            }
                            return COMBINATION_POINTS_DOUBLE_PAIR + i * 5 + j * 2 + acolyte;
                        }
                    }
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationOnePair(Deck hand) {
            for (int i = 14; i >= 2; i--) {
                int comparision = 0;
                for (int j = 0; j < 5; j++) {
                    if (hand.getCards().get(j).getNum() == i) {
                        comparision++;
                    }
                }
                if (comparision == 2) {
                    int highestCard = 0;
                    for (int k = 0; k < 5; k++) {
                        if (hand.getCards().get(k).getNum() != i && highestCard < hand.getCards().get(k).getNum()) {
                            highestCard = hand.getCards().get(k).getNum();
                        }
                    }
                    return COMBINATION_POINTS_PAIR + i * 10 + highestCard;
                }
            }
            return COMBINATION_POINTS_NONE;
        }

        private int combinationHighCard(Deck hand) {
            return COMBINATION_POINTS_HIGH_CARD + hand.getCards().get(0).getNum() * 10;
        }

        private void addCard(Deck.Card card) {
            this.hand.push(card);
        }

        private void addToTable(int money) {
            if (money >= this.bankroll) {
                removeFromBankroll(this.bankroll);
            } else {
                this.onTable += money;
                removeFromBankroll(money);
            }
        }

        private void addToBankroll(int money) {
            this.bankroll += money;
        }

        private void removeFromBankroll(int money) {
            this.bankroll -= money;
        }

        private int getBankroll() {
            return bankroll;
        }

        private int getOnTable() {
            return onTable;
        }

        private int getLastBet() {
            return lastBet;
        }

        private int getPoints() {
            return points;
        }
    }
}
