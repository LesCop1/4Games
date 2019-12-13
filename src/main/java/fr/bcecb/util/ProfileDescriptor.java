package fr.bcecb.util;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ProfileDescriptor {
    private String name = "Name";
    private Long moneyAmount = 0L;
    private int avatarIndex;
    private Map<Constants.GameType, Long> timeRecords = new EnumMap<>(Constants.GameType.class);
    private Map<Constants.GameType, List<Integer>> achievements = new EnumMap<>(Constants.GameType.class);

    public ProfileDescriptor() {

    }

    public ProfileDescriptor(String name, Long moneyAmount, int avatarIndex, Map<Constants.GameType, Long> timeRecords, Map<Constants.GameType, List<Integer>> achievements) {
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.avatarIndex = avatarIndex;
        this.timeRecords = timeRecords;
        this.achievements = achievements;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setAvatarIndex(int avatarIndex) {
        this.avatarIndex = avatarIndex;
    }

    public int getAvatarIndex() {
        return avatarIndex;
    }

    public void setTimeRecords(Map<Constants.GameType, Long> timeRecords) {
        this.timeRecords = timeRecords;
    }

    public Map<Constants.GameType, Long> getTimeRecords() {
        return timeRecords;
    }

    public void setAchievements(Map<Constants.GameType, List<Integer>> achievements) {
        this.achievements = achievements;
    }

    public Map<Constants.GameType, List<Integer>> getAchievements() {
        return achievements;
    }
}
