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
    private GuiElement gameStatus;

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
        System.out.println("NB GRILLES : " + nbGrids);
        super.onEnter();
    }

    @Override
    public void onExit() {
        System.out.println("bobaye");
        super.onExit();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!player.checkWin()) {
            if (numberList.size() > 0) {
                if (++ticks > tickMultiplier * 60) {
                    dropball();
                    System.out.println("Boule :" + this.lastDrop);
                    dispList();
                    this.ticks = 0;
                }
            } else ((Text) gameStatus).setText("Perdu");

        } else ((Text) gameStatus).setText("Gagné");
// push winstate

    }

    private void dropball() {
        ((Text) gameStatus).setText("Playing");
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size()); // random de la taille de la liste

        this.lastDrop = numberList.get(randInt);
        ((Text) ball).setText(Integer.toString(this.lastDrop));
        //System.out.println("((Text) ball).getText() = " + ((Text) ball).getText());
        addGuiElement(ball);
        numberList.remove(randInt);
    }

    public void checkCase(int btnID) {
        if (((Button) getGuiElementById(btnID)).getTitle().equals(((Integer) this.lastDrop).toString())) {
            ((Button) getGuiElementById(btnID)).setTexture(new ResourceHandle<>("textures/bingo/caseBGchecked.png") {
            });
            ((Button) getGuiElementById(btnID)).setHoverTexture(new ResourceHandle<>("textures/bingo/caseBGchecked.png") {
            });
        }
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        setBackgroundTexture(new ResourceHandle<>("textures/bingo/bingoBG.png") {
        });
        this.ball = new Text(401, 6 * (width / 8f), (height / 5f), "", 5f, false);
        this.gameStatus = new Text(402, 2 * (width / 8f), 1 * (height / 10f), "Starting", 5f, true);
        addGuiElement(gameStatus);

        float gridX, gridW, gridY, gridH;
        int caseID;
        List<Grid> playerGrids = player.getGrids();
        float caseScale;

        switch (nbGrids) {
            case 1:
                gridX = 1 * (width / 20f);
                gridY = 1 * (height / 2.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);
                caseScale = (0.9f * ((gridW / 9f) / (gridH / 3f)));
                Vector4f lineColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);

                //GuiElement line1 = new Line(1000,(width/20f),1* (height/2.5f),gridW,(height/40f),lineColor);
                //addGuiElement(line1);
                caseID = 0;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 9; j++, caseID++) {
                        final GuiElement caseX = new Button(caseID,
                                (gridX + j * (gridW / 9)), (gridY + i * (gridH / 3)),
                                (gridW / 10), (gridH / 3),
                                false, Integer.toString(playerGrids.get(0).getGrid()[i][j]), caseScale, new ResourceHandle<>("textures/bingo/caseBG.png") {
                        });
                        ((Button) caseX).setHoverTexture(new ResourceHandle<Texture>("textures/bingo/caseBGhovered.png") {
                        });
                        if (((Button) caseX).getTitle().equals("0")) {
                            ((Button) caseX).setDisabled(true);
                        }
                        addGuiElement(caseX);
                        caseX.setClickHandler((id, e) -> {
                            checkCase(caseX.getId());
                        });

                    }
                }

                // addGuiElement(grid);
                break;

            case 2:
                gridX = 1 * (width / 20f);
                gridY = 1 * (height / 3.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);
                caseScale = (0.9f * ((gridW / 9f) / (gridH / 3f)));
                caseID = 0;
                for (int i = 0; i < nbGrids; i++) {


                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 9; k++, caseID++) {
                            final GuiElement caseX = new Button(caseID,
                                    (gridX + k * (gridW / 9)), (gridY + (j * (gridH / 3) + (i * gridH))),
                                    (gridW / 10), (gridH / 3),
                                    false, Integer.toString(playerGrids.get(i).getGrid()[j][k]), caseScale,
                                    new ResourceHandle<>("textures/bingo/caseBG.png") {
                                    });
                            caseX.setClickHandler((id, e) -> {
                                checkCase(id);
                            });
                            addGuiElement(caseX);
                        }
                    }
                    gridY += gridH / 3;
                }

                break;

            case 3:
                gridX = 2 * (width / 20f);
                gridY = 1 * (height / 7.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);
                caseScale = (0.9f * ((gridW / 9f) / (gridH / 3f)));
                caseID = 0;

                for (int i = 0; i < nbGrids; i++) {

                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 9; k++, caseID++) {
                            final GuiElement caseX = new Button(caseID,
                                    (gridX + k * (gridW / 9)), (gridY + (j * (gridH / 3) + (i * gridH))),
                                    (gridW / 10), (gridH / 3),
                                    false, Integer.toString(playerGrids.get(i).getGrid()[j][k]), caseScale,
                                    new ResourceHandle<>("textures/bingo/caseBG.png") {
                                    });
                            caseX.setClickHandler((id, e) -> {
                                checkCase(id);
                            });
                            addGuiElement(caseX);
                        }
                    }
                    gridY += gridH / 3;
                }

                break;

            case 4:

                gridX = 2 * (width / 20f);
                gridY = 1 * (height / 7.5f);
                gridW = (width / 3f);
                gridH = (height / 5f);
                caseScale = (0.9f * ((gridW / 9f) / (gridH / 3f)));
                caseID = 0;

                for (int i = 0; i < nbGrids; i++) {
                    if (i > 2) {
                        gridX = 1 * (width / 2f);
                    }
                    for (int j = 0; j < 3; j++) {
                        for (int k = 0; k < 9; k++, caseID++) {
                            final GuiElement caseX = new Button(caseID,
                                    1 * (gridX + k * (gridW / 9)), 1 * (gridY + (j * (gridH / 3) + (i * gridH))),
                                    1 * (gridW / 10), 1 * (gridH / 3),
                                    false, Integer.toString(playerGrids.get(i).getGrid()[j][k]), caseScale,
                                    new ResourceHandle<>("textures/bingo/caseBG.png") {
                                    });
                            caseX.setClickHandler((id, e) -> {
                                checkCase(id);
                            });
                            addGuiElement(caseX);
                        }
                    }
                    gridY += gridH / 3;

                }
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
        });
        backButton.setClickHandler((id, e) -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton);
    }

    @Override
    public boolean shouldRenderBelow() {
        return false;
    }

    public void dispList() {
        for (int boule :
                numberList) {
            System.out.print(" | " + boule);
        }
        System.out.println("");
    }

}

