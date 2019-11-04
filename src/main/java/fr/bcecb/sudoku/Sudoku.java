package fr.bcecb.sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Sudoku {
    private final static int SIZE = 9;
    private final static int DEFAULT_VALUE = 0;
    private final static int BACKTRACKING_TRIES = 100;
    private final static int SOLVER_TRIES = 20;
    private final static int DIFFICULTY = 35;
    private int[][] correctGrid = new int[SIZE][SIZE];
    private int[][] playerGrid = new int[SIZE][SIZE];

    public void init() {
        long start = System.nanoTime();
        generate();
        System.out.println("CORRECT GRID");
        showGrid(this.correctGrid);
        System.out.println("");
        System.out.println("PLAYER GRID");
        showGrid(playerGrid);
        System.out.println("//////////");
        float time = (((float) System.nanoTime()) - start) / 1000000;
        System.out.println("Time of generation : " + time + "ms.");
    }

    /**
     * Generate a default grid (correct full grid) and
     * a playerGrid with some default value in.
     *
     * @see Sudoku#backtracking(int[][])
     */
    private void generate() {
        fillDefaultValue();
        while (checkNotFull(this.correctGrid)) {
            generateTopLeft();
            generateTopMiddle();
            generateTopRight();
            generateLeftCol();
            while (checkNotFull(this.correctGrid)) {
                if (!backtracking(this.correctGrid)) {
                    generate();
                }
            }
        }
        playerGrid = copyGrid(this.correctGrid);
        for (int i = 0; i < DIFFICULTY; i++) {
            removeOneValue(playerGrid);
        }
    }

    /**
     * Fill the defaultGrid with DEFAULT_VALUE
     */
    private void fillDefaultValue() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                correctGrid[i][j] = DEFAULT_VALUE;
            }
        }
    }

    private void generateTopLeft() {
        ArrayList<Integer> availableNumbers = new ArrayList<>(SIZE);
        Random random = new Random();

        for (int i = 1; i <= SIZE; i++) {
            availableNumbers.add(i);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int value = availableNumbers.get(random.nextInt(availableNumbers.size()));
                correctGrid[i][j] = value;
                availableNumbers.remove(Integer.valueOf(value));
            }
        }
    }

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku();
        sudoku.init();
    }

    private void generateTopRight() {
        Random random = new Random();

        // Top Row
        ArrayList<Integer> topRow = availableInRow(this.correctGrid, 0);
        if (topRow.size() < 3) {
            return;
        } else {
            for (int i = 0; i < 3; i++) {
                int value = topRow.get(random.nextInt(topRow.size()));
                correctGrid[0][6 + i] = value;
                topRow.remove(Integer.valueOf(value));
            }
        }

        // Middle Row
        // Add available number in rows.
        ArrayList<Integer> middleRow = availableInRow(this.correctGrid, 1);
        if (middleRow.size() < 3) {
            return;
        } else {
            for (int i = 0; i < 3; i++) {
                int value = middleRow.get(random.nextInt(middleRow.size()));
                correctGrid[1][6 + i] = value;
                middleRow.remove(Integer.valueOf(value));
            }
        }

        // Bottom Row
        // Same as before
        ArrayList<Integer> bottomRow = availableInRow(this.correctGrid, 2);
        if (bottomRow.size() < 3) {
            return;
        } else {
            for (int i = 0; i < 3; i++) {
                int value = bottomRow.get(random.nextInt(bottomRow.size()));
                correctGrid[2][6 + i] = value;
                bottomRow.remove(Integer.valueOf(value));
            }
        }
    }

    private void generateLeftCol() {
        Random random = new Random();
        ArrayList<Integer> middleRow = availableInCol(this.correctGrid, 0);
        if (middleRow.size() < 6) {
            return;
        } else {
            for (int i = 0; i < 6; i++) {
                int value = middleRow.get(random.nextInt(middleRow.size()));
                correctGrid[3 + i][0] = value;
                middleRow.remove(Integer.valueOf(value));
            }
        }
    }

    /**
     * Backtrack the grid using the candidate values for each row/col and try to complete the grid.
     *
     * @param grid The grid who would be backtracked
     * @return boolean. If the backtracking can't no longer backtrack returns false. If the backtrack worked, returns true.
     */
    private boolean backtracking(int[][] grid) {
        ArrayList<Integer>[][] candidatesValues = getCandidateValue(grid);
        ArrayList<ArrayList<Integer>> biggestValues = new ArrayList<>();
        ArrayList<Integer> iValue = new ArrayList<>();
        ArrayList<Integer> jValue = new ArrayList<>();
        int smallestValue = SIZE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int size = candidatesValues[i][j].size();
                if (smallestValue > size && size != 0) {
                    smallestValue = size;
                }
            }
        }
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (candidatesValues[i][j].size() == smallestValue) {
                    biggestValues.add(candidatesValues[i][j]);
                    iValue.add(i);
                    jValue.add(j);
                }
            }
        }

        if (biggestValues.size() == 0) {
            return false;
        } else {
            Random random = new Random();
            int value = random.nextInt(biggestValues.size());
            grid[iValue.get(value)][jValue.get(value)] = biggestValues.get(value).get(random.nextInt(biggestValues.get(value).size()));
            biggestValues.remove(value);
            iValue.remove(value);
            jValue.remove(value);
        }
        return true;
    }

    private ArrayList<Integer>[][] getCandidateValue(int[][] grid) {
        ArrayList<Integer>[][] candidateValue = new ArrayList[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                candidateValue[i][j] = grid[i][j] == DEFAULT_VALUE ? candidateValue(grid, i, j) : new ArrayList<>(0);
            }
        }
        return candidateValue;
    }

    private ArrayList<Integer> candidateValue(int[][] grid, int row, int col) {
        ArrayList<Integer> rowCandidateNumbers = availableInRow(grid, row);
        ArrayList<Integer> colCandidateNumbers = availableInCol(grid, col);
        ArrayList<Integer> squareCandidateNumbers = availableInSquare(grid, row, col);

        rowCandidateNumbers.retainAll(colCandidateNumbers);
        rowCandidateNumbers.retainAll(squareCandidateNumbers);
        return rowCandidateNumbers;

    }

    private ArrayList<Integer> availableInRow(int[][] grid, int row) {
        ArrayList<Integer> availableNumbers = new ArrayList<>(SIZE);
        ArrayList<Integer> rowNumbers = new ArrayList<>(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            availableNumbers.add(i);
        }
        for (int i = 0; i < SIZE; i++) {
            if (grid[row][i] != DEFAULT_VALUE) {
                rowNumbers.add(grid[row][i]);
            }
        }
        availableNumbers.removeAll(rowNumbers);
        return availableNumbers;
    }

    private ArrayList<Integer> availableInCol(int[][] grid, int col) {
        ArrayList<Integer> availableNumbers = new ArrayList<>(SIZE);
        ArrayList<Integer> colNumbers = new ArrayList<>(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            availableNumbers.add(i);
        }
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][col] != DEFAULT_VALUE) {
                colNumbers.add(grid[i][col]);
            }
        }
        availableNumbers.removeAll(colNumbers);
        return availableNumbers;
    }

    private ArrayList<Integer> availableInSquare(int[][] grid, int row, int col) {
        ArrayList<Integer> availableNumbers = new ArrayList<>(SIZE);
        ArrayList<Integer> squareNumbers = new ArrayList<>(SIZE);

        for (int i = 1; i <= SIZE; i++) {
            availableNumbers.add(i);
        }

        int squareRow = 3 * (row / 3);
        int squareCol = 3 * (col / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[squareRow + i][squareCol + j] != DEFAULT_VALUE) {
                    squareNumbers.add(grid[squareRow + i][squareCol + j]);
                }
            }
        }
        availableNumbers.removeAll(squareNumbers);
        return availableNumbers;
    }

    /**
     * Take a grid and remove some value to create a playerGrid.
     *
     * @param grid The grid who would have some DEFAULT_VALUE in (ex PlayerGrid).
     * @see Sudoku#solver(int[][])
     */
    private void removeOneValue(int[][] grid) {
        Random randomGenerator = new Random();
        int xRand = randomGenerator.nextInt(9);
        int yRand = randomGenerator.nextInt(9);
        if (this.playerGrid[xRand][yRand] != DEFAULT_VALUE) {
            this.playerGrid[xRand][yRand] = DEFAULT_VALUE;
            if (!solver(grid)) {
                this.playerGrid[xRand][yRand] = this.correctGrid[xRand][yRand];
            }
        }
    }

    /**
     * Try to solve the grid according to BACKTRACKING_TRIES and SOLVER_TRIES
     *
     * @param grid The grid who is trying to be solved.
     * @return boolean. True if the grid is solved, false if not.
     */
    private boolean solver(int[][] grid) {
        if (!checkNotFull(grid)) {
            return true;
        }
        for (int i = 0; i < SOLVER_TRIES; i++) {
            int[][] testGrid = copyGrid(grid);
            for (int j = 0; j < BACKTRACKING_TRIES; j++) {
                if (!checkNotFull(testGrid)) {
                    break;
                }
                backtracking(testGrid);
            }
            if (!equalsGrid(this.correctGrid, testGrid)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if the value has DEFAULT_VALUE in.
     *
     * @param grid The grid who is checked.
     * @return boolean. True if the grid has DEFAULT_VALUE in. Otherwise, returns false.
     */
    private boolean checkNotFull(int[][] grid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] == DEFAULT_VALUE) {
                    return true;
                }
            }
        }
        return false;
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] newGrid = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                newGrid[i][j] = grid[i][j];
            }
        }
        return newGrid;
    }

    private boolean equalsGrid(int[][] grid1, int[][] grid2) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid1[i][j] != grid2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showGrid(int[][] grid) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println("");
        }
    }

    private void generateTopMiddle() {
        Random random = new Random();

        // Top Row
        ArrayList<Integer> topRow = availableInRow(this.correctGrid, 0);
        if (topRow.size() > 2) {
            for (int i = 0; i < 3; i++) {
                int value = topRow.get(random.nextInt(topRow.size()));
                correctGrid[0][3 + i] = value;
                topRow.remove(Integer.valueOf(value));
            }
        }

        // Middle Row
        // Add available number in rows.
        ArrayList<Integer> middleRow = availableInRow(this.correctGrid, 1);
        // Only keep the value that are possible in the Square and the row.
        middleRow.retainAll(availableInSquare(this.correctGrid, 0, 3));
        if (middleRow.size() > 2) {
            for (int i = 0; i < 3; i++) {
                int value = middleRow.get(random.nextInt(middleRow.size()));
                correctGrid[1][3 + i] = value;
                middleRow.remove(Integer.valueOf(value));
            }
        }

        // Bottom Row
        // Same as before
        ArrayList<Integer> bottomRow = availableInRow(this.correctGrid, 2);
        bottomRow.retainAll(availableInSquare(this.correctGrid, 0, 3));
        if (bottomRow.size() > 2) {
            for (int i = 0; i < 3; i++) {
                int value = bottomRow.get(random.nextInt(bottomRow.size()));
                correctGrid[2][3 + i] = value;
                bottomRow.remove(Integer.valueOf(value));
            }
        }
    }
}