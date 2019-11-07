package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.Image;
import fr.bcecb.state.gui.ScreenState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BingoState extends ScreenState {
    private int tickMultiplier = 0;
    private int ticks = 0;
    private int nbGrids;
    private List<Integer> numberList = new ArrayList<>();
    private List<Player> bots = new ArrayList<>();
    private final static int NB_BOTS = 5;
    private Player player;
    private int lastDrop;

    public BingoState(int nbGrids, int difficulty) {
        super("bingo");
        this.nbGrids = nbGrids;
        switch (difficulty) {
            case 1:
                this.tickMultiplier = 6;
                break;
            case 2:
                this.tickMultiplier = 4;
                break;
            case 3:
                this.tickMultiplier = 1;
        }
        initGame();
        createBots();
    }

    private void initGame() {
        player = new Player(nbGrids);
        int i = 1;
        while (i < 91) {
            numberList.add(i - 1, i);
            i++;
        }
    }

    private void createBots() {
        Random rand = new Random();
        for (int j = 0; j < NB_BOTS; j++) {
            Player bot = new Player((rand.nextInt(5) + 1));
            bots.add(bot);
        }
    }

    @Override
    public void onEnter() {
        System.out.println(nbGrids);
        super.onEnter();
    }

    @Override
    public void onExit() {
        System.out.println("bobaye");
        super.onExit();
    }

    @Override
    public void update() {
        super.update();
        if (numberList.size() > 2) {
            if (++ticks > tickMultiplier * 60) {
                dropball();
                System.out.println(this.lastDrop);
                this.ticks = 0;
            }

        }else System.out.println("boulier vide");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/bingo/bingoBG.png") {
        });

        final GuiElement grid = new Image(1,new ResourceHandle<>("textures/bingo/gridBG.png"){},
                (width/3f),(height /2f),
                (width/2f),(height/5f),
                true,true);

        addGuiElement(grid);

        GuiElement backButton = new Button(1,
                (width / 20f), (height - (height / 20f) - (height / 10f)),
                (height / 10f), (height / 10f),
                false, "Back", new ResourceHandle<Texture>("textures/btn.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }


    private void dropball() {
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size()); // random de la taille de la liste

        this.lastDrop = numberList.get(randInt);
        numberList.remove(randInt);
    }


    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    @Override
    public boolean shouldUpdateBelow() {
        return false;
    }
}

