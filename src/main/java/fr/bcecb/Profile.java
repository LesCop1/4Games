package fr.bcecb;

import fr.bcecb.util.Constants;
import fr.bcecb.util.JsonLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile {
    private String name;
    private Long moneyAmount;
    private Long profilePictureValue;
    private Map<Constants.GameType, Long> records = new HashMap<>();
    private Map<Constants.GameType, List<Integer>> achievementsSucceed = new HashMap<>();
    private List<Integer> itemsOwns = new ArrayList<>();

    public Profile() {
        this.name = "Default";
        this.moneyAmount = 0L;
        this.profilePictureValue = 0L;
        for (Constants.GameType gameType : Constants.GameType.values()) {
            this.records.put(gameType, 0L);
            this.achievementsSucceed.put(gameType, new ArrayList<>());
        }
    }

    public void loadProfile() {
        JSONObject profileJSON = JsonLoader.load(Constants.PROFILE_FILE_PATH);

        this.name = (String) profileJSON.get(Info.NAME.getValue());
        System.out.println(profileJSON.get(Info.MONEY.getValue()).getClass());
        this.moneyAmount = (Long) profileJSON.get(Info.MONEY.getValue());
        this.profilePictureValue = (Long) profileJSON.get(Info.PROFILE_PIC.getValue());

        JSONArray recordsJSON = (JSONArray) profileJSON.get(Info.RECORDS.getValue());
        for (Constants.GameType gameType : Constants.GameType.values()) {
            this.records.put(gameType, (Long) recordsJSON.get(gameType.ordinal()));
        }
    }

    public void save() {
        JSONObject profile = new JSONObject();

        profile.put(Info.NAME.getValue(), this.name);
        profile.put(Info.MONEY.getValue(), this.moneyAmount);
        profile.put(Info.PROFILE_PIC.getValue(), this.profilePictureValue);

        JSONArray records = new JSONArray();
        for (Constants.GameType gameType : Constants.GameType.values()) {
            records.add(gameType.ordinal(), this.records.get(gameType));
        }

        profile.put(Info.RECORDS.getValue(), records);

        try (FileWriter file = new FileWriter(Constants.PROFILE_FILE_PATH)) {
            file.write(profile.toJSONString());
        } catch (Exception e) {
            System.out.println(e);
        }
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
        return this.profilePictureValue.intValue();
    }

    public void setProfilePictureValue(Long profilePictureValue) {
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
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("name='").append(name).append('\'');
        sb.append(", moneyAmount=").append(moneyAmount);
        sb.append(", profilePictureValue=").append(profilePictureValue);
        sb.append(", records=").append(records);
        sb.append('}');
        return sb.toString();
    }

    private enum Info {
        NAME("name"),
        MONEY("moneyAmount"),
        PROFILE_PIC("profilePictureValue"),
        RECORDS("records");

        private String value;

        Info(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
