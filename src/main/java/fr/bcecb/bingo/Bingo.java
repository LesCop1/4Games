package fr.bcecb.bingo;

import fr.bcecb.util.Constants;

import java.util.List;
import java.util.stream.IntStream;

public class Bingo {
    private Player player;
    private List<Integer> numbers;

    public Bingo(int gridCount) {
        this.player = new Player(gridCount);
        this.numbers = IntStream.rangeClosed(1, 90).boxed().collect(Constants.toShuffledList());
    }

    public boolean hasRemaining() {
        return !this.numbers.isEmpty();
    }

    public int dropBall() {
        return this.numbers.remove(0);
    }

    public Player getPlayer() {
        return player;
    }
}
