package fr.bcecb;

import fr.bcecb.util.Constants;

public class Achievement {
    private int id;
    private Constants.GameType gameType;
    private String title;
    private String howTo;
    private String rewards;

    public Achievement(int id, Constants.GameType gameType, String title, String howTo, String rewards) {
        this.id = id;
        this.gameType = gameType;
        this.title = title;
        this.howTo = howTo;
        this.rewards = rewards;
    }

    public int getId() {
        return id;
    }

    public Constants.GameType getGameType() {
        return gameType;
    }

    public String getTitle() {
        return title;
    }

    public String getHowTo() {
        return howTo;
    }

    public String getReward() {
        return rewards;
    }
}
