package fr.bcecb.sudoku;

import fr.bcecb.Game;
import fr.bcecb.render.Window;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.state.gui.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class SudokuState extends ScreenState {
    private static final int SIZE = 9;
    private static final int SIZE_BOX = (int) Math.floor(Math.sqrt(SIZE));
    private static final Random RANDOM = new Random();
    List<Button> buttonsCase = new ArrayList<>();
    private static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(
            Collectors.toCollection(ArrayList::new),
            list -> {
                Collections.shuffle(list);
                return list;
            }
    );

    /**
     * The sudoku grid
     */
    private final int[][] grid = new int[SIZE][SIZE];
    private final int[][] generateGrid;

    private GuiElement text;

    public SudokuState(Difficulty difficulty) {
        super("sudoku_game");
        for (int i = 0; i < SIZE; i += SIZE_BOX) {
            fillBox(i, i);
        }

        fillRemaining(0, SIZE_BOX);

        List<Integer> toRemove = IntStream.range(0, SIZE * SIZE).boxed().collect(toShuffledList()).subList(0, difficulty.getMissingValueCount());

        int x, y;
        for (int i : toRemove) {
            x = i / SIZE;
            y = i % SIZE;

            this.grid[x][y] = 0;
        }

        this.generateGrid = Arrays.stream(this.grid).map(int[]::clone).toArray(int[][]::new);
    }

    public void printSudoku() {
        for (int i = 0; i < SIZE; i++) {
            if (i % SIZE_BOX == 0) {
                System.out.println();
            }

            for (int j = 0; j < SIZE; j++) {
                if (j % SIZE_BOX == 0) {
                    System.out.print(" ");
                }
                System.out.print(this.grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * @return a random {@code int} between 1 and 9 (inclusive)
     */
    private static int randomInt() {
        return RANDOM.nextInt(9) + 1;
    }

    /**
     * Pretty-prints the sudoku grid to the console
     */


    private static <T> Collector<T, ?, List<T>> toShuffledList() {
        //noinspection unchecked
        return (Collector<T, ?, List<T>>) SHUFFLER;
    }

    /**
     * Computes valid candidates for slot of given row and column
     *
     * @param x the row
     * @param y the column
     * @return an array of valid values for the specified slot
     */
    public int[] computeCandidates(int x, int y) {
        return IntStream.rangeClosed(1, 9).filter(n -> checkGrid(n, x, y)).toArray();
    }

    /**
     * Checks if the row, column and box contain valid values (i.e no duplicates)
     *
     * @param x the row
     * @param y the column
     * @return {@code true} if the given row, column and box are valid
     */
    private boolean checkGrid(int x, int y) {
        return checkRow(x) && checkColumn(y) && checkBox(x - (x % SIZE_BOX), y - (y % SIZE_BOX));
    }

    /**
     * Checks if the value {@code n} is already present in given row, column and box
     *
     * @param n the value to check against the grid
     * @param x the row
     * @param y the column
     * @return {@code false} if the given row, column and box already contain the value
     */
    private boolean checkGrid(int n, int x, int y) {
        return !checkRow(n, x) && !checkColumn(n, y) && !checkBox(n, x - (x % SIZE_BOX), y - (y % SIZE_BOX));
    }

    /**
     * Checks if the given box is valid
     *
     * @param x the row
     * @param y the column
     * @return {@code true} if the box doesn't contain any duplicate
     */
    private boolean checkBox(int x, int y) {
        boolean found = false;
        for (int n = 1; n <= 9; ++n, found = false) {
            for (int i = x; i < x + SIZE_BOX; ++i) {
                for (int j = y; j < y + SIZE_BOX; ++j) {
                    if (this.grid[i][j] == n) {
                        if (!found) {
                            found = true;
                        } else return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Checks if {@code n} is already in the box
     *
     * @param n the number to check in the box
     * @param x the row
     * @param y the column
     * @return {@code true} if the number is already present in the specified box, {@code false} otherwise
     */
    private boolean checkBox(int n, int x, int y) {
        for (int i = x; i < x + SIZE_BOX; i++) {
            for (int j = y; j < y + SIZE_BOX; j++) {
                if (this.grid[i][j] == n) return true;
            }
        }

        return false;
    }

    /**
     * Checks if the given row contains valid values (i.e no duplicates)
     *
     * @param x the row
     * @return {@code true} if the row doesn't contain any duplicate
     */
    private boolean checkRow(int x) {
        boolean found = false;
        for (int n = 1; n <= 9; ++n, found = false) {
            for (int y = 0; y < SIZE; y++) {
                if (this.grid[x][y] == n) {
                    if (!found) {
                        found = true;
                    } else return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the given value is already present in the given row
     *
     * @param n the value
     * @param x the row
     * @return {@code true} if the row already does contain the value
     */
    private boolean checkRow(int n, int x) {
        for (int i = 0; i < SIZE; i++) {
            if (this.grid[x][i] == n) return true;
        }

        return false;
    }

    /**
     * Checks if the given column contains valid values (i.e no duplicates)
     *
     * @param y the column
     * @return {@code true} if the column doesn't contain any duplicate
     */
    private boolean checkColumn(int y) {
        boolean found = false;
        for (int n = 1; n <= 9; ++n, found = false) {
            for (int x = 0; x < SIZE; x++) {
                if (this.grid[x][y] == n) {
                    if (!found) {
                        found = true;
                    } else return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the given value is already present in the given column
     *
     * @param n the value
     * @param y the column
     * @return {@code true} if the column already does contain the value
     */
    private boolean checkColumn(int n, int y) {
        for (int i = 0; i < SIZE; i++) {
            if (this.grid[i][y] == n) return true;
        }

        return false;
    }

    /**
     * Fills a given diagonal box with random values
     *
     * @param x the row
     * @param y the column
     */
    private void fillBox(int x, int y) {
        int n;
        for (int i = 0; i < SIZE_BOX; i++) {
            for (int j = 0; j < SIZE_BOX; j++) {
                do {
                    n = randomInt();
                }
                while (checkBox(n, x, y));

                this.grid[x + i][y + j] = n;
            }
        }
    }

    /**
     * Fills remaining box (i.e other than diagonals)
     *
     * @param x the row
     * @param y the column
     * @return {@code true} if the box could be filled completely
     */
    private boolean fillRemaining(int x, int y) {
        if (y >= SIZE && x < SIZE - 1) {
            x = x + 1;
            y = 0;
        }

        if (x >= SIZE && y >= SIZE) {
            return true;
        }

        if (x < SIZE_BOX) {
            if (y < SIZE_BOX) {
                y = SIZE_BOX;
            }
        } else if (x < SIZE - SIZE_BOX) {
            if (y == (x / SIZE_BOX) * SIZE_BOX)
                y = y + SIZE_BOX;
        } else {
            if (y == SIZE - SIZE_BOX) {
                x = x + 1;
                y = 0;
                if (x >= SIZE)
                    return true;
            }
        }

        for (int n = 1; n <= SIZE; n++) {
            if (checkGrid(n, x, y)) {
                this.grid[x][y] = n;
                if (fillRemaining(x, y + 1))
                    return true;

                this.grid[x][y] = 0;
            }
        }
        return false;
    }

    /**
     * Tries to solve the current grid.
     *
     * @return {@code true} if the grid could be solved, {@code false} otherwise
     */
    public boolean solve() {
        int[][] copy = Arrays.stream(this.grid).map(int[]::clone).toArray(int[][]::new);

        return solve(copy);
    }

    /**
     * Tries to solve the grid.
     *
     * @param grid the grid to be solved
     * @return {@code true} if the grid could be solved, {@code false} otherwise
     */
    public boolean solve(int[][] grid) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (grid[x][y] == 0) {
                    for (int n = 1; n <= 9; ++n) {
                        grid[x][y] = n;
                        if (checkGrid(x, y) && solve(grid)) {
                            return true;
                        }
                        grid[x][y] = 0;
                    }
                    return false;
                }
            }
        }

        return true;
    }

    public boolean winCondition() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == 0) return false;

            }
        }
        return true;
    }

    @Override
    public void initGui() {
        int width = Window.getCurrentWindow().getWidth();
        int height = Window.getCurrentWindow().getHeight();
        Button[] buttonsCandidateValues = new Button[SIZE];
        setBackgroundTexture(new ResourceHandle<>("textures/arche.jpg") {
        });
        GuiElement backButton = new Button(999, 0, 0, 50 / ((float) 1920 / width), 50 / ((float) 1920 / width), false, new ResourceHandle<>("textures/back_button.png") {
        }).setClickHandler(e -> Game.instance().getStateEngine().popState());
        text = new Text(99, 960, 540, "YOU WON !", false);
        text.setVisible(false);
        addGuiElement(backButton, text);
        for (int i = 0; i < SIZE; i++) {
            buttonsCandidateValues[i] = new Button(100 + i, ((float) width - 80 / ((float) 1920 / width) * SIZE) / 2 + i * 80 / ((float) 1920 / width), height - 80 / ((float) 1920 / width), 80 / ((float) 1920 / width), 80 / ((float) 1920 / width), false, String.valueOf(i + 1), new ResourceHandle<>("textures/candidateValuesTextures.png") {
            });
            buttonsCandidateValues[i].setVisible(false);
            for (int j = 0; j < 9; j++) {
                final Button button = new Button((9 * i) + j, ((float) width - 80 / ((float) 1920 / width) * SIZE) / 2 + i * 80 / ((float) 1920 / width), ((float) height - 80 / ((float) 1920 / width) * SIZE) / 2 + j * 80 / ((float) 1920 / width), (80 / ((float) 1920 / width)), (80 / ((float) 1920 / width)), false, String.valueOf(grid[j][i] != 0 ? grid[j][i] : ""), new ResourceHandle<>("textures/caseBattleship.png") {
                });

                if (generateGrid[j][i] == 0) {
                    button.setClickHandler(e -> {
                        for (Button candidateValue : buttonsCandidateValues) {
                            candidateValue.setVisible(false);
                        }
                        int x = button.getId() % 9;
                        int y = button.getId() / 9;
                        if (grid[x][y] == 0) {
                            buttonsCase.add(button);
                            int[] candidatesValues = computeCandidates(x, y);
                            for (int l = 0; l < candidatesValues.length; l++) {
                                int value = candidatesValues[l];
                                buttonsCandidateValues[l].setTitle(String.valueOf(value));
                                buttonsCandidateValues[l].setVisible(true);
                                buttonsCandidateValues[l].setClickHandler(f -> {
                                    button.setTitle(String.valueOf(value));
                                    grid[x][y] = value;

                                    for (GuiElement buttonsCandidateValue : buttonsCandidateValues) {
                                        buttonsCandidateValue.setVisible(false);
                                    }
                                });
                            }
                        } else if (e.getButton() == GLFW_MOUSE_BUTTON_RIGHT) {
                            button.setTitle("");
                            grid[x][y] = 0;
                        }
                    });
                } else button.setEnabled(false);

                addGuiElement(button);
            }
        }

        addGuiElement(buttonsCandidateValues);

    }

    @Override
    public void update() {
        super.update();
        if (winCondition()) {
            text.setVisible(true);

            for (Button buttonCase : buttonsCase) {
                buttonCase.setEnabled(false);
            }
        }
    }

    public enum Difficulty {
        EASY(20),
        NORMAL(40),
        HARD(60);

        private final int missingValueCount;

        Difficulty(int missingValueCount) {
            this.missingValueCount = missingValueCount;
        }

        /**
         * @return the amount of values to remove from a Sudoku grid
         */
        public int getMissingValueCount() {
            return missingValueCount;
        }
    }

}

