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
    private GuiElement ball;

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
        this.ball = new Text(40, (width / 8f), (height / 5f), "", 5f, false);


        float gridX, gridW, gridY, gridH;
        int id;
        List<Grid> playerGrids = player.getGrids();
        float caseScale = 0.7f;

        switch (nbGrids) {
            case 1:
                gridX = 1 * (width / 20f);
                gridY = 1 * (height / 2.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);

                id = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++, id++) {


                        GuiElement caseX = new Button(id,
                                (gridX + j * (gridW / 10)), (gridY + i * (gridH / 3)),
                                (gridW / 10), (gridH / 3),
                                false, Integer.toString(playerGrids.get(0).getGrid()[i][j]),caseScale, new ResourceHandle<>("textures/bingo/caseBG.png") {
                        }).setClickHandler(e -> System.out.println("coucou"));
                        addGuiElement(caseX);
                    }
                }

                // addGuiElement(grid);
                break;

            case 2:
                gridX = 1 * (width / 20f);
                gridY = 1 * (height / 3.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);
                id = 0;
                for (int i = 0; i < 2; i++) {


                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 9; k++, id++) {
                            System.out.println("grille n°"+ i);
                            GuiElement caseX = new Button(id,
                                    (gridX + k * (gridW / 10)), (gridY + (j * (gridH / 3) + (i * gridH)) ),
                                    (gridW / 10), (gridH / 3),
                                    false, Integer.toString(playerGrids.get(i).getGrid()[j][k]),caseScale,
                                    new ResourceHandle<Texture>("textures/bingo/caseBG.png") {
                                    }).setClickHandler(e -> System.out.println("coucou"));
                            addGuiElement(caseX);
                        }
                    }
                    gridY += gridH/3;
                }

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

        GuiElement backButton = new Button(400,
                (width / 20f), (height - (height / 20f) - (height / 10f)),
                (height / 10f), (height / 10f),
                false, "Back", new ResourceHandle<>("textures/defaultButton.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }


    private void dropball() {

        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size()); // random de la taille de la liste

        this.lastDrop = numberList.get(randInt);
        ((Text) ball).setText(Integer.toString(this.lastDrop));
        //System.out.println("((Text) ball).getText() = " + ((Text) ball).getText());
        addGuiElement(ball);
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

