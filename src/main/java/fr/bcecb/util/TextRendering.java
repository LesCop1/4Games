package fr.bcecb.util;

import fr.bcecb.render.Window;
import fr.bcecb.resources.Font;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.stb.STBTruetype.*;

public class TextRendering {
    public static float getStringWidth(Font font, String text) {
        int width = 0;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pAdvancedWidth = stack.mallocInt(1);
            IntBuffer pLeftSideBearing = stack.mallocInt(1);

            for (int i = 0; i < text.length(); ++i) {
                int cp = text.codePointAt(i);

                stbtt_GetCodepointHMetrics(font.getInfo(), cp, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if (i < text.length() - 1) {
                    width += stbtt_GetCodepointKernAdvance(font.getInfo(), cp, text.codePointAt(i + 1));
                }
            }
        }

        return width * stbtt_ScaleForPixelHeight(font.getInfo(), 32 * Window.getCurrentWindow().getContentScaleY());
    }
}
