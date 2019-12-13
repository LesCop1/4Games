package fr.bcecb.util;

import fr.bcecb.Achievement;
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
    public static final Vector4f COLOR_RED = new Vector4f(1f, 0f, 0f, 1f);
    public static final Vector4f COLOR_GREEN = new Vector4f(0f, 1f, 0f, 1f);


    public static final Vector4f COLOR_BANNER = new Vector4f(0.569f, 0.569f, 0.569f, 0.75f);

    /* SUDOKU */
    public static final ResourceHandle<Texture> SUDOKU_BACKGROUND = new ResourceHandle<>("textures/sudoku/sudokuBackground.png") {};
    public static final ResourceHandle<Texture> SUDOKU_FIXED_CASE = new ResourceHandle<>("textures/sudoku/caseSudokuBase.png") {};
    public static final ResourceHandle<Texture> SUDOKU_FREE_CASE = new ResourceHandle<>("textures/sudoku/caseSudoku.png") {};

    /* BINGO */
    public static final ResourceHandle<Texture> BINGO_BACKGROUND = new ResourceHandle<>("textures/bingo/bingoBG.png") {};
    public static final ResourceHandle<Texture> BINGO_CASE = new ResourceHandle<>("textures/bingo/caseBG.png") {};
    public static final ResourceHandle<Texture> BINGO_EMPTY_CASE = new ResourceHandle<>("textures/bingo/emptyCaseBG.png") {};
    public static final ResourceHandle<Texture> BINGO_CASE_OK = new ResourceHandle<>("textures/bingo/caseBGOK.png") {};
    public static final ResourceHandle<Texture> BINGO_BUTTON = new ResourceHandle<>("textures/bingo/button.png") {};
    public static final ResourceHandle<Texture> BINGO_BUTTON_HOVER = new ResourceHandle<>("textures/bingo/buttonHover.png") {};

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
    public static final ResourceHandle<Texture> BS_J1 = new ResourceHandle<>("textures/BatailleNavale/j1.png"){};
    public static final ResourceHandle<Texture> BS_J2 = new ResourceHandle<>("textures/BatailleNavale/j2.png"){};

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
    public static final String MONEY_NAME = "FourCoins";
    public static final String MONEY_NAME_SHORT = "FC";

    /* ACHIEVEMENTS */
    private static final Achievement a1 = new Achievement(1, GameType.SUDOKU, "e", "get gud", "potatoes");
    private static final Achievement a2 = new Achievement(1, GameType.SUDOKU, "ea", "get gud", "potatoes");
    private static final Achievement a3 = new Achievement(1, GameType.SUDOKU, "ez", "get gud", "potatoes");

    public static final Map<GameType, List<Achievement>> achievements = new HashMap<>() {{
        put(GameType.SUDOKU, new ArrayList<>() {{
            add(a1);
            add(a2);
            add(a3);
            add(a1);
        }});
        put(GameType.BINGO, new ArrayList<>());
        put(GameType.BATTLESHIP, new ArrayList<>());
        put(GameType.POKER, new ArrayList<>());
    }};

    /* SHOP */
    private static final ItemDescriptor ppi1 = new ItemDescriptor(1, new ResourceHandle<>("textures/default/default.png") {}, "Le gamer casque", "no", 25);
    private static final ItemDescriptor ppi2 = new ItemDescriptor(2, new ResourceHandle<>("textures/default/default.png") {}, "ta", "ee", 50);
    private static final ItemDescriptor ppi3 = new ItemDescriptor(3, new ResourceHandle<>("textures/default/default.png") {}, "mer", "eea", 75);
    private static final ItemDescriptor ppi4 = new ItemDescriptor(4, new ResourceHandle<>("textures/default/default.png") {}, "la", "eeee", 100);
    private static final ItemDescriptor ppi5 = new ItemDescriptor(5, new ResourceHandle<>("textures/default/default.png") {}, "pu", "eeeee", 125);
    private static final ItemDescriptor ppi6 = new ItemDescriptor(6, new ResourceHandle<>("textures/default/default.png") {}, "a", "eeeeeee", 150);
    private static final ItemDescriptor ppi7 = new ItemDescriptor(7, new ResourceHandle<>("textures/default/default.png") {}, "led", "ea", 175);

    public static final List<ItemDescriptor> shopItems = new ArrayList<>() {{
        add(ppi1);
        add(ppi2);
        add(ppi3);
        add(ppi4);
        add(ppi5);
        add(ppi6);
        add(ppi7);
    }};

    /* PROFILE */
    public static final String PROFILE_FILE_PATH = System.getProperty("user.dir") + "\\profile.json";

    private Constants() {
    }

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

    public enum ProfilePicture {
        DEFAULT
    }
}
