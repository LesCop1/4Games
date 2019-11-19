package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.sql.Types.NULL;

public class BingoState extends ScreenState {
    private static final ResourceHandle<Texture> CASE_TEXTURE = new ResourceHandle<>("textures/bingo/caseBG.png") {
    };
    private static final ResourceHandle<Texture> CASE_HOVERED_TEXTURE = new ResourceHandle<>("textures/bingo/caseBGhovered.png") {
    };
    private static final ResourceHandle<Texture> CASE_CHECKED_TEXTURE = new ResourceHandle<>("textures/bingo/caseBGchecked.png") {
    };
    private final static int NB_BOTS = 5;
    private int tickMultiplier = 0;
    private int ticks = 0;
    private int nbGrids;
    private List<Integer> numberList = new ArrayList<>();
    private List<Player> bots = new ArrayList<>();
    private Player player;
    private int lastDrop;
    private GuiElement ball;
    private GuiElement gameStatus;

    public BingoState(int nbGrids, int difficulty) {
        super("bingo");
        this.nbGrids = nbGrids;
        switch (difficulty) {
            case 1:
                this.tickMultiplier = 8;
                break;
            case 2:
                this.tickMultiplier = 6;
                break;
            case 3:
                this.tickMultiplier = 4;
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
        if (numberList.size() > 0) {
            if (++ticks > tickMultiplier * 60) {
                dropball();
                System.out.println("Boule :" + this.lastDrop);
                dispList();
                this.ticks = 0;
            }
        }
// push winstate

    }

    private void dropball() {
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size()); // random de la taille de la liste

        this.lastDrop = numberList.get(randInt);
        numberList.remove(randInt);
    }

    @Override
    public void initGui() {
        setBackgroundTexture(new ResourceHandle<>("textures/bingo/bingoBG.png") {
        });

        float startX = 2.5f * (width / 20f);
        float startY = (height / 5f);

        float gridW = (width / 3f);
        float gridH = (height / 5f);
        float marginW = (width / 10f);
        float marginH = (height / 20f);

        for (int i = 0; i < this.nbGrids; i++) {
            float offsetX = ((float) Math.floor(i / 3f)) * (gridW + marginW);
            float offsetY = (i % 3) * (gridH + marginH);
            float gridX = startX + offsetX;
            float gridY = startY + offsetY;

            drawGrid(gridX, gridY, gridW, gridH, i);
        }

        this.ball = new Text(2000, 1 * (startX + gridW), 1 * (height / 10f), false, null, 5f) {
            @Override
            public String getText() {
                return lastDrop != 0 ? Integer.toString(lastDrop) : "";
            }
        };


        this.gameStatus = new Text(2001, 2 * (width / 8f), 1 * (height / 10f), false, "Starting", 5f) {
            @Override
            public String getText() {
                return updateGameStatus();
            }
        };

        final GuiElement backButton = new Button(2002,
                (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back") {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };

        addGuiElement(backButton, this.gameStatus, ball);
    }

    private void drawGrid(float gridX, float gridY, float gridW, float gridH, int numGrid) {
        int id = (100 * numGrid);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++, id++) {
                int caseX = i;
                int caseY = j;
                int caseId = id;
                final Button caseButton = new Button(caseId, (gridX + j * (gridW / 9)), (gridY + i * (gridH / 3)), (gridW / 10), (gridH / 3), false) {
                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = getValue();
                        return value != 0 ? CASE_TEXTURE : CASE_CHECKED_TEXTURE;
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        int value = getValue();
                        return value == 0 ? CASE_CHECKED_TEXTURE : value > 0 ? CASE_HOVERED_TEXTURE : CASE_TEXTURE;
                    }

                    @Override
                    public String getTitle() {
                        int value = getValue();
                        return value > 0 ? String.valueOf(value) : null;
                    }

                    @Override
                    public void onClick(MouseEvent.Click event) {
                        super.onClick(event);
                        if (BingoState.this.player.getGrid(numGrid).getGrid()[caseX][caseY] == lastDrop) {
                            BingoState.this.player.getGrid(numGrid).getGrid()[caseX][caseY] = 0;
                        }
                    }

                    @Override
                    public boolean isDisabled() {
                        return getValue() <= 0;
                    }

                    private int getValue() {
                        return BingoState.this.player.getGrid(numGrid).getValue(caseX, caseY);
                    }
                };

                addGuiElement(caseButton);
            }
        }
    }

    public String updateGameStatus() {
        if(lastDrop == NULL){
            return "Starting";
        }
        if (player.checkWin()) {
            return "Bingo !";
        } else if (numberList.isEmpty() && !player.checkWin()) {
            return "You Loose !";
        } else return "Playing";
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
        System.out.println();
    }

}

