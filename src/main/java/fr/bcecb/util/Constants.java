package fr.bcecb.util;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Constants {
    /* COLORS */
    public static final Vector4f COLOR_WHITE = new Vector4f(1.0f);
    public static final Vector4f COLOR_BLACK = new Vector4f();
    public static final Vector4f COLOR_GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
    public static final Vector4f COLOR_RED = new Vector4f(1f, 0f, 0f, 1f);
    public static final Vector4f COLOR_GREEN = new Vector4f(0f, 1f, 0f, 1f);

    /* SUDOKU */
    public static final ResourceHandle<Texture> SUDOKU_BACKGROUND = new ResourceHandle<>("textures/sudoku/sudokuBackground.png") {
    };
    public static final ResourceHandle<Texture> SUDOKU_FIXED_CASE = new ResourceHandle<>("textures/sudoku/caseSudokuBase.png") {
    };
    public static final ResourceHandle<Texture> SUDOKU_FREE_CASE = new ResourceHandle<>("textures/sudoku/caseSudoku.png") {
    };
    public static final ResourceHandle<Texture> SUDOKU_CANDIDATES_VALUE_CASE = new ResourceHandle<>("textures/sudoku/candidateValuesTextures.png") {
    };

    /* BINGO */
    public static final ResourceHandle<Texture> BINGO_BACKGROUND = new ResourceHandle<>("textures/bingo/bingoBG.png") {
    };
    public static final ResourceHandle<Texture> BINGO_CASE = new ResourceHandle<>("textures/bingo/caseBG.png") {
    };
    public static final ResourceHandle<Texture> BINGO_EMPTY_CASE = new ResourceHandle<>("textures/bingo/emptyCaseBG.png") {
    };
    public static final ResourceHandle<Texture> BINGO_CASE_OK = new ResourceHandle<>("textures/bingo/caseBGOK.png") {
    };
    public static final ResourceHandle<Texture> BINGO_BUTTON = new ResourceHandle<>("textures/bingo/button.png") {
    };
    public static final ResourceHandle<Texture> BINGO_BUTTON_HOVER = new ResourceHandle<>("textures/bingo/buttonHover.png") {
    };

    /* BS */
    public static final ResourceHandle<Texture> BS_BACKGROUND = new ResourceHandle<>("textures/BatailleNavale/background_battleship.jpg") {};
    public static final ResourceHandle<Texture> BS_DEFAULT_TEXTURE = new ResourceHandle<>("textures/BatailleNavale/caseBattleship.png") {};
    public static final ResourceHandle<Texture> BS_TORPEDO_H = new ResourceHandle<>("textures/BatailleNavale/Torpedo_H.png") {};
    public static final ResourceHandle<Texture> BS_SUBMARINE_H = new ResourceHandle<>("textures/BatailleNavale/Submarine_H.png") {};
    public static final ResourceHandle<Texture> BS_FRIGATE_H = new ResourceHandle<>("textures/BatailleNavale/Frigate_H.png") {};
    public static final ResourceHandle<Texture> BS_CRUISER_H = new ResourceHandle<>("textures/BatailleNavale/Cruiser_H.png") {};
    public static final ResourceHandle<Texture> BS_AIRCRAFT_CARRIER_H = new ResourceHandle<>("textures/BatailleNavale/Aircraft_Carrier_H.png") {};
    public static final ResourceHandle<Texture> BS_TORPEDO_V = new ResourceHandle<>("textures/BatailleNavale/Torpedo_V.png") {};
    public static final ResourceHandle<Texture> BS_SUBMARINE_V = new ResourceHandle<>("textures/BatailleNavale/Submarine_V.png") {};
    public static final ResourceHandle<Texture> BS_FRIGATE_V = new ResourceHandle<>("textures/BatailleNavale/Frigate_V.png") {};
    public static final ResourceHandle<Texture> BS_CRUISER_V = new ResourceHandle<>("textures/BatailleNavale/Cruiser_V.png") {};
    public static final ResourceHandle<Texture> BS_AIRCRAFT_CARRIER_V = new ResourceHandle<>("textures/BatailleNavale/Aircraft_Carrier_V.png") {};


    /* POKER */
    public static final ResourceHandle<Texture> POKER_BACKGROUND = new ResourceHandle<>("textures/poker/poker_background.jpg") {};
    public static final ResourceHandle<Texture> POKER_BACK_CARD = new ResourceHandle<>("textures/poker/back_card.png") {};
    public static final ResourceHandle<Texture> POKER_HBACK_CARD = new ResourceHandle<>("textures/poker/horizontal_back_card.png") {};
    public static final ResourceHandle<Texture> POKER_BUTTON = new ResourceHandle<>("textures/poker/btn_texture.jpg") {};
    public static final ResourceHandle<Texture> POKER_CLUB = new ResourceHandle<>("textures/poker/club.png") {};
    public static final ResourceHandle<Texture> POKER_DIAMOND = new ResourceHandle<>("textures/poker/diamond.png") {};
    public static final ResourceHandle<Texture> POKER_HEART = new ResourceHandle<>("textures/poker/heart.png") {};
    public static final ResourceHandle<Texture> POKER_SPADE = new ResourceHandle<>("textures/poker/spade.png") {};

    /* GAME BASED VARS */
    public static final String MONEY_NAME = "FourCoins";
    public static final String MONEY_NAME_SHORT = "FC";
    public static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
        Collections.shuffle(list);
        return list;
    });
    public static int BANKROLL = 0;

    private Constants() {
    }

    public static <T> Collector<T, ?, List<T>> toShuffledList() {
        return (Collector<T, ?, List<T>>) Constants.SHUFFLER;
    }

    public enum GameType {
        SUDOKU("Sudoku", 2, 0, 0),
        BINGO("Bingo", 2, 0, 0),
        BATTLESHIP("Bataille Navale", 1, 0, 0),
        POKER("Poker", 2, 0, 0);

        private final String name;
        private final int nbState;
        private int bestScore;
        private long bestTime;

        GameType(String name, int nbState, int bestScore, long bestTime) {
            this.name = name;
            this.nbState = nbState;
            this.bestScore = bestScore;
            this.bestTime = bestTime;
        }

        public String getName() {
            return name;
        }

        public int getNbState() {
            return nbState;
        }

        public int getBestScore() {
            return bestScore;
        }

        public void setBestScore(int bestScore) {
            this.bestScore = bestScore;
        }

        public long getBestTime() {
            return bestTime;
        }

        public void setBestTime(long bestTime) {
            this.bestTime = bestTime;
        }
    }
}
