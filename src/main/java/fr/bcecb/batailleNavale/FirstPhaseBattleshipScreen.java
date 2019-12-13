package fr.bcecb.batailleNavale;

import fr.bcecb.event.KeyboardEvent;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.input.InputManager;
import fr.bcecb.input.Key;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class FirstPhaseBattleshipScreen extends ScreenState {
    private Battleship battleship;
    private List<Boat> addedBoats = new ArrayList<>();
    private List<Float> xButton = new ArrayList<>();
    private List<Float> yButton = new ArrayList<>();
    private Boat boat;
    private Button nextButton;
    private int currentPlayer;
    private float btnSize = 14.99f;

    public FirstPhaseBattleshipScreen(StateManager stateManager, Battleship battleship) {
        super(stateManager, "game_battleship.firstphase");
        this.battleship = battleship;
        this.currentPlayer = 0;
    }

    @Override
    public void initGui() {
        setBackgroundTexture(Constants.BS_BACKGROUND);
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
                        return Constants.BS_DEFAULT_TEXTURE;
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
                if (!xButton.contains(caseButton.getX())) xButton.add(caseButton.getX());
                if (!yButton.contains(caseButton.getY())) yButton.add(caseButton.getY());
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
                    return type.getTextureHandleH();
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

        this.nextButton = new Button(110, (width / 1.4f), (height/3f), (height / 3f), (height / 10f), false) {
            @Override
            public String getTitle() {
                return currentPlayer == 0 ? "Joueur Suivant" : "Commencer la partie";
            }

            @Override
            public boolean isVisible() {
                return addedBoats.size() == 5;
            }
        };

        Button whichPlayer = new Button(801, 1.6f * (width / 4f), (height / 10f), (width / 5f), (height / 8f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return currentPlayer == 0 ? Constants.BS_J1 : Constants.BS_J2;
            }

            @Override
            public ResourceHandle<Texture> getHoverTexture() {
                return null;
            }

            @Override
            public ResourceHandle<Texture> getDisabledTexture() {
                return null;
            }

            @Override
            public boolean isDisabled() {
                return true;
            }
        };

        Button backButton = new Button(9090, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(this.nextButton, backButton, whichPlayer);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == 9090) { //Retour Arrière
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
            if (this.boat != null && button == MouseButton.RIGHT) {
                this.battleship.swapOrientation(this.boat);
            }
            int x = id / 10;
            int y = id % 10;
            if (this.boat != null && !this.battleship.cannotPlace(this.currentPlayer, this.boat, x, y) && !this.addedBoats.contains(this.boat)) {
                this.addedBoats.add(this.boat);
                this.battleship.putBoat(this.currentPlayer, this.boat, x, y);
                float finalWidth, finalHeight;
                if (boat.isHorizontal()) {
                    finalWidth = btnSize * boat.type.getSize();
                    finalHeight = btnSize;
                } else {
                    finalWidth = btnSize;
                    finalHeight = btnSize * boat.type.getSize();
                }
                if (currentPlayer == 0) putTextureBoatJ1(x, y, finalHeight, finalWidth, boat);
                else putTextureBoatJ2(x, y, finalHeight, finalWidth, boat);
                this.boat = null;
            }
            return true;
        } else if (id < 105) {
            this.boat = new Boat(Boat.Type.values()[id - 100]);
            return true;
        } else if (id >= 1000) {
            String tempName = null;
            if (button == MouseButton.LEFT) {
                if(id==1000) tempName = "T";
                if(id==1001) tempName = "S";
                if(id==1002) tempName = "F";
                if(id==1003) tempName = "C";
                if(id==1004) tempName = "A";
                for (Boat b: addedBoats) if(b.getType().getName()==tempName) this.boat=b;
                this.addedBoats.remove(this.boat);
                this.boat=null;
                this.battleship.deleteBoat(currentPlayer, id-1000);
            }
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

    private void putTextureBoatJ1(int x, int y, float finalHeight, float finalWidth, Boat boat) {
        Button boatTextureJ1 = new Button(1000 + boat.type.ordinal(), xButton.get(x), yButton.get(y), finalWidth, finalHeight, false) {
            @Override
            public boolean isVisible() {
                return !checkIfNotPlaced(boat.getType().getName()) && currentPlayer==0 ? true : false;
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                if (boat.isHorizontal()) return boat.type.getTextureHandleH();
                else return boat.type.getTextureHandleV();
            }

            @Override
            public boolean isDisabled() {
                return checkIfNotPlaced(boat.getType().getName());
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
        addGuiElement(boatTextureJ1);
    }

    private void putTextureBoatJ2(int x, int y, float finalHeight, float finalWidth, Boat boat) {
        Button boatTextureJ2 = new Button(1000 + boat.type.ordinal(), xButton.get(x), yButton.get(y), finalWidth, finalHeight, false) {
            @Override
            public boolean isVisible() {
                return !checkIfNotPlaced(boat.getType().getName()) ? true : false;
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                if (boat.isHorizontal()) return boat.type.getTextureHandleH();
                else return boat.type.getTextureHandleV();
            }

            @Override
            public boolean isDisabled() {
                return checkIfNotPlaced(boat.getType().getName());
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
        addGuiElement(boatTextureJ2);
    }
}
