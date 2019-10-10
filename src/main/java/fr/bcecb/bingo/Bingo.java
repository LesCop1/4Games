package fr.bcecb.bingo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bingo {
    private List<Integer> numberList = new ArrayList<>();
    private List<Integer> grid = new ArrayList<>();
    // 0-9 ligne 1
    // 10-19 ligne 2
    // 20-29 ligne 3

    public void initGame() {
        int i = 1;
        while (i < 100) {
            numberList.add(i - 1, i);
            i++;
        }

    }

    public void dispList() {
        int i = 1;
        while (i < numberList.size()) {
            System.out.print(numberList.get(i - 1) + " ;");
            i++;
        }
        System.out.println("\n");
    }

    public int dropNumber() {
        Random rand = new Random();
        int randInt = rand.nextInt(numberList.size() - 1 + 1) + 1;
        int number;
        number = numberList.get(randInt);
        numberList.remove(randInt);
        return number;
    }

    public List<Integer> randIndexList(int minValue, int maxValue, int n) {
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        int randValue;

        while (list.size() < n) {
            while (list.size() < 5) {
                randValue = rand.nextInt(9 - minValue + 1) + minValue;
                if (randValue != 0) {
                    if (list.isEmpty()) {
                        list.add(randValue);

                    } else if (!valInList(list, randValue)) {
                        list.add(randValue);
                    }
                }
            }
            while (list.size() < 10) {
                randValue = rand.nextInt(19 - 10 + 1) + 10;
                if (randValue != 0) {
                    if (list.isEmpty()) {
                        list.add(randValue);

                    } else if (!valInList(list, randValue)) {
                        list.add(randValue);
                    }
                }
            }
            while (list.size() < 15) {
                randValue = rand.nextInt(29 - 20 + 1) + 20;
                if (randValue != 0) {
                    if (list.isEmpty()) {
                        list.add(randValue);

                    } else if (!valInList(list, randValue)) {
                        list.add(randValue);
                    }
                }
            }

        }

        return list;
    }

    public boolean valInList(List<Integer> list, int val) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == val) {
                return true;
            }
        }
        return false;
    }


    public void fillGrid() {
        int k = 0;
        while (k < 30) {

            grid.add(k, 0);
            k++;
        }

        Random rand = new Random();
        int randInt;

        List<Integer> indexList = randIndexList(0, 29, 15);

        for (int i : indexList) {
            System.out.println(i);
            switch (i % 10) {
                case 0:
                    randInt = rand.nextInt(9 + 1);
                    grid.set(i, randInt);
                    break;
                case 1:
                    randInt = rand.nextInt(19 - 10 + 1) + 10;
                    grid.set(i, randInt);
                    break;
                case 2:
                    randInt = rand.nextInt(29 - 20 + 1) + 20;
                    grid.set(i, randInt);
                    break;
                case 3:
                    randInt = rand.nextInt(39 - 30 + 1) + 30;
                    grid.set(i, randInt);
                    break;
                case 4:
                    randInt = rand.nextInt(49 - 40 + 1) + 40;
                    grid.set(i, randInt);
                    break;
                case 5:
                    randInt = rand.nextInt(59 - 50 + 1) + 50;
                    grid.set(i, randInt);
                    break;
                case 6:
                    randInt = rand.nextInt(69 - 60 + 1) + 60;
                    grid.set(i, randInt);
                    break;
                case 7:
                    randInt = rand.nextInt(79 - 70 + 1) + 70;
                    grid.set(i, randInt);
                    break;
                case 8:
                    randInt = rand.nextInt(89 - 80 + 1) + 80;
                    grid.set(i, randInt);
                    break;
                case 9:
                    randInt = rand.nextInt(99 - 90 + 1) + 90;
                    grid.set(i, randInt);
                    break;
            }
        }
    }


    public void dispGrid() {
        int i = 0;
        while (i < 30) {
            if (grid.get(i) == 0) {
                System.out.print("X | ");
            } else {
                System.out.print(grid.get(i) + " | ");
            }
            if (i == 9 || i == 19) {
                System.out.println();
            }
            i++;
        }
    }
}

