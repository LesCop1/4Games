package fr.bcecb.state;

import fr.bcecb.Achievement;
import fr.bcecb.Game;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Rectangle;
import fr.bcecb.state.gui.RoundedButton;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;

import java.util.List;
import java.util.Map;

public class AchievementScreen extends ScreenState {
    private Map<Constants.GameType, List<Achievement>> achievements;
    private int currentPage;
    private Constants.GameType selectedGameType;
    private int selectedAchievement;

    public AchievementScreen(StateManager stateManager) {
        super(stateManager, "achievement");
        setBackgroundTexture(null);

        this.achievements = Constants.achievements;
        this.currentPage = 1;
        this.selectedGameType = Constants.GameType.SUDOKU;
        this.selectedAchievement = -1;

        setBackgroundTexture(Resources.DEFAULT_BACKGROUND_TEXTURE);
    }

    @Override
    public void initGui() {
        Rectangle topBanner = new Rectangle(1, 0, 2 * (height / 20f), width, 3 * (height / 20f), false, Constants.COLOR_BANNER);
        Text title = new Text(2, (width / 2f), (height / 20f), true, "Succès", 2f);
        title.setColor(Constants.COLOR_WHITE);

        float gameButtonWidth = (width / 4f);
        float gameButtonStart = 2 * (height / 20f);
        float gameButtonHeight = 3 * (height / 20f);
        for (int i = 0; i < Constants.GameType.values().length; i++) {
            RoundedButton gameButton = new RoundedButton(20 + i, (i * gameButtonWidth) + 5, gameButtonStart + 5,
                    gameButtonWidth - 10, gameButtonHeight - 10,
                    2f, false, Constants.GameType.values()[i].getName(), Resources.DEFAULT_BUTTON_TEXTURE) {
                @Override
                public ResourceHandle<Texture> getTexture() {
                    if (this.getTitle().equals(selectedGameType.getName())) {
                        return Resources.DEFAULT_BUTTON_HOVER_TEXTURE;
                    } else {
                        return Resources.DEFAULT_BUTTON_DISABLED_TEXTURE;
                    }
                }
            };

            addGuiElement(gameButton);
        }

        float achievementStartY = gameButtonStart + gameButtonHeight;
        float achievementHeight = 4 * (height / 20f);
        float achievementWidth = 2 * (width / 3f) - 15;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            RoundedButton achievement = new RoundedButton(100 + finalI, 7.5f, achievementStartY + (finalI * achievementHeight) + 7.5f, achievementWidth,
                    achievementHeight - 15, 2f, Resources.DEFAULT_BUTTON_TEXTURE) {
                @Override
                public String getTitle() {
                    if (achievements.get(selectedGameType).size() > (((currentPage - 1) * 3) + finalI)) {
                        return achievements.get(selectedGameType).get(((currentPage - 1) * 3) + finalI).getTitle();
                    } else {
                        return "";
                    }
                }

                @Override
                public boolean isVisible() {
                    return achievements.get(selectedGameType).size() > (((currentPage - 1) * 3) + finalI);
                }

                @Override
                public ResourceHandle<Texture> getTexture() {
                    if (Game.instance().getProfile().getAchievementsByGameType(selectedGameType).contains(achievements.get(selectedGameType).get(((currentPage - 1) * 3) + finalI).getId())) {
                        return Resources.DEFAULT_BUTTON_CONFIRMED;
                    } else {
                        return Resources.DEFAULT_BUTTON_TEXTURE;
                    }
                }
            };

            addGuiElement(achievement);
        }

        float previewBGHeight = (3 * achievementHeight) - 15f;
        RoundedButton previewAchievementBG = new RoundedButton(6, 2 * (width / 3f), achievementStartY + 7.5f, (width / 3f) - 10f, (3 * achievementHeight) - 15f,
                2f, Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return getTexture();
            }

