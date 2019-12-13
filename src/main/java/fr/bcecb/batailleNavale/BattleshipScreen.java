package fr.bcecb.batailleNavale;

import com.google.common.base.Stopwatch;
import fr.bcecb.input.MouseButton;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.state.StateManager;
import fr.bcecb.state.gui.*;
import fr.bcecb.util.Constants;
import fr.bcecb.util.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BattleshipScreen extends ScreenState {
    private static final ResourceHandle<Texture> defaultTexture = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {
    };
    private static final ResourceHandle<Texture> sink = new ResourceHandle<>("textures/BatailleNavale/sink.png") {
    };
    private static final ResourceHandle<Texture> touch = new ResourceHandle<>("textures/BatailleNavale/touch.png") {
    };
    private static final ResourceHandle<Texture> cible = new ResourceHandle<>("textures/BatailleNavale/cible.png"){};
    private Battleship battleship;
    private int currentPlayer = 0;
    private int nextPlayer = 1;
    private boolean shoot = false;
    private float btnSize = 14.99f;
    private float btnSize2 = 7.99f;
    private Button changePlayerButton;
    private Stopwatch stopwatch;

    public BattleshipScreen(StateManager stateManager) {
        super(stateManager, "game_battleship");
        this.battleship = new Battleship();
        this.battleship.init();
        this.stopwatch = Stopwatch.createStarted();
    }

    @Override
    public void onEnter() {
        super.onEnter();
        stateManager.pushState(new FirstPhaseBattleshipScreen(stateManager, battleship));
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
                        int value = battleship.getPlayerGrid(currentPlayer)[finalI][finalJ];
                        return shoot || value == Battleship.SUCCESS_HIT || value == Battleship.FAILED_HIT;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = battleship.getPlayerGrid(currentPlayer)[finalI][finalJ];
                        return value == Battleship.SUCCESS_HIT ? touch : value == Battleship.FAILED_HIT ? sink : defaultTexture;
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
                addGuiElement(caseButton);
            }
        }

        float m = 5 * (width / 6f) - (9 * btnSize2 / 2) - 4;
        for (int i = 0; i < Battleship.GRID_SIZE; i++, m += btnSize2) {
            float n = (height / 2f) - (9 * btnSize2 / 2) - 4;
            for (int j = 0; j < Battleship.GRID_SIZE; j++, n += btnSize2) {
                int finalI = i;
                int finalJ = j;
                Button caseButton2 = new Button(((10 * finalI) + finalJ + 500), m, n, btnSize2, btnSize2, false) {
                    @Override
                    public boolean isDisabled() {
                        return true;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        int value = battleship.getNextPlayerGrid(currentPlayer)[finalI][finalJ];
                        return value == Battleship.SUCCESS_HIT ? touch : value == Battleship.FAILED_HIT ? sink : defaultTexture;
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
                addGuiElement(caseButton2);
            }
        }

        this.changePlayerButton = new CircleButton(120, 5 * (width / 6f) - (7.3f * btnSize2 / 2) - 4, 4.3f* (height / 6f), (width / 16f), false) {
            @Override
            public boolean isVisible() {
                return shoot;
            }

            @Override
            public ResourceHandle<Texture> getTexture() {
                return cible;
            }
        };

        Button whichPlayer = new Button(121, 1.6f * (width / 4f), (height / 10f), (width / 5f), (height / 8f), false) {
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

        Button whichGrid = new Button(123, 5f * (width / 6f) - (9 * btnSize2 / 2) - 4, (height / 4f), (width / 6f), (height / 10f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return currentPlayer == 0 ? Constants.BS_J2 : Constants.BS_J1;
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

        Button t1 = new Button(122, (width / 36f), (height / 12f), (width / 4f), (height / 6f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/bateauxEnnemis.png") {
                };
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

        Image b1 = new Image(124, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f), (height / 6f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 4 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 1 ? false : true;
            }
        };
        Image b2 = new Image(125, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 20f, (height / 6f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 3 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 2 ? false : true;
            }
        };
        Image b3 = new Image(126, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 40f, (height / 6f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 2 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 3 ? false : true;
            }
        };
        Image b4 = new Image(127, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 60f, (height / 6f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 1 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 4 ? false : true;
            }
        };
        Image b5 = new Image(128, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 80f, (height / 6f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 0 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 5 ? false : true;
            }
        };

        Button d1 = new Button(129, (width / 24f), (height / 3.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 5 ? true : false;
            }
        };
        Button d2 = new Button(130, (width / 24f) + 20f, (height / 3.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 4 ? true : false;
            }
        };
        Button d3 = new Button(131, (width / 24f) + 40f, (height / 3.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 3 ? true : false;
            }
        };
        Button d4 = new Button(132, (width / 24f) + 60f, (height / 3.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 2 ? true : false;
            }
        };
        Button d5 = new Button(133, (width / 24f) + 80f, (height / 3.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 1 ? true : false;
            }
        };

        Button t2 = new Button(134, (width / 36f), (height / 2.5f), (width / 4f), (height / 6f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/bateauxAllies.png") {
                };
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

        Image b6 = new Image(135, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f), (height / 2f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(nextPlayer) > 4 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 1 ? false : true;
            }
        };
        Image b7 = new Image(136, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 20f, (height / 2f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 3 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 2 ? false : true;
            }
        };
        Image b8 = new Image(137, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 40f, (height / 2f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 2 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 3 ? false : true;
            }
        };
        Image b9 = new Image(138, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 60f, (height / 2f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(currentPlayer) > 1 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(currentPlayer) < 4 ? false : true;
            }
        };
        Image b10 = new Image(139, new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
        }, (width / 24f) + 80f, (height / 2f), (width / 24f), (height / 6f), true) {
            @Override
            public ResourceHandle<Texture> getImage() {
                return battleship.hit(nextPlayer) > 0 ? new ResourceHandle<>("textures/BatailleNavale/minecraftBoatTouch.png") {
                } : new ResourceHandle<>("textures/BatailleNavale/minecraftBoat.png") {
                };
            }

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 5 ? false : true;
            }
        };

        Button d6 = new Button(140, (width / 24f), (height / 1.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 5 ? true : false;
            }
        };
        Button d7 = new Button(141, (width / 24f) + 20f, (height / 1.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 4 ? true : false;
            }
        };
        Button d8 = new Button(142, (width / 24f) + 40f, (height / 1.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 3 ? true : false;
            }
        };
        Button d9 = new Button(143, (width / 24f) + 60f, (height / 1.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 2 ? true : false;
            }
        };
        Button d10 = new Button(144, (width / 24f) + 80f, (height / 1.5f), (width / 23f), (height / 16f), false) {
            @Override
            public ResourceHandle<Texture> getTexture() {
                return new ResourceHandle<>("textures/BatailleNavale/dead.png") {
                };
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

            @Override
            public boolean isVisible() {
                return battleship.countBoat(nextPlayer) < 1 ? true : false;
            }
        };

        Button backButton = new Button(BACK_BUTTON_ID, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back");
        addGuiElement(this.changePlayerButton, backButton, whichPlayer, whichGrid, t1, t2, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10);
    }

    @Override
    public boolean mouseClicked(int id, MouseButton button) {
        if (id == this.changePlayerButton.getId()) {
            this.currentPlayer = this.currentPlayer == 1 ? 0 : 1;
            this.nextPlayer = this.currentPlayer == 1 ? 0 : 1;
            this.shoot = false;
            return true;
        } else if (id < 100) {
            if (!this.shoot) {
                int x = id / 10;
                int y = id % 10;
                if (currentPlayer == 0 && battleship.getPlayerGrid(currentPlayer)[x][y] > -1)
                    this.battleship.hitGridJ1.set(battleship.getPlayerGrid(currentPlayer)[x][y], true);
                else if (currentPlayer == 1 && battleship.getPlayerGrid(currentPlayer)[x][y] > -1)
                    this.battleship.hitGridJ2.set(battleship.getPlayerGrid(currentPlayer)[x][y], true);
                this.battleship.shoot(this.currentPlayer, x, y);
                this.shoot = true;
                if (this.battleship.checkWinCondition(this.currentPlayer)) {
                    this.stopwatch.stop();
                    long time = this.stopwatch.elapsed(TimeUnit.MILLISECONDS);
                    stateManager.pushState(new EndGameScreen(stateManager, Constants.GameType.BATTLESHIP, time, calculatePoints()));
                }
            }
        }
        return false;
    }

    private int calculatePoints() {
        long time = this.stopwatch.elapsed(TimeUnit.SECONDS);

        int failedCount = 0;
        for (int i = 0; i < Battleship.GRID_SIZE; i++) {
            for (int j = 0; j < Battleship.GRID_SIZE; j++) {
                int value = this.battleship.getPlayerGrid(this.currentPlayer)[i][j];
                if (value == Battleship.FAILED_HIT) {
                    failedCount++;
                }
            }
        }
        time = time - 120;
        int minusPoint = (int) (((time / 30) * 3) + (failedCount * 10));
        return MathHelper.clamp(600 - minusPoint, 200, 600);
    }
}