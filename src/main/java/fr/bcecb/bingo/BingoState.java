package fr.bcecb.bingo;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BingoState extends ScreenState {
    private static final ResourceHandle<Texture> CASE_TEXTURE = new ResourceHandle<>("textures/bingo/caseBG.png") {
    };
    private static final ResourceHandle<Texture> CASE_HOVERED_TEXTURE = new ResourceHandle<>("textures/bingo/caseBGhovered.png") {
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

        float startX = (width / 20f);
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

        this.ball = new Text(401, 6 * (width / 8f), (height / 5f), "", 5f, false);
        this.gameStatus = new Text(402, 2 * (width / 8f), 1 * (height / 10f), "Starting", 5f, true);

        final GuiElement backButton = new Button(-1,
                (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back")
                .setClickHandler((id, e) -> Game.instance().getStateEngine().popState());

        addGuiElement(backButton, this.gameStatus);
    }

    private void drawGrid(float gridX, float gridY, float gridW, float gridH, int numGrid) {
        int caseID = (100 * numGrid);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++, caseID++) {
                final Button caseX = new Button(caseID,
                        (gridX + j * (gridW / 9)), (gridY + i * (gridH / 3)),
                        (gridW / 10), (gridH / 3), false, Integer.toString(this.player.getGrid(numGrid).getValue(i, j)), CASE_TEXTURE);

                caseX.setHoverTexture(CASE_HOVERED_TEXTURE);

                if (caseX.getTitle().equals("0")) {
                    caseX.setDisabled(true);
                }

                caseX.setClickHandler((id, e) -> {
                    checkCase(caseX.getId());
                });

                addGuiElement(caseX);
            }
        }
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

