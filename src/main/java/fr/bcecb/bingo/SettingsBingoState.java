package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class SettingsBingoState extends ScreenState {
    private int nbGrids;
    private int difficulty;
    private ResourceHandle<Texture> BUTTON_TEXTURE = new ResourceHandle<>("textures/defaultButton.png") {
    };

    public SettingsBingoState() {
        super("bingoSettings");

    }

    @Override
    public void onEnter() {
        super.onEnter();
        System.out.println("entersettings");
        this.nbGrids = 0;
        this.difficulty = 0;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();


    }

    @Override
    public void onExit() {
        super.onExit();
        Game.instance().getStateManager().pushState(new BingoState(this.nbGrids, this.difficulty));

        // clignotement ( le state pop puis le jeu s'affiche, laisse apparaître le menu de sélection de jeu le temps  d'une fraction de seconde
    }

    @Override
    public void initGui() {
        int nbGridsChoice = 1,difficultyChoice =7;

        setBackgroundTexture(new ResourceHandle<>("textures/bingo/bingoBG.png") {
        });

        for (int i = 0; i < 6; i++, nbGridsChoice++) {
            int finalId = nbGridsChoice;
            GuiElement optionButtonX = new Button(finalId,(width/10f)+ i * (width/10f),(height/6f)+10,
                    (width/20f),(height/10f),
                    true, String.valueOf(nbGridsChoice),BUTTON_TEXTURE){
                @Override
                public void onClick(MouseEvent.Click event) {
                    nbGrids= finalId;
                }
            };
            addGuiElement(optionButtonX);
        }

        for (int i = 0; i < 3; i++,difficultyChoice++) {
            int finalId = difficultyChoice;
            GuiElement optionButtonX = new Button(finalId,(width/10f)+ i * (width/10f),2*(height/6f)+10,
                    (width/20f),(height/10f),
                    true,String.valueOf(difficultyChoice-6),BUTTON_TEXTURE){
                @Override
                public void onClick(MouseEvent.Click event) {
                    difficulty= finalId-6;
                }
            };
            addGuiElement(optionButtonX);
        }


        GuiElement startButton = new Button(12,
                (width / 2f), (height / 4f) + 2 * (height / 4f),
                (width / 5f), (height / 10f),
                true, "Start", new ResourceHandle<>("textures/defaultButton.png") {
        }){
            @Override
            public void onClick(MouseEvent.Click event) {
                if (difficulty != 0 && nbGrids != 0) {
                    Game.instance().getStateManager().popState();
                }
            }
        };

        GuiElement backButton = new Button(13,
                (width / 20f), (height - (height / 20f) - (height / 10f)),
                (height / 10f), (height / 10f),
                false, "Back", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }){
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(startButton,backButton);
    }
}
