package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.*;
import org.joml.Vector4f;

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
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
        } else System.out.println("boulier vide");
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<Texture>("textures/bingo/bingoBG.png") {
        });
// nbGrids n'est pas encore récupéré, redimensionner la fenetre pour rentrer dans le if
// System.out.println("nbgrids : "+ nbGrids);
        switch (nbGrids) {

            case 1:
                float gridX = 1 * (width / 20f), gridY = 1 * (height / 2.5f);
                float gridW = (width / 3f), gridH = (height / 5f);

//                final GuiElement grid = new Image(1, new ResourceHandle<>("textures/bingo/gridBG.png") {
//                },
//                        gridX, gridY,
//                        gridW, gridH,
//                        false, false);


                int[][] playerGrid = player.getGrids().get(0).getGrid();
                int id=0;

                for( int i = 0 ; i < 3 ; i++){
                    for (int j = 0; j < 9; j++ , j++) {
                        GuiElement caseX = new Button(id,
                                (gridX + j*(gridW/5)),(gridY + i*(gridH/3)),
                                (gridW / 10), (gridH / 3),
                                false, Integer.toString(playerGrid[i][j]),new ResourceHandle<>("textures/bingo/caseBG.png") {
                        });
                        addGuiElement(caseX);
                    }
            }

               // addGuiElement(grid);
                break;


            case 2:
                break;

            case 3:
                break;

            case 4:
                break;

            case 5:
                break;

            case 6:
                break;

            default:
                Game.instance().getStateEngine().popState();
                break;
        }


        // GuiElement gridCase = new Button (10,);

        GuiElement backButton = new Button(3,
                (width / 20f), (height - (height / 20f) - (height / 10f)),
                (height / 10f), (height / 10f),
                false, "Back", new ResourceHandle<Texture>("textures/btn.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }


    private void dropball() {

        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size()); // random de la taille de la liste

        Vector4f black = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        GuiElement ball = new Text(20, 15 * (width / 20f), (height / 15f), Integer.toString(numberList.get(randInt)), 5f, black, false);
        addGuiElement(ball);

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