            @Override
            public boolean isVisible() {
                return selectedAchievement != -1;
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                if (Game.instance().getProfile().getAchievementsByGameType(selectedGameType).contains(achievements.get(selectedGameType).get(selectedAchievement).getId())) {
                    return Resources.DEFAULT_BUTTON_CONFIRMED;
                } else {
                    return Resources.DEFAULT_BUTTON_TEXTURE;
                }
            }
        };

        float previewCenter = achievementWidth + (width - achievementWidth) / 2;
        Text previewRewardTitle = new Text(7, previewCenter, achievementStartY + (height / 15f) + 2, true, "Récompense :") {
            @Override
            public boolean isVisible() {
                return selectedAchievement != -1;
            }
        };
        Text previewReward = new Text(8, previewCenter, achievementStartY + (previewBGHeight / 2) - 5f, true, "", 1.5f) {
            @Override
            public String getText() {
                return achievements.get(selectedGameType).get(selectedAchievement).getReward();
            }

            @Override
            public boolean isVisible() {
                return selectedAchievement != -1;
            }
        };
        Text previewTitle = new Text(9, previewCenter, achievementStartY + (3 * (previewBGHeight / 4f)), true, "", 1.1f) {
            @Override
            public String getText() {
                return achievements.get(selectedGameType).get(selectedAchievement).getTitle();
            }

            @Override
            public boolean isVisible() {
                return selectedAchievement != -1;
            }
        };
        Text previewHowTo = new Text(10, previewCenter, achievementStartY + (3 * (previewBGHeight / 4f)) + 15f, true, "", 0.8f) {
            @Override
            public String getText() {
                return achievements.get(selectedGameType).get(selectedAchievement).getHowTo();
            }

            @Override
            public boolean isVisible() {
                return selectedAchievement != -1;
            }
        };
        previewHowTo.setColor(Constants.COLOR_GREY);

        float backButtonY = achievementStartY + 3 * achievementHeight;
        RoundedButton backButton = new RoundedButton(3, (2 * (height / 20f) + 15) / 2, backButtonY + ((height - backButtonY) / 2), 2 * (height / 20f), 2 * (height / 20f), 5f, true, "Quitter", Resources.DEFAULT_BUTTON_TEXTURE);

        RoundedButton prevButton = new RoundedButton(4, (width / 5f), backButtonY + ((height - backButtonY) / 2), (width / 5f) - 4, 2 * (height / 20f),
                2f, true, "Retour", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public boolean isVisible() {
                return currentPage > 1;
            }
        };
        RoundedButton nextButton = new RoundedButton(5, 2 * (width / 5f), backButtonY + ((height - backButtonY) / 2), (width / 5f) - 4, 2 * (height / 20f),
                2f, true, "Suivant", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public boolean isVisible() {
                return achievements.get(selectedGameType).size() / 3f > currentPage;
            }
        };

        addGuiElement(topBanner, title, backButton, prevButton, nextButton, previewAchievementBG, previewRewardTitle, previewReward, previewTitle, previewHowTo);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == 3) {
            this.stateManager.popState();
            return true;
        } else if (id == 4) {
            this.currentPage--;
            return true;
        } else if (id == 5) {
            this.currentPage++;
            return true;
        } else if (id == 20) {
            this.selectedGameType = Constants.GameType.SUDOKU;
            this.selectedAchievement = -1;
            this.currentPage = 1;
            return true;
        } else if (id == 21) {
            this.selectedGameType = Constants.GameType.BINGO;
            this.selectedAchievement = -1;
            this.currentPage = 1;
            return true;
        } else if (id == 22) {
            this.selectedGameType = Constants.GameType.BATTLESHIP;
            this.selectedAchievement = -1;
            this.currentPage = 1;
            return true;
        } else if (id == 23) {
            this.selectedGameType = Constants.GameType.POKER;
            this.selectedAchievement = -1;
            this.currentPage = 1;
            return true;
        } else if (id >= 100) {
            this.selectedAchievement = ((currentPage - 1) * 3) + (id - 100);
            return true;
        }
        return false;
    }
}
