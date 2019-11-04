package fr.bcecb.resources;

import fr.bcecb.util.Resources;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBTruetype.*;

public class Font extends Texture {
    private STBTTFontinfo info = STBTTFontinfo.create();

    private final STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(256);

    private int ascent;
    private int descent;
    private int lineGap;

    public Font() {
        this.width = 512;
        this.height = 512;
    }

    public STBTTFontinfo getInfo() {
        return info;
    }

    public STBTTBakedChar.Buffer getCData() {
        return cdata;
    }

    public int getAscent() {
        return ascent;
    }

    public int getDescent() {
        return descent;
    }

    public int getLineGap() {
        return lineGap;
    }

    @Override
    public int create(InputStream inputStream) throws IOException {
        ByteBuffer ttf = Resources.readBytes(inputStream);

        if (!stbtt_InitFont(info, ttf)) {
            return 0;
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pAscent = stack.mallocInt(1);
            IntBuffer pDescent = stack.mallocInt(1);
            IntBuffer pLineGap = stack.mallocInt(1);

            stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

            this.ascent = pAscent.get(0);
            this.descent = pDescent.get(0);
            this.lineGap = pLineGap.get(0);
        }

        ByteBuffer bitmap = BufferUtils.createByteBuffer(512 * 512);
        stbtt_BakeFontBitmap(ttf, 64, bitmap, 512, 512, 32, cdata);

        int texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, 512, 512, 0, GL_RED, GL_UNSIGNED_BYTE, bitmap);

        return texture;
    }
}