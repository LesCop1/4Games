package fr.bcecb.poker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * The main poker class which includes all the logic of the game
 */
public class Poker {
    public static final int DEFAULT_BANKROLL = 500;
    public static final int START_NUM_CARD = 2;
    private static final int STARTING_SMALL_BLIND = DEFAULT_BANKROLL / 100;
    private static final int BLIND_GAME_INCREASE = 5;

    public static final int ACTION_BET = 0;
    public static final int ACTION_CHECK = 1;
    public static final int ACTION_FOLLOW = 2;
    public static final int ACTION_BED = 3;

    private int playerAmount;
    private HashMap<Integer, Player> players = new HashMap<>();

    private Deck deck;
    private Deck table;
    private int onTableAmount;
    private int numGame;
    private int startingGamePlayer;
    private int currentSmallBlind;
    private int currentPlayer;
    private int currentHighestPlayerBet;
    private int numTurns;
    private int numPlayerInTurn;
    private boolean newTurn = false;
    private boolean doPokerEnds = false;

    public Poker(int playerAmount) {
        this.deck = new Deck();
        this.playerAmount = playerAmount;
        this.table = new Deck();
        for (int i = 0; i < playerAmount; i++) {
            players.put(i, new Player());
        }
        this.startingGamePlayer = 0;
        this.numGame = 0;
        this.currentSmallBlind = STARTING_SMALL_BLIND;
    }

    /**
     * Initialize the poker game
     */
    public void initGame() {
        // Create a new deck, with the right numbers of cards and shuffle it.
        this.deck.clear();
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

        // Reset vars
        this.table.clear();
        this.onTableAmount = 0;
        this.numTurns = 0;
        this.newTurn = false;
        this.numPlayerInTurn = 0;
        this.currentPlayer = this.startingGamePlayer;

        // Subtract the blind to each players.
        this.players.get(this.currentPlayer).addToTable(this.currentSmallBlind);
        this.addOnTableAmount(this.currentSmallBlind);
        for (int i = 0; i < this.playerAmount; i++) {
            if (!(i == this.currentPlayer)) {
                this.players.get(i).addToTable(this.currentSmallBlind * 2);
                this.addOnTableAmount(this.currentSmallBlind * 2);
            }
        }
        this.currentHighestPlayerBet = currentSmallBlind * 2;

        // Add the first 3 cards.
        for (int i = 0; i < 3; i++) {
            this.table.push(this.deck.pop());
        }

        // Increment numTurns
        this.numTurns++;
        this.numGame++;
    }

    /**
     * Update the game logic
     */
    public void update(int action) {
        // Let the players play and switch to the next one
        this.players.get(this.currentPlayer).play(this, action);
        updatePlayer();
        updateTurn();

        // Increase blind
        if (this.numGame % BLIND_GAME_INCREASE == 0) this.currentSmallBlind *= 2;
    }

    private void updatePlayer() {
        int count = 0;
        this.currentPlayer = ++this.currentPlayer % this.playerAmount;
        while (!getPlayer(this.currentPlayer).isPlaying()) {
            this.currentPlayer = ++this.currentPlayer % this.playerAmount;
            count++;
            if (count >= this.playerAmount) break;
        }
    }

