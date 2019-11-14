package fr.bcecb.sudoku;

import fr.bcecb.Game;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.gui.Button;
import fr.bcecb.state.gui.GuiElement;
import fr.bcecb.state.gui.ScreenState;
import fr.bcecb.state.gui.Text;
import fr.bcecb.util.Constants;
import fr.bcecb.util.Resources;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;

public class SudokuState extends ScreenState {
    private static final ResourceHandle<Texture> BACKGROUND = new ResourceHandle<>("textures/sudokuBackground.png") {};

    private final Sudoku sudoku;

    private int selectedX = -1;
    private int selectedY = -1;

    public SudokuState(Sudoku.Difficulty difficulty) {
        super("sudoku_game");
        this.sudoku = new Sudoku(difficulty);
    }

    @Override
    public ResourceHandle<Texture> getBackgroundTexture() {
        return BACKGROUND;
    }

    @Override
    public void initGui() {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();

        GuiElement text = new Text(0, 10, 10, "done", Constants.COLOR_BLACK, false) {
            @Override
            public boolean isVisible() {
                return sudoku.winCondition();
            }
        };

        GuiElement backButton = new Button(-1, (width / 20f), (height - (height / 20f) - (height / 10f)), (height / 10f), (height / 10f), false, "Back", Resources.DEFAULT_BUTTON_TEXTURE)
                .setClickHandler((id, event) -> Game.instance().getStateEngine().popState());

        Button caseButton;
        int id = 1;

        float btnSize = 80.05f;
        float x = (width / 2) - (Sudoku.SIZE * btnSize / 2);
        for (int i = 0; i < Sudoku.SIZE; ++i, x += btnSize) {
            if (i % 3 == 0) {
                x += 4;
            }

            float y = (height / 2) - (Sudoku.SIZE * btnSize / 2);
            for (int j = 0; j < Sudoku.SIZE; ++j, ++id, y += btnSize) {
                if (j % 3 == 0) {
                    y += 4;
                }

                int caseX = i;
                int caseY = j;
                caseButton = new Button(id, x, y, btnSize, btnSize, true, "") {
                    @Override
                    public void onClick(MouseEvent.Click event) {
                        super.onClick(event);

                        if (sudoku.winCondition()) return;

                        if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                            sudoku.getGrid()[caseX][caseY] = 0;
                        }

                        selectedX = caseX;
                        selectedY = caseY;
                    }

                    @Override
                    public boolean isDisabled() {
                        return sudoku.getGeneratedGrid()[caseX][caseY] != 0;
                    }

                    @Override
                    public String getTitle() {
                        return sudoku.getGrid()[caseX][caseY] != 0 ? String.valueOf(sudoku.getGrid()[caseX][caseY]) : "";
                    }

                    @Override
                    public Vector4f getTitleColor() {
                        return sudoku.getGeneratedGrid()[caseX][caseY] == 0 ? Constants.COLOR_BLACK : Constants.COLOR_WHITE;
                    }

                    @Override
                    public ResourceHandle<Texture> getTexture() {
                        return sudoku.getGeneratedGrid()[caseX][caseY] == 0 ? new ResourceHandle<>("textures/caseSudoku.png") {} : new ResourceHandle<>("textures/caseSudokuBase.png") {};
                    }
                };

                addGuiElement(caseButton);
            }
        }

        for (int i = 0; i < Sudoku.SIZE; i++) {
            int value = i + 1;
            GuiElement candidateValueButton = new Button(++id, (width / 2) - (Sudoku.SIZE * btnSize / 2) + (btnSize * i) + 8, height - btnSize, btnSize, btnSize, true, String.valueOf(value), new ResourceHandle<>("textures/candidateValuesTextures.png") {
            }) {
                @Override
                public boolean isVisible() {
                    if (selectedX != -1 && selectedY != -1) {
                        int[] candidateValues = sudoku.computeCandidates(selectedX, selectedY);

                        for (int candidateValue : candidateValues) {
                            if (candidateValue == value) return true;
                        }
                    }

                    return false;
                }

                @Override
                public void onClick(MouseEvent.Click event) {
                    super.onClick(event);

                    sudoku.getGrid()[selectedX][selectedY] = value;

                    selectedX = -1;
                    selectedY = -1;
                }
            };

            addGuiElement(candidateValueButton);
        }

        addGuiElement(text, backButton);
    }
}

