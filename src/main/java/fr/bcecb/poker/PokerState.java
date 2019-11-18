package fr.bcecb.poker;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Resources;

import java.util.ArrayList;
import java.util.List;

import static fr.bcecb.util.Constants.COLOR_WHITE;

public class PokerState extends ScreenState {
    public static final List<GuiElement> middleCards = new ArrayList<>();
    private static final ResourceHandle<Texture> BACK_CARD_TEXTURE = new ResourceHandle<Texture>("textures/back_card.png") {
    };
    private int numPlayers;
    private static final ResourceHandle<Texture> BACKGROUND = new ResourceHandle<>("textures/poker_background.jpg") {
    };
    private GuiElement[] playersUI;
    private final Poker poker;

    public PokerState(int playerAmount) {
        super("poker_game");
        this.numPlayers = playerAmount;
        this.playersUI = new GuiElement[this.numPlayers * 4];
        this.poker = new Poker(this.numPlayers);
        poker.initGame();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return BACKGROUND;
    }

    public ResourceHandle<Texture> getCardTexture(Deck.Card card) {
        if (card.getType().equals(Deck.Type.SPADE)) return new ResourceHandle<Texture>("textures/pique.png") {
        };
        if (card.getType().equals(Deck.Type.HEART)) return new ResourceHandle<Texture>("textures/heart.png") {
        };
        if (card.getType().equals(Deck.Type.CLUB)) return new ResourceHandle<Texture>("textures/club.png") {
        };
        return new ResourceHandle<Texture>("textures/diamond.png") {
        };

    }

    public String getCardNum(int nb) {
        if (nb < 11) return "" + nb;
        if (nb == 11) return "J";
        if (nb == 12) return "Q";
        if (nb == 13) return "K";
        return "A";
    }

    @Override
    public void initGui() {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();

        Button botFirstCard = new Button(1, 650, height - 150, 110, 150, false, "", BACK_CARD_TEXTURE);

        Button botSecondCard = new Button(2, 765, height - 150, 110, 150, false, "", BACK_CARD_TEXTURE);

        Text botPlayerTitle = new Text(18, width / 2, height - 165, "Joueur 1", true);

        Text botPoint = new Text(22, 580, height - 90, "point:500", true);

        Button topFirstCard = new Button(3, 650, 0, 110, 150, false, "", BACK_CARD_TEXTURE);

        Button topSecondCard = new Button(4, 765, 0, 110, 150, false, "", BACK_CARD_TEXTURE);

        Text topPlayerTitle = new Text(19, width / 2, 165, "Joueur 2", true);

        Text topPoint = new Text(23, 580, 90, "point:500", true);

        this.playersUI[0] = botFirstCard;
        this.playersUI[1] = botSecondCard;
        this.playersUI[2] = botPlayerTitle;
        this.playersUI[3] = botPoint;

        this.playersUI[4] = topFirstCard;
        this.playersUI[5] = topSecondCard;
        this.playersUI[6] = topPlayerTitle;
        this.playersUI[7] = topPoint;

        if (this.numPlayers > 2) {
            Button leftFirstCard = new Button(5, 0, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Button leftSecondCard = new Button(6, 0, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            topPlayerTitle.setText("Joueur 3");

            Text leftPlayerTitle = new Text(20, -80, 225, "Joueur 2", false);

            Text leftPoint = new Text(24, -80, 490, "point:500", false);

            this.playersUI[8] = leftFirstCard;
            this.playersUI[9] = leftSecondCard;
            this.playersUI[10] = leftPlayerTitle;
            this.playersUI[11] = leftPoint;
        }

        if (this.numPlayers > 3) {
            Button rightFirstCard = new Button(7, width - 150, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Button rightSecondCard = new Button(8, width - 150, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Text rightPlayerTitle = new Text(21, width - 350, 225, "Joueur 4", false);

            Text rightPoint = new Text(25, width - 340, 490, "point:500", false);
            addGuiElement(rightPoint);

            ((Text) this.playersUI[6]).setText("Joueur 3");
            ((Text) this.playersUI[10]).setText("Joueur 2");

            this.playersUI[12] = rightFirstCard;
            this.playersUI[13] = rightSecondCard;
            this.playersUI[14] = rightPlayerTitle;
            this.playersUI[15] = rightPoint;
        }

        Button middle_card1 = new Button(9, width / 2 - 115 * 2, height / 2, 110, 150, true, "", BACK_CARD_TEXTURE);
        middle_card1.setDisabled(true);
        middleCards.add(middle_card1);

        Button middle_card2 = new Button(10, width / 2 - 115, height / 2, 110, 150, true, "", BACK_CARD_TEXTURE);
        middle_card2.setDisabled(true);
        middleCards.add(middle_card2);

        Button middle_card3 = new Button(11, width / 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card3.setDisabled(true);
        middleCards.add(middle_card3);

        Button middle_card4 = new Button(12, width / 2 + 115, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card4.setDisabled(true);
        middleCards.add(middle_card4);

        Button middle_card5 = new Button(13, width / 2 + 115 * 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card5.setDisabled(true);
        middleCards.add(middle_card5);

        for (GuiElement middleCard : middleCards) middleCard.setVisible(false);

        Button follow_button = new Button(14, 900, height - 150, 110, 50, false, "Suivre", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        });
        follow_button.setTitleScale(0.8f);

        Button relaunch_button = new Button(15, 1020, height - 150, 110, 50, false, "Relancer", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        });
        relaunch_button.setTitleScale(0.8f);

        Button checkButton = new Button(16, 900, height - 90, 110, 50, false, "Check", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        });
        checkButton.setTitleScale(0.8f);

        Button bedButton = new Button(17, 1020, height - 90, 110, 50, false, "Se coucher", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        });
        bedButton.setTitleScale(0.8f);


        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().popState());

        for (GuiElement playerUI : playersUI) playerUI.setDisabled(true);

        addGuiElement(this.playersUI);

        addGuiElement(backButton, middle_card1, middle_card2, middle_card3, middle_card4, middle_card5, follow_button, relaunch_button, bedButton, checkButton);


        for (int i = 0; i < poker.START_NUM_CARD; i++) {

            ((Button) this.playersUI[i]).setTexture(getCardTexture(poker.getPlayers().get(0).getHand().getCards().get(i)));
            ((Button) this.playersUI[i]).setTitle("" + getCardNum(poker.getPlayers().get(0).getHand().getCards().get(i).getNum()));
            ((Button) this.playersUI[i]).setTitleColor(COLOR_WHITE);


            // botSecondCard.setTexture(getCardTexture(poker.getPlayers().get(0).getHand().getCards().get(1)));
            //botSecondCard.setTitle("" + getCardNum(poker.getPlayers().get(0).getHand().getCards().get(1).getNum()));
            //botSecondCard.setTitleColor(COLOR_WHITE);
        }


    }

}
