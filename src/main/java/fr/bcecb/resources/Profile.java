package fr.bcecb.resources;

import com.google.gson.Gson;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Log;
import fr.bcecb.util.ProfileDescriptor;
import fr.bcecb.util.Resources;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile implements IResource {
    private static final Gson GSON = new Gson();

    private ProfileDescriptor descriptor;

    private String name;
    private Long moneyAmount;
    private int profilePictureValue;
    private Map<Constants.GameType, Long> records = new HashMap<>();
    private Map<Constants.GameType, List<Integer>> achievementsSucceed = new HashMap<>();
    private List<Integer> itemsOwns = new ArrayList<>();

    public Profile() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoneyAmount() {
        return this.moneyAmount.intValue();
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public void remMoneyAmount(int moneyAmount) {
        this.moneyAmount -= moneyAmount;
    }

    public int getProfilePictureValue() {
        return this.profilePictureValue;
    }

    public void setProfilePictureValue(int profilePictureValue) {
        this.profilePictureValue = profilePictureValue;
    }

    public Map<Constants.GameType, Long> getRecords() {
        return this.records;
    }

    public long getRecord(Constants.GameType gameType) {
        return this.records.get(gameType);
    }

    public Map<Constants.GameType, List<Integer>> getAchievementsSucceed() {
        return achievementsSucceed;
    }

    public List<Integer> getAchievementsByGameType(Constants.GameType gameType) {
        return this.achievementsSucceed.get(gameType);
    }

    public void addAchievement(Constants.GameType gameType, int id) {
        this.achievementsSucceed.get(gameType).add(id);
    }

    public List<Integer> getItemsOwns() {
        return itemsOwns;
    }

    public void addItem(int itemId) {
        this.itemsOwns.add(itemId);
    }

    public void setRecord(Constants.GameType gameType, long value) {
        this.records.replace(gameType, value);
    }

    @Override
    public String toString() {
        return "Profile{" + "name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", profilePictureValue=" + profilePictureValue +
                ", records=" + records +
                '}';
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.descriptor = GSON.fromJson(Resources.readResource(inputStream), ProfileDescriptor.class);

        this.name = descriptor.getName();
        this.moneyAmount = descriptor.getMoneyAmount();
        this.profilePictureValue = descriptor.getAvatarIndex();
        this.records = descriptor.getTimeRecords();
        this.achievementsSucceed = descriptor.getAchievements();
        this.itemsOwns = descriptor.getItemsOwned();
    }

    @Override
    public void dispose() {
        this.descriptor = new ProfileDescriptor(name, moneyAmount, profilePictureValue, records, achievementsSucceed, itemsOwns);

        try (FileWriter fw = new FileWriter(Constants.PROFILE_FILE_PATH)) {
            GSON.toJson(this.descriptor, fw);
        } catch (IOException e) {
            Log.SYSTEM.severe("Couldn't save profile info");
        }
    }

    @Override
    public boolean validate() {
        return descriptor != null;
    }
}
