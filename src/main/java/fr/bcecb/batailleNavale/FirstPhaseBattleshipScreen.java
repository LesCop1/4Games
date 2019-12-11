package fr.bcecb.batailleNavale;

import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private static ResourceHandle<Texture> temp = null;
    private Battleship battleship;
    private List<Boat> addedBoats = new ArrayList<>();
    private Boat boat;
    private int currentPlayer;
    private Button nextButton;
    
    public FirstPhaseBattleshipScreen(StateManager stateManager, Battleship battleship) {
        super(stateManager, "game_battleship.firstphase");
        this.battleship = battleship;
        this.currentPlayer = 0;
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Constants.BS_BACKGROUND);

        float btnSize = 14.99f;

        float x = (width / 2f) - (9 * btnSize / 2) - 4;
        for (int i = 0; i < Battleship.GRID_SIZE; i++, x += btnSize) {

            float y = (height / 2f) - (9 * btnSize / 2) - 4;
            for (int j = 0; j < Battleship.GRID_SIZE; j++, y += btnSize) {
                int finalI = i;
                int finalJ = j;
                Button caseButton = new Button(((10 * finalI) + finalJ), x, y, btnSize, btnSize, false) {
                    @Override
                    public boolean isDisabled() {
                        return ((battleship.getPlayerGrid(currentPlayer)[finalI][finalJ] != Battleship.DEFAULT_VALUE) || (addedBoats.size() == 5));
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = battleship.getPlayerGrid(currentPlayer)[finalI][finalJ];
                        return value != Battleship.DEFAULT_VALUE ? findTexture(value) : Constants.BS_DEFAULT_TEXTURE;
                    }

                    @Override
                    public ResourceHandle<Texture> getHoverTexture() {
                        return null;
                    }

                    @Override
                    public ResourceHandle<Texture> getDisabledTexture() {
                        return null;
                    }

                    private ResourceHandle<Texture> findTexture(int i) {
                        return switch (i) {
                            case 0 -> new ResourceHandle<>("textures/BatailleNavale/Torpedo.png") {};
                            case 1 -> new ResourceHandle<>("textures/BatailleNavale/Submarine.png") {};
                            case 2 -> new ResourceHandle<>("textures/BatailleNavale/Frigate.png") {};
                            case 3 -> new ResourceHandle<>("textures/BatailleNavale/Cruiser.png") {};
                            case 4 -> new ResourceHandle<>("textures/BatailleNavale/Aircraft_Carrier.png") {};
                            default -> null;
                        };
                    }
                };
                addGuiElement(caseButton);
            }
        }

        for (Boat.Type type : Boat.Type.values()) {
            Button boatButton = new Button(100 + type.ordinal(), (width / 20f), 50 + (type.ordinal() * 30), btnSize * type.getSize(), btnSize, false) {
                @Override
                public boolean isVisible() {
                    return checkIfNotPlaced(type.getName());
                }

                @Override
                public ResourceHandle<Texture> getTexture() {
                    return type.getTextureHandle();
                }

                @Override
                public ResourceHandle<Texture> getHoverTexture() {
                    return null;
                }

                @Override
                public ResourceHandle<Texture> getDisabledTexture() {
                    return null;
                }
            };
            addGuiElement(boatButton);
        }

        this.nextButton = new Button(110, (width / 2.9f), (height - (height / 20f) - (height / 10f)), (height / 3f), (height / 10f), false) {
            @Override
            public String getTitle() {
                return currentPlayer == 0 ? "Joueur Suivant" : "Commencer la partie";
            }

            @Override
            public boolean isVisible() {
                return addedBoats.size() == 5;
            }
        };

        Text whichPlayer = new Text(801, (width / 2f), (height / 5f), true, null) {
            @Override
            public String getText() {
                return currentPlayer==0 ? "Joueur 1" : "Joueur 2";
            }

            @Override
            public Vector4f getColor() {
                return Constants.COLOR_BLACK;
            }
        };


        Button backButton = new Button(9090, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(this.nextButton, backButton, whichPlayer);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if(id == 9090){ //Retour Arrière
            stateManager.popState();
            stateManager.popState();
            return true;
        } else if (id == this.nextButton.getId()) { //Si on veut passer aux placements du joueur suivant
            if (this.currentPlayer == 0) {
                this.currentPlayer = 1;
                this.addedBoats.clear();
            } else {
                stateManager.popState();
            }
            return true;
        } else if (id < 100) {
            if (button == MouseButton.RIGHT) {
                this.battleship.swapOrientation(this.boat);
            }
            int x = id / 10;
            int y = id % 10;
            if (this.boat != null && !this.battleship.cannotPlace(this.currentPlayer, this.boat, x, y) && !this.addedBoats.contains(this.boat)) {
                this.addedBoats.add(this.boat);
                this.battleship.putBoat(this.currentPlayer, this.boat, x, y);
                this.boat = null;
            }
            return true;
        } else if (id < 105) {
            this.boat = new Boat(Boat.Type.values()[id - 100]);
            return true;
        }
        return false;
    }

    private boolean checkIfNotPlaced(String s) {
        for (Boat boat : this.addedBoats) {
            if (boat.getType().getName().equals(s)) {
                return false;
            }
        }
        return true;
    }
}
