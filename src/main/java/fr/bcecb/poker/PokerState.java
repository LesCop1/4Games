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

public class PokerState extends ScreenState {
    private static final ResourceHandle<Texture> BACKGROUND = new ResourceHandle<>("textures/poker_background.jpg") {
    };
    private final Poker poker;

    public PokerState(int playerAmount) {
        super("poker_game");
        this.poker = new Poker(playerAmount);

    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return BACKGROUND;
    }

    @Override
    public void initGui() {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();

        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().popState());
        addGuiElement(backButton);

        GuiElement card1_1 = new Button(1, 650, height - 150, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        card1_1.setDisabled(true);

        GuiElement card1_2 = new Button(2, 765, height - 150, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        card1_2.setDisabled(true);

        GuiElement card2_1 = new Button(3, 0, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        card2_1.setDisabled(true);

        GuiElement card2_2 = new Button(4, 0, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        card2_2.setDisabled(true);

        GuiElement card3_1 = new Button(5, 650, 0, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        card3_1.setDisabled(true);

        GuiElement card3_2 = new Button(6, 765, 0, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        card3_2.setDisabled(true);
        addGuiElement(card3_2);

        GuiElement card4_1 = new Button(7, width - 150, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        card4_1.setDisabled(true);

        GuiElement card4_2 = new Button(8, width - 150, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        card4_2.setDisabled(true);

        GuiElement middle_card1 = new Button(9, width / 2 - 115 * 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card1.setDisabled(true);

        GuiElement middle_card2 = new Button(10, width / 2 - 115, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card2.setDisabled(true);

        GuiElement middle_card3 = new Button(11, width / 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card3.setDisabled(true);
        addGuiElement(middle_card3);

        GuiElement middle_card4 = new Button(12, width / 2 + 115, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card4.setDisabled(true);

        GuiElement middle_card5 = new Button(13, width / 2 + 115 * 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        middle_card5.setDisabled(true);

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

        GuiElement botPlayer = new Text(18, width / 2, height - 165, "Joueur 1", true);

        GuiElement leftPlayer = new Text(19, -80, 225, "Joueur 2", false);

        GuiElement topPlayer = new Text(20, width / 2, 165, "Joueur 3", true);

        GuiElement rightPlayer = new Text(21, width - 230, 225, "Joueur 4", false);
        addGuiElement(card1_1, card1_2, card2_1, card2_2, card3_1, card3_2, card4_1, card4_2, middle_card1, middle_card2, middle_card3, middle_card4, middle_card5, follow_button, relaunch_button, bedButton, checkButton, botPlayer, leftPlayer, topPlayer, rightPlayer);
    }
}
