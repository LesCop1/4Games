package fr.bcecb.poker;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.*;

public class PokerScreen extends ScreenState {
    private static final ResourceHandle<Texture> BACKGROUND = new ResourceHandle<>("textures/poker_background.jpg") {};
    private final Poker poker;
    private int playerCount;

    private Button followButton;
    private Button betButton;
    private Button checkButton;
    private Button bedButton;

    public PokerScreen(StateManager stateManager, int playerAmount) {
        super(stateManager, "poker_game");
        this.playerCount = playerAmount;
        this.poker = new Poker(this.playerCount);

        this.poker.initGame();
    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return BACKGROUND;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
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

//        for (int i = 0; i < 5; i++) {
//            RoundedButton middleCard = new RoundedButton((40 + i), ((width / 2f) - (115 * 2)) + (115 * i), height / 2f, 110, 150, 5f, Resources.DEFAULT_TEXTURE);
//            addGuiElement(middleCard);
//        }

        Text numTurns = new Text(40, (width / 8f), 5 * (height / 6f), true, null) {
            @Override
            public String getText() {
                return "Tour n " + poker.getNumTurns();
            }
        };

        this.followButton = new Button(50, (width / 2f) + 52.5f, height - 60, 40, 20, false, "Suivre");

        this.betButton = new Button(51, (width / 2f) + 97.5f, height - 60, 40, 20, false, "Relancer");

        this.checkButton = new Button(52, (width / 2f) + 52.5f, height - 35, 40, 20, false, "Check");

        this.bedButton = new Button(53, (width / 2f) + 97.5f, height - 35, 40, 20, false, "Se coucher");

        GuiElement backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");

        addGuiElement(numTurns, this.followButton, this.betButton, this.checkButton, this.bedButton, backButton);
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
            public String getTitle() {
                return getCardTitle(poker.getPlayer(((poker.getCurrentPlayer() + order) % playerCount)).getCard(0));
            }
        };

        RoundedButton botSecondCard = new RoundedButton(1, (width / 2f) + 5, height - 60, 40, 60, 5f, null) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return getCardTexture(poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getCard(1));
            }

            @Override
            public String getTitle() {
                return getCardTitle(poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getCard(1));
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

        RoundedImage topFirstCard = new RoundedImage(2, null, (width / 2f) - 42.5f, 0, 40, 60, false, false, 5f);

        RoundedImage topSecondCard = new RoundedImage(3, null, (width / 2f) + 2.5f, 0, 40, 60, false, false, 5f);

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

        RoundedImage leftFirstCard = new RoundedImage(4, null, 0, (height / 2f) - 42.5f, 60, 40, false, false, 5f);

        RoundedImage leftSecondCard = new RoundedImage(5, null, 0, (height / 2f) + 2.5f, 60, 40, false, false, 5f);

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

        RoundedImage rightFirstCard = new RoundedImage(6, null, width - 60, (height / 2f) - 40, 60, 40, false, false, 5f);

        RoundedImage rightSecondCard = new RoundedImage(7, null, width - 60, (height / 2f) + 5, 60, 40, false, false, 5f);

        Text rightBankAmountText = new Text(14, width - 60, (height / 2f) + 57.5f, false, null) {
            @Override
            public String getText() {
                return "Bank : " + poker.getPlayer((poker.getCurrentPlayer() + order) % playerCount).getBankroll();
            }
        };

        addGuiElement(rightPlayerTitle, rightFirstCard, rightSecondCard, rightBankAmountText);
    }

    private ResourceHandle<Texture> getCardTexture(Deck.Card card) {
        Deck.Type type = card.getType();
        if (type.equals(Deck.Type.SPADE)) {
            return new ResourceHandle<>("textures/pique.png") {};
        } else if (type.equals(Deck.Type.HEART)) {
            return new ResourceHandle<>("textures/heart.png") {};
        } else if (type.equals(Deck.Type.CLUB)) {
            return new ResourceHandle<>("textures/club.png") {};
        } else if (type.equals(Deck.Type.DIAMOND)) {
            return new ResourceHandle<>("textures/diamond.png") {};
        } else {
            return null;
        }
    }

    private String getCardTitle(Deck.Card card) {
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
        } else {
            return null;
        }
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
}
