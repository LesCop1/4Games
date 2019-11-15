package fr.bcecb.poker;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
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

        GuiElement card1_1 = new Button(1, 650, height - 180, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(card1_1);

        GuiElement card1_2 = new Button(2, 765, height - 180, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(card1_2);

        GuiElement card2_1 = new Button(3, 0, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        addGuiElement(card2_1);

        GuiElement card2_2 = new Button(4, 0, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        addGuiElement(card2_2);

        GuiElement card3_1 = new Button(5, 650, 0, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(card3_1);

        GuiElement card3_2 = new Button(6, 765, 0, 110, 150, false, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(card3_2);

        GuiElement card4_1 = new Button(7, width - 180, 280, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        addGuiElement(card4_1);

        GuiElement card4_2 = new Button(8, width - 180, 395, 150, 110, false, "", new ResourceHandle<Texture>("textures/horizontal_back_card.png") {
        });
        addGuiElement(card4_2);

        GuiElement middle_card1 = new Button(9, width / 2 - 115 * 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(middle_card1);

        GuiElement middle_card2 = new Button(10, width / 2 - 115, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(middle_card2);

        GuiElement middle_card3 = new Button(11, width / 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(middle_card3);

        GuiElement middle_card4 = new Button(12, width / 2 + 115, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(middle_card4);

        GuiElement middle_card5 = new Button(13, width / 2 + 115 * 2, height / 2, 110, 150, true, "", new ResourceHandle<Texture>("textures/back_card.png") {
        });
        addGuiElement(middle_card5);
    }
}