    public boolean isAlone() {
        int counter = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (!getPlayer(i).isPlaying()) {
                counter++;
            }
        }
        return counter == this.playerAmount - 1;
    }

    private void updateTurn() {
        this.newTurn = false;
        this.numPlayerInTurn++;

        boolean nextTurn = true;
        for (int i = 1; i < this.players.size(); i++) {
            if (this.players.get(i - 1).getOnTable() != this.players.get(i).getOnTable()) {
                nextTurn = false;
                break;
            }
        }
        if (nextTurn && this.numPlayerInTurn >= this.playerAmount) {
            this.currentPlayer = this.startingGamePlayer;
            this.table.push(this.deck.pop());
            this.newTurn = true;
            this.numPlayerInTurn = 0;
            this.numTurns++;
        }
    }

    /**
     * Check if the poker ends or just the game
     */
    public void endGame() {
        for (Player player : this.players.values()) {
            if (player.getBankroll() == this.playerAmount * DEFAULT_BANKROLL) {
                this.doPokerEnds = true;
                break;
            }
        }
        this.startingGamePlayer = ++this.startingGamePlayer % this.playerAmount;
    }

    public Player findAlonePlayer() {
        Player alone = null;
        for (Player player : this.players.values()) {
            if (player.isPlaying()) {
                alone = player;
            }
        }
        return alone;
    }

    public Player findWinner() {
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

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getPlayerNumber(Player player) {
        int find = -1;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i) == player) {
                find = i;
            }
        }
        return find;
    }

    public Player getPlayer(int i) {
        return this.players.get(i);
    }

    public int getPlayerAmount() {
        return playerAmount;
    }

    public Deck getTable() {
        return table;
    }

    public int getNumGame() {
        return numGame;
    }

    public int getNumTurns() {
        return numTurns;
    }

    public boolean isNewTurn() {
        return newTurn;
    }

    public int getOnTableAmount() {
        return onTableAmount;
    }

    public void addOnTableAmount(int onTableAmount) {
        this.onTableAmount += onTableAmount;
    }

    public int getCurrentHighestPlayerBet() {
        return currentHighestPlayerBet;
    }

    public void setCurrentHighestPlayerBet(int currentHighestPlayerBet) {
        this.currentHighestPlayerBet = currentHighestPlayerBet;
    }

    public boolean doPokerEnds() {
        return doPokerEnds;
    }

    public class Player {
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
        private int points;

        public Player() {
            this.playing = true;
            this.bankroll = DEFAULT_BANKROLL;
            this.hand = new Deck();
            this.onTable = 0;
            this.points = 0;
        }

        /**
         * Initialize a player, reset every variable
         */
        private void init() {
            this.playing = true;
            this.hand.clear();
            this.onTable = 0;
            this.points = 0;
        }

        /**
         * Let the current player choose an action
         *
         * @param pokerInstance The current poker Instance
         */
        private void play(Poker pokerInstance, int action) {
            if (this.playing) {
                if (action == Poker.ACTION_BET) {
                    actionBet(pokerInstance, 50);
                } else if (action == Poker.ACTION_FOLLOW) {
                    actionFollow(pokerInstance);
                } else if (action == Poker.ACTION_CHECK) {
                    actionCheck();
                } else if (action == Poker.ACTION_BED) {
                    actionLeave();
                }
            }
        }

        private void actionCheck() {

        }

        private void actionBet(Poker pokerInstance, int amount) {
            if (this.bankroll > 0) {
                if (this.bankroll >= amount && (this.onTable + amount) >= pokerInstance.getCurrentHighestPlayerBet()) {
                    this.addToTable(amount);
                    pokerInstance.addOnTableAmount(amount);
                    pokerInstance.setCurrentHighestPlayerBet(this.onTable);
                } else {
                    pokerInstance.addOnTableAmount(this.bankroll);
                    this.addToTable(this.bankroll);
                }
            }
        }

        private void actionFollow(Poker pokerInstance) {
            actionBet(pokerInstance, pokerInstance.getCurrentHighestPlayerBet() - this.onTable);
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
                            if (j != i && hand.getCards().get(k).getNum() == j) {
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

        public Deck.Card getCard(int i) {
            return this.hand.getCards().get(i);
        }

        public void addToBankroll(int money) {
            this.bankroll += money;
        }

        private void removeFromBankroll(int money) {
            this.bankroll -= money;
        }

        public int getBankroll() {
            return bankroll;
        }

        public int getOnTable() {
            return onTable;
        }

        private int getPoints() {
            return points;
        }

        public boolean isPlaying() {
            return playing;
        }
    }
}
