package fr.bcecb.resources;

import fr.bcecb.util.Resources;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL45.GL_RED;
import static org.lwjgl.stb.STBTruetype.*;

public class Font extends Texture {
    private final STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(256);

    private ByteBuffer ttf;
    private STBTTFontinfo info = STBTTFontinfo.create();

    private int ascent;
    private int descent;
    private int lineGap;

    public Font() {
        this.width = 1024;
        this.height = 1024;
    }

    public ByteBuffer getTTF() {
        return ttf;
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
        this.ttf = Resources.readBytes(inputStream);

        if (!stbtt_InitFont(info, ttf)) {
            return 0;
        }

        int[] ascent = new int[1];
        int[] descent = new int[1];
        int[] lineGap = new int[1];

        stbtt_GetFontVMetrics(info, ascent, descent, lineGap);

        this.ascent = ascent[0];
        this.descent = descent[0];
        this.lineGap = lineGap[0];

        ByteBuffer bitmap = BufferUtils.createByteBuffer(width * height);
        stbtt_BakeFontBitmap(ttf, this.getFontHeight(), bitmap, width, height, 32, cdata);

        return generate(bitmap, GL_RED);
    }

    public float getFontHeight() {
        return 128;
    }

    @Override
    public void dispose() {
        super.dispose();
        this.cdata.free();
    }
}