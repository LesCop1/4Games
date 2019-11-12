package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;

public class SettingsBingoState extends ScreenState {
    private int nbGrids;
    private int difficulty;


    public SettingsBingoState() {
        super("bingoSettings");

    }

    @Override
    public void onEnter() {
        super.onEnter();
        System.out.println("entersetting");
        this.nbGrids = 0;
        this.difficulty = 0;
    }

    @Override
    public void update() {
        super.update();


    }

    @Override
    public void onExit() {
        super.onExit();
        Game.instance().getStateEngine().pushState(new BingoState(this.nbGrids, this.difficulty));
        // clignotement ( le state pop puis le jeu s'affiche, laisse apparaître le menu de sélection de jeu le temps  d'une fraction de seconde
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/bingo/bingoBG.png") {
        });

        GuiElement option1Button1 = new Button(1,
                (width / 10f), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "1", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 1;
        });

        GuiElement option1Button2 = new Button(2,
                (width / 10f) + (width / 10f), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "2", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 2;
        });

        GuiElement option1Button3 = new Button(3,
                (width / 10f) + (2 * (width / 10f)), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "3", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 3;
        });

        GuiElement option1Button4 = new Button(4,
                (width / 10f) + (3 * (width / 10f)), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "4", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 4;
        });

        GuiElement option1Button5 = new Button(5,
                (width / 10f) + (4 * (width / 10f)), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "5", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 5;
        });

        GuiElement option1Button6 = new Button(6,
                (width / 10f) + (5 * (width / 10f)), (height / 6f) + 10,
                (width / 20f), (height / 10f),
                true, "6", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            this.nbGrids = 6;
        });

        GuiElement option2Button1 = new Button(7,
                (width / 10f), (height / 6f) + (2 * height / 6f),
                (width / 20f), (height / 10f),
                true, "E", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> this.difficulty = 1);

        GuiElement option2Button2 = new Button(8,
                (width / 10f) + (width / 10f), (height / 6f) + (2 * height / 6f),
                (width / 20f), (height / 10f),
                true, "N", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> this.difficulty = 2);

        GuiElement option2Button3 = new Button(9,
                (width / 10f) + 2 * (width / 10f), (height / 6f) + (2 * height / 6f),
                (width / 20f), (height / 10f),
                true, "H", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> this.difficulty = 3);


        GuiElement startButton = new Button(10,
                (width / 2f), (height / 4f) + 2 * (height / 4f),
                (width / 5f), (height / 10f),
                true, "Start", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            if (this.difficulty != 0 && this.nbGrids != 0) {
              Game.instance().getStateEngine().popState();
            }
        });

        GuiElement backButton = new Button(11,
                (width / 20f), (height - (height / 20f) - (height / 10f)),
                (height / 10f), (height / 10f),
                false, "Back", new ResourceHandle<Texture>("textures/defaultButton.png") {
        }).setClickHandler(e -> {
            Game.instance().getStateEngine().popState();
            Game.instance().getStateEngine().popState();
        });

        addGuiElement(option1Button1);
        addGuiElement(option1Button2);
        addGuiElement(option1Button3);
        addGuiElement(option1Button4);
        addGuiElement(option1Button5);
        addGuiElement(option1Button6);
        addGuiElement(option2Button1);
        addGuiElement(option2Button2);
        addGuiElement(option2Button3);
        addGuiElement(startButton);
        addGuiElement(backButton);
    }
}
