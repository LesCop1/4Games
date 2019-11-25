package fr.bcecb.util;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Constants {
    /* COLORS */
    public static final Vector4f COLOR_WHITE = new Vector4f(1.0f);
    public static final Vector4f COLOR_BLACK = new Vector4f();
    public static final Vector4f COLOR_GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
    public static final Vector4f COLOR_LIGHT_GREY = new Vector4f(0.8f, 0.8f, 0.8f, 1f);
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
    public static final ResourceHandle<Texture> BINGO_BACKGROUND = new ResourceHandle<>("textures/bingo/bingoBG.png") {};
    public static final ResourceHandle<Texture> BINGO_CASE = new ResourceHandle<>("textures/bingo/caseBG.png") {};
    public static final ResourceHandle<Texture> BINGO_CASE_HOVERED = new ResourceHandle<>("textures/bingo/caseBGhovered.png") {};
    public static final ResourceHandle<Texture> BINGO_CASE_CHECKED = new ResourceHandle<>("textures/bingo/caseBGchecked.png") {};

    /* BS */
    public static final ResourceHandle<Texture> BS_BACKGROUND = new ResourceHandle<>("textures/BatailleNavale/background_battleship.jpg") {};


    /* POKER */
    public static final ResourceHandle<Texture> POKER_BACKGROUND = new ResourceHandle<Texture>("textures/poker/poker_background.jpg") {};
    public static final ResourceHandle<Texture> POKER_BACK_CARD = new ResourceHandle<Texture>("textures/poker/back_card.png") {};
    public static final ResourceHandle<Texture> POKER_HBACK_CARD = new ResourceHandle<Texture>("textures/poker/horizontal_back_card.png") {};
    public static final ResourceHandle<Texture> POKER_BUTTON = new ResourceHandle<Texture>("textures/poker/btn_texture.jpg") {};
    public static final ResourceHandle<Texture> POKER_CLUB = new ResourceHandle<Texture>("textures/poker/club.png") {};
    public static final ResourceHandle<Texture> POKER_DIAMOND = new ResourceHandle<Texture>("textures/poker/diamond.png") {};
    public static final ResourceHandle<Texture> POKER_HEART = new ResourceHandle<Texture>("textures/poker/heart.png") {};
    public static final ResourceHandle<Texture> POKER_SPADE = new ResourceHandle<Texture>("textures/poker/spade.png") {};

    /* GAME BASED VARS */
    public static final Collector<?, ?, ?> SHUFFLER = Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
        Collections.shuffle(list);
        return list;
    });
    /* PROFILE */
    public static final String MONEY_NAME = "FourCoins";

    public static <T> Collector<T, ?, List<T>> toShuffledList() {
        return (Collector<T, ?, List<T>>) Constants.SHUFFLER;
    }

    public enum GameType {
        SUDOKU("Sudoku", 2),
        BINGO("Bingo", 2),
        BATTLESHIP("Bataille Navale", 1),
        POKER("Poker", 2);

        private final String name;
        private final int nbState;

        GameType(String name, int nbState) {
            this.name = name;
            this.nbState = nbState;
        }

        public String getName() {
            return name;
        }

        public int getNbState() {
            return nbState;
        }
    }
    public static final String MONEY_NAME_SHORT = "FC";
    public static int BANKROLL = 0;

    public static Map<GameType, Long> BEST_TIMES = new EnumMap<>(GameType.class) {{
        put(GameType.SUDOKU, 0L);
        put(GameType.BINGO, 0L);
        put(GameType.BATTLESHIP, 0L);
        put(GameType.POKER, 52543L);
    }};

    private Constants() {
    }
}
