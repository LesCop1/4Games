package fr.bcecb.poker;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
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
        Button botFirstCard = new Button(1, width / 2 - 40, height - 60, 40, 60, false, "", BACK_CARD_TEXTURE);

        Button botSecondCard = new Button(2, width / 2 + 5, height - 60, 40, 60, false, "", BACK_CARD_TEXTURE);

        Text botPlayerTitle = new Text(18, width / 2, height - 70, true, "Joueur 1") {
            @Override
            public String getText() {
                return super.getText();
            }
        };

        Text botPoint = new Text(22, 580, height - 90, true, "bank:500");

        Button topFirstCard = new Button(3, (width / 2f) - 42.5f, 0, 40, 60, false, "", BACK_CARD_TEXTURE);

        Button topSecondCard = new Button(4, (width / 2f) + 2.5f, 0, 40, 60, false, "", BACK_CARD_TEXTURE);

        Text topPlayerTitle = new Text(19, width / 2f, 70, true, "Joueur 2") {
            @Override
            public String getText() {
                return super.getText();
            }
        };

        Text topPoint = new Text(23, 580, 90, true, "bank:500");

        this.playersUI[0] = botFirstCard;
        this.playersUI[1] = botSecondCard;
        this.playersUI[2] = botPlayerTitle;
        this.playersUI[3] = botPoint;

        this.playersUI[4] = topFirstCard;
        this.playersUI[5] = topSecondCard;
        this.playersUI[6] = topPlayerTitle;
        this.playersUI[7] = topPoint;

        if (this.numPlayers > 2) {
            Button leftFirstCard = new Button(5, 0, height / 2 - 42.5f, 60, 40, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Button leftSecondCard = new Button(6, 0, height / 2 + 2.5f, 60, 40, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });


            //topPlayerTitle.setText("Joueur 3");

            Text leftPlayerTitle = new Text(20, 0, (height / 2) - 47.5f, false, "Joueur 2") {
                @Override
                public String getText() {
                    return super.getText();
                }
            };

            Text leftPoint = new Text(24, -80, 490, false, "bank:500");

            this.playersUI[8] = leftFirstCard;
            this.playersUI[9] = leftSecondCard;
            this.playersUI[10] = leftPlayerTitle;
            this.playersUI[11] = leftPoint;
        }

        if (this.numPlayers > 3) {
            Button rightFirstCard = new Button(7, width - 60, height / 2 - 40, 60, 40, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Button rightSecondCard = new Button(8, width - 60, height / 2 + 5, 60, 40, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
            });

            Text rightPlayerTitle = new Text(21, width - 60, height / 2 - 47.5f, false, "Joueur 4") {
                @Override
                public String getText() {
                    return "Joueur " + poker.getCurrentPlayer();
                }
            };

            Text rightBank = new Text(25, width - 340, 490, false, "bank:500");
            addGuiElement(rightBank);


            this.playersUI[12] = rightFirstCard;
            this.playersUI[13] = rightSecondCard;
            this.playersUI[14] = rightPlayerTitle;
            this.playersUI[15] = rightBank;
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
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                poker.updateGame(this);
            }
        };

        Button relaunch_button = new Button(15, 1020, height - 150, 110, 50, false, "Relancer", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                poker.updateGame(this);
            }
        };

        Button checkButton = new Button(16, 900, height - 90, 110, 50, false, "Check", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                poker.updateGame(this);
            }
        };

        Button bedButton = new Button(17, 1020, height - 90, 110, 50, false, "Se coucher", new ResourceHandle<Texture>("textures/bet_texture.jpg") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                poker.updateGame(this);
            }
        };


        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };


        for (GuiElement playerUI : playersUI) playerUI.setDisabled(false);

        addGuiElement(this.playersUI);

        addGuiElement(backButton, middle_card1, middle_card2, middle_card3, middle_card4, middle_card5, follow_button, relaunch_button, bedButton, checkButton);


        for (int i = 0; i < poker.START_NUM_CARD; i++) {

            //        ((Button) this.playersUI[i]).setTexture(getCardTexture(poker.getPlayers().get(0).getHand().getCards().get(i)));
            //      ((Button) this.playersUI[i]).setTitle("" + getCardNum(poker.getPlayers().get(0).getHand().getCards().get(i).getNum()));
            //    ((Button) this.playersUI[i]).setTitleColor(COLOR_WHITE);


            // botSecondCard.setTexture(getCardTexture(poker.getPlayers().get(0).getHand().getCards().get(1)));
            //botSecondCard.setTitle("" + getCardNum(poker.getPlayers().get(0).getHand().getCards().get(1).getNum()));
            //botSecondCard.setTitleColor(COLOR_WHITE);
        }


    }

}
