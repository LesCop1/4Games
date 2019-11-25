package fr.bcecb.poker;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.PopUpScreenState;
import fr.bcecb.state.gui.RoundedButton;
import fr.bcecb.state.gui.RoundedRectangle;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class PokerGameEnds extends PopUpScreenState {
    private Poker poker;
    private int playerWinner;

    public PokerGameEnds(StateManager stateManager, Poker poker, int playerWinner) {
        super(stateManager, "game_poker_turn");
        this.poker = poker;
        this.playerWinner = playerWinner;

        if (this.playerWinner != -1) {
            endGame();
        }

        setBackground(180, 130, Constants.COLOR_LIGHT_GREY);
    }

    private void endGame() {
        this.poker.getPlayer(this.playerWinner).addToBankroll(this.poker.getOnTableAmount());
        this.poker.endGame();
    }

    @Override
    public void initGui() {

        Text title = new Text(500, (width / 2f), (height / 2f) - 55, true, null) {
            @Override
            public String getText() {
                return formatPlayerWinnerText();
            }
        };

        if (this.poker.getNumGame() == 0) {
            Text defaultBankAmount = new Text(503, (width / 2f), (height / 2f) - 30f, true, "Mise de départ : " + (Poker.DEFAULT_BANKROLL + Constants.MONEY_NAME_SHORT), 1f);
            addGuiElement(defaultBankAmount);
        } else {
            Text howPlayerWon = new Text(503, (width / 2f) - 80f, (height / 2f) - 35f, false, "Carte du joueur gagnant", 0.7f);
            for (int i = 0; i < Poker.START_NUM_CARD; i++) {
                int finalI = i;
                RoundedButton cards = new RoundedButton(550 + finalI, (width / 2f) + 50f + (finalI * 10), (height / 2f) - 35f, 9, 4.5f, 5f, true, null, null) {
                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return PokerScreen.getCardTexture(poker.getPlayer(playerWinner).getCard(finalI));
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        return null;
                    }

                    @Override
                    public String getTitle() {
                        return PokerScreen.getCardTitle(poker.getPlayer(playerWinner).getCard(finalI));
                    }

                    @Override
                    public float getTitleScale() {
                        return 0.2f;
                    }

                    @Override
                    public Vector4f getTitleColor() {
                        return Constants.COLOR_WHITE;
                    }
                };
                addGuiElement(cards);
            }
            Text betOnTableTitle = new Text(504, (width / 2f) - 80f, (height / 2f) - 22.5f, false, "Argent sur la table", 0.7f);
            Text betOnTable = new Text(506, (width / 2f) + 60f, (height / 2f) - 22.5f, false, poker.getOnTableAmount() + Constants.MONEY_NAME_SHORT, 0.7f);

            addGuiElement(howPlayerWon, betOnTableTitle, betOnTable);
        }

        RoundedRectangle lineSeparation = new RoundedRectangle(507, (width / 2f), (height / 2f) - 15f, 160, 1, true, Constants.COLOR_GREY, Float.MAX_VALUE);

        Text secondPartTitle = new Text(508, (width / 2f), (height / 2f) - 7.5f, true, "Argent", 0.7f);

        float y = 27.5f / this.poker.getPlayerAmount() / 2f;
        float ySpacing = 27.5f / this.poker.getPlayerAmount();

        for (int i = 0; i < this.poker.getPlayerAmount(); i++) {
            Text playerName = new Text(510 + i, (width / 2f) - 80f, (height / 2f) - 2.5f + y + (i * ySpacing), false, "Joueur " + (i + 1), 0.4f);
            Text playerNameBank = new Text(520 + i, (width / 2f) + 60f, (height / 2f) - 2.5f + y + (i * ySpacing), false, this.poker.getPlayer(i).getBankroll() + Constants.MONEY_NAME_SHORT, 0.4f);
            addGuiElement(playerName, playerNameBank);
        }

        RoundedButton startNewGameButton = new RoundedButton(530, (width / 2f), (height / 2f) + 50, 80, 25, 5f, true, null, null) {
            @Override
            public String getTitle() {
                return "Start game " + (poker.getNumGame() + 1);
            }
        };

        addGuiElement(title, lineSeparation, secondPartTitle, startNewGameButton);
    }


    private String formatPlayerWinnerText() {
        if (this.playerWinner != -1) {
            return "Joueur " + (this.playerWinner + 1) + " gagne la manche";
        } else {
            return "Le Poker commence";
        }
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == 530) {
            this.poker.initGame();
            stateManager.popState();
            stateManager.rebuildGui(width, height);
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldRenderBelow() {
        return true;
    }

    @Override
    public boolean shouldPauseBelow() {
        return true;
    }
}
