package fr.bcecb.batailleNavale;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import org.lwjgl.glfw.GLFW;


public class BattleshipScreen extends ScreenState {
    private static final ResourceHandle<Texture> defaultTexture = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {};
    private Battleship battleship = new Battleship();
    private int[][] gridPlayer1;
    private int[][] gridPlayer2;
    private int idGrid=1;
    private int whichPlayer=1;


    public BattleshipScreen() {
        super("game-battleship");
    }

    public BattleshipScreen(int[][] grid1, int[][] grid2, int idGrid) {
        super("game-battleship");
        if(idGrid==2){
            gridPlayer1=grid1;
            gridPlayer2=grid2;
            this.idGrid=idGrid;
            whichPlayer++;
        }
        else if(idGrid==3) {
            gridPlayer1=grid1;
            this.gridPlayer2=grid2;
            this.idGrid=idGrid;
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        if(idGrid<3)Game.instance().getStateManager().pushState(new FirstPhaseBattleshipScreen(battleship,whichPlayer,gridPlayer1,gridPlayer2));
    }

    @Override
    public void initGui() {
        setBackgroundTexture(new ResourceHandle<>("textures/BatailleNavale/background_battleship.jpg") {
        });
        Button caseButton;
        int id = 1;
        float btnSize = 25f;
        float x = (width / 2f) - (9 * btnSize / 2) - 4;
        for (int i = 0; i < 10; ++i, x += btnSize) {
            float y = (height / 2f) - (9 * btnSize / 2) - 4;
            for (int j = 0; j < 10; ++j, ++id, y += btnSize) {
                int caseX = i;
                int caseY = j;
                caseButton = new Button(id, x, y, btnSize, btnSize, false) {
                    
                    @Override
                    public void onClick(MouseEvent.Click event) {
                        super.onClick(event);
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return defaultTexture;
                    }
                };
                addGuiElement(caseButton);
            }
        }

        final GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        }) {
            @Override
            public void onClick(MouseEvent.Click event) {
                Game.instance().getStateManager().popState();
            }
        };
        addGuiElement(backButton);
    }
}