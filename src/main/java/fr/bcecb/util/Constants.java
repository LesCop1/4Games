package fr.bcecb.util;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

import java.util.EnumMap;
import java.util.Map;

public class Constants {
    /* COLORS */
    public static final Vector4f COLOR_WHITE = new Vector4f(1.0f);
    public static final Vector4f COLOR_BLACK = new Vector4f();
    public static final Vector4f COLOR_GREY = new Vector4f(0.5f, 0.5f, 0.5f, 1f);
    public static final Vector4f COLOR_GREEN = new Vector4f(0f, 1f, 0f, 1f);

    public static final ResourceHandle<Texture> CURRENT_PROFILE_TEXTURE = new ResourceHandle<>("textures/defaultProfile.jpg") {
    };

    public static final String MONEY_NAME = "FourCoins";
    public static final String MONEY_NAME_SHORT = "FC";
    public static int BANKROLL = 54;

    public static Map<GameType, Long> BEST_TIMES = new EnumMap<>(GameType.class) {{
        put(GameType.SUDOKU, 52543L);
        put(GameType.BINGO, 52543L);
        put(GameType.BATTLESHIP, 52543L);
        put(GameType.POKER, 52543L);
    }};

    private Constants() {
    }

    public enum GameType {
        SUDOKU("Sudoku", 2),
        BINGO("Bingo", 2),
        BATTLESHIP("Bataille Navale", 2),
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
}
