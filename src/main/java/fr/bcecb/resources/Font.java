package fr.bcecb.resources;

import fr.bcecb.render.Window;
import fr.bcecb.util.Resources;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL45.GL_RED;
import static org.lwjgl.stb.STBTruetype.*;

public class Font extends Texture {
    private ByteBuffer ttf;
    private STBTTFontinfo info = STBTTFontinfo.create();

    private final STBTTBakedChar.Buffer cdata = STBTTBakedChar.malloc(256);

    private int ascent;
    private int descent;
    private int lineGap;

    public Font() {
        this.width = Math.round(512 * Window.getCurrentWindow().getContentScaleX());
        this.height = Math.round(512 * Window.getCurrentWindow().getContentScaleY());
    }

    @SuppressWarnings("unused")
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

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pAscent = stack.mallocInt(1);
            IntBuffer pDescent = stack.mallocInt(1);
            IntBuffer pLineGap = stack.mallocInt(1);

            stbtt_GetFontVMetrics(info, pAscent, pDescent, pLineGap);

            this.ascent = pAscent.get(0);
            this.descent = pDescent.get(0);
            this.lineGap = pLineGap.get(0);
        }

        ByteBuffer bitmap = BufferUtils.createByteBuffer(width * height);
        stbtt_BakeFontBitmap(ttf, 32 * Window.getCurrentWindow().getContentScaleY(), bitmap, width, height, 32, cdata);

        return generate(bitmap, GL_RED);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.cdata.free();
    }
}