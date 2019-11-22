package fr.bcecb.poker;

import com.google.common.base.Stopwatch;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;
import fr.bcecb.util.MathHelper;
import org.joml.Vector4f;

import java.util.concurrent.TimeUnit;

public class PokerScreen extends ScreenState {
    private final Poker poker;
    private int playerCount;

    private Button followButton;
    private Button betButton;
    private Button checkButton;
    private Button bedButton;

    private Stopwatch stopwatch;

    public PokerScreen(StateManager stateManager, int playerAmount) {
        super(stateManager, "poker_game");
        this.playerCount = playerAmount;
        this.poker = new Poker(this.playerCount);
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return Constants.POKER_BACKGROUND;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (poker.doPokerEnds()) {
            this.stopwatch.stop();
            long time = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
            stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.POKER, time, calculatePoints()));
        }
    }

    @Override
    public void initGui() {
        drawBottomPlayerUI(0);
        switch (this.playerCount) {
            case 2 -> drawTopPlayerUI(1);
            case 3 -> {
                drawTopPlayerUI(1);
                drawLeftPlayerUI(2);
            }
            case 4 -> {
                drawRightPlayerUI(1);
                drawTopPlayerUI(2);
                drawLeftPlayerUI(3);
            }
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            RoundedButton middleCard = new RoundedButton((30 + finalI), ((width / 2f) - 110 + (45 * finalI)), height / 2f, 40, 60, 5f, true, null, null) {
                @Override
                public ResourceHandle<Texture> getTexture() {
                    return getCardTexture(poker.getTable().getCard(finalI));
                }

                @Override
                public ResourceHandle<Texture> getHoverTexture() {
                    return null;
                }

                @Override
                public String getTitle() {
                    return getCardTitle(poker.getTable().getCard(finalI));
                }

                @Override
                public float getTitleScale() {
                    return 1f;
                }

                @Override
                public Vector4f getTitleColor() {
                    return Constants.COLOR_WHITE;
                }

                @Override
                public boolean isVisible() {
                    return finalI < poker.getTable().size();
                }
            };

            addGuiElement(middleCard);
        }

        Text numTurns = new Text(40, (width / 8f), 4.5f * (height / 6f), true, null) {
            @Override
            public String getText() {
                return "Tour n " + poker.getNumTurns();
            }
        };

        Text onTableAmount = new Text(41, (width / 2f), (height / 2f) + 35, true, null) {
            @Override
            public String getText() {
                return "Mise :" + poker.getOnTableAmount();
            }
        };

        this.followButton = new Button(50, (width / 2f) + 52.5f, height - 60, 40, 20, false, "Suivre") {
            @Override
            public boolean isDisabled() {
                return poker.isNewTurn() || poker.doPokerEnds();
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                return Constants.POKER_BUTTON;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        this.betButton = new Button(51, (width / 2f) + 97.5f, height - 60, 40, 20, false, "Relancer") {
            @Override
            public boolean isDisabled() {
                return poker.doPokerEnds();
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                return Constants.POKER_BUTTON;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        this.checkButton = new Button(52, (width / 2f) + 52.5f, height - 35, 40, 20, false, "Check") {
            @Override
            public boolean isDisabled() {
                return poker.getPlayer(poker.getCurrentPlayer()).getOnTable() != poker.getCurrentHighestPlayerBet() || poker.doPokerEnds();
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                return Constants.POKER_BUTTON;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        this.bedButton = new Button(53, (width / 2f) + 97.5f, height - 35, 40, 20, false, "Se coucher") {
            @Override
            public boolean isDisabled() {
                return poker.doPokerEnds();
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                return Constants.POKER_BUTTON;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }
        };

        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(numTurns, onTableAmount, this.followButton, this.betButton, this.checkButton, this.bedButton, backButton);
    }

    private void drawBottomPlayerUI(int order) {
        Text botPlayerTitle = new Text(10, width / 2f, height - 70, true, null) {
            @Override
            public String getText() {
                return "Joueur " + (((poker.getCurrentPlayer() + order) % playerCount) + 1);
            }
        };

        RoundedButton botFirstCard = new RoundedButton(0, (width / 2f) - 40, height - 60, 40, 60, 5f, null) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return getCardTexture(poker.getPlayer(((poker.getCurrentPlayer() + order) % playerCount)).getCard(0));
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }

            @Override
            public String getTitle() {
                return getCardTitle(poker.getPlayer(((poker.getCurrentPlayer() + order) % playerCount)).getCard(0));
            }

            @Override
            public float getTitleScale() {
                return 1f;
            }

            @Override
            public Vector4f getTitleColor() {
                return Constants.COLOR_WHITE;
            }
        };

        RoundedButton botSecondCard = new RoundedButton(1, (width / 2f) + 5, height - 60, 40, 60, 5f, null) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return getCardTexture(poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getCard(1));
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }

            @Override
            public String getTitle() {
                return getCardTitle(poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getCard(1));
            }

            @Override
            public float getTitleScale() {
                return 1f;
            }

            @Override
            public Vector4f getTitleColor() {
                return Constants.COLOR_WHITE;
            }
        };

        Text botBankAmountText = new Text(20, (width / 2f) - 75, height - 50, true, null) {
            @Override
            public String getText() {
                return "Bank : " + poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getBankroll();
            }
        };

        addGuiElement(botPlayerTitle, botFirstCard, botSecondCard, botBankAmountText);
    }

    private void drawTopPlayerUI(int order) {
        Text topPlayerTitle = new Text(11, width / 2f, 70, true, null) {
            @Override
            public String getText() {
                return "Joueur " + (((poker.getCurrentPlayer() + order) % playerCount) + 1);
            }
        };

        RoundedImage topFirstCard = new RoundedImage(2, Constants.POKER_BACK_CARD, (width / 2f) - 42.5f, 0, 40, 60, false, false, 5f) {};

        RoundedImage topSecondCard = new RoundedImage(3, Constants.POKER_BACK_CARD, (width / 2f) + 2.5f, 0, 40, 60, false, false, 5f);

        Text topBankAmountText = new Text(21, (width / 2f) - 75, 50, true, null) {
            @Override
            public String getText() {
                return "Bank : " + poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getBankroll();
            }
        };

        addGuiElement(topPlayerTitle, topFirstCard, topSecondCard, topBankAmountText);
    }

    private void drawLeftPlayerUI(int order) {
        Text leftPlayerTitle = new Text(12, 0, (height / 2f) - 47.5f, false, null) {
            @Override
            public String getText() {
                return "Joueur " + (((poker.getCurrentPlayer() + order) % playerCount) + 1);
            }
        };

        RoundedImage leftFirstCard = new RoundedImage(4, Constants.POKER_HBACK_CARD, 0, (height / 2f) - 42.5f, 60, 40, false, false, 5f);

        RoundedImage leftSecondCard = new RoundedImage(5, Constants.POKER_HBACK_CARD, 0, (height / 2f) + 2.5f, 60, 40, false, false, 5f);

        Text leftBankAmountText = new Text(22, 0, (height / 2f) + 52.5f, false, null) {
            @Override
            public String getText() {
                return "Bank : " + poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getBankroll();
            }
        };

        addGuiElement(leftPlayerTitle, leftFirstCard, leftSecondCard, leftBankAmountText);
    }

    private void drawRightPlayerUI(int order) {
        Text rightPlayerTitle = new Text(13, width - 60, (height / 2f) - 47.5f, false, null) {
            @Override
            public String getText() {
                return "Joueur " + (((poker.getCurrentPlayer() + order) % playerCount) + 1);
            }
        };

        RoundedImage rightFirstCard = new RoundedImage(6, Constants.POKER_HBACK_CARD, width - 60, (height / 2f) - 40, 60, 40, false, false, 5f);

        RoundedImage rightSecondCard = new RoundedImage(7, Constants.POKER_HBACK_CARD, width - 60, (height / 2f) + 5, 60, 40, false, false, 5f);

        Text rightBankAmountText = new Text(14, width - 60, (height / 2f) + 57.5f, false, null) {
            @Override
            public String getText() {
                return "Bank : " + poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getBankroll();
            }
        };

        addGuiElement(rightPlayerTitle, rightFirstCard, rightSecondCard, rightBankAmountText);
    }

    private ResourceHandle<Texture> getCardTexture(Deck.Card card) {
        if (card != null) {
            Deck.Type type = card.getType();
            if (type.equals(Deck.Type.SPADE)) {
                return Constants.POKER_SPADE;
            } else if (type.equals(Deck.Type.HEART)) {
                return Constants.POKER_HEART;
            } else if (type.equals(Deck.Type.CLUB)) {
                return Constants.POKER_CLUB;
            } else if (type.equals(Deck.Type.DIAMOND)) {
                return Constants.POKER_DIAMOND;
            }
        }
        return null;
    }

    private String getCardTitle(Deck.Card card) {
        if (card != null) {
            int value = card.getNum();
            if (value < 11) {
                return String.valueOf(value);
            } else if (value == 11) {
                return "J";
            } else if (value == 12) {
                return "Q";
            } else if (value == 13) {
                return "K";
            } else if (value == 14) {
                return "A";
            }
        }
        return null;
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.followButton.getId()) {
            this.poker.update(Poker.ACTION_FOLLOW);
            return true;
        } else if (id == this.betButton.getId()) {
            this.poker.update(Poker.ACTION_BET);
            return true;
        } else if (id == this.checkButton.getId()) {
            this.poker.update(Poker.ACTION_CHECK);
            return true;
        } else if (id == this.bedButton.getId()) {
            this.poker.update(Poker.ACTION_BED);
            return true;
        }
        return false;
    }

    private int calculatePoints() {
        long time = this.stopwatch.elapsed(TimeUnit.SECONDS);
        time = time - (5 * 60);
        return (int) MathHelper.clamp((2000 - (time / 10f)), 600, 2000);
    }
}
