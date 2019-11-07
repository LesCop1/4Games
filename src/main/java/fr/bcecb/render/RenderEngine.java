package fr.bcecb.render;

import fr.bcecb.resources.*;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBTruetype.*;

public class RenderEngine {
    private final ResourceManager resourceManager;
    private final RenderManager renderManager;
    private final Tessellator tessellator;
    private Window window;

    public RenderEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.renderManager = new RenderManager(this, resourceManager);
        this.window = Window.newInstance("4Games", 800, 600, false);

        GL.createCapabilities();
        Log.RENDER.config("Using OpenGL Version {0}", glGetString(GL_VERSION));

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        this.tessellator = new Tessellator();
        tessellator.begin(GL_TRIANGLE_FAN);
        tessellator.uv(0.0f, 0.0f).vertex(0.0f, 0.0f);
        tessellator.uv(1.0f, 0.0f).vertex(1.0f, 0.0f);
        tessellator.uv(1.0f, 1.0f).vertex(1.0f, 1.0f);
        tessellator.uv(0.0f, 1.0f).vertex(0.0f, 1.0f);
        tessellator.finish();
    }

    public void cleanUp() {
        window.destroy();
        glfwTerminate();
    }

    public void render(StateEngine stateEngine, float partialTick) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        stateEngine.render(renderManager, partialTick);
    }

    public void drawBackground(ResourceHandle<Texture> textureHandle) {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();
        float imageHeight = resourceManager.getResource(textureHandle).getHeight();
        float imageWidth = resourceManager.getResource(textureHandle).getWidth();

        float hR = height / imageHeight;
        float wR = width / imageWidth;
        if (Math.abs(1 - hR) < Math.abs(1 - wR)) {
            float offset = ((hR * imageWidth) - width) / 2;
            renderManager.getRenderEngine().drawTexturedRect(textureHandle, -offset, 0,
                    width + offset, hR * imageHeight);
        } else {
            float offset = ((wR * imageHeight) - height) / 2;
            renderManager.getRenderEngine().drawTexturedRect(textureHandle, 0, -offset,
                    wR * imageWidth, height + offset);
        }
    }

    public void drawTexturedRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY) {
        drawTexturedRect(textureHandle, minX, minY, maxX, maxY, false);
    }

    public void drawTexturedRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY, boolean centered) {
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, ResourceManager.DEFAULT_TEXTURE);

        float width = Math.abs(maxX - minX);
        float height = Math.abs(maxY - minY);

        texture.bind();
        {
            drawRect(minX - (centered ? (width / 2) : 0), minY - (centered ? (height / 2) : 0), maxX - (centered ? (width / 2) : 0), maxY - (centered ? (height / 2) : 0));
        }
        texture.unbind();
    }


    public void drawRect(float minX, float minY, float maxX, float maxY) {
        Matrix4f model = new Matrix4f().translate(minX, minY, 0.0f).scaleXY(maxX - minX, maxY - minY);

        draw(ResourceManager.DEFAULT_SHADER, model);
    }

    public void drawCircle(float x, float y, float radius) {
        Shader shader = resourceManager.getResource(ResourceManager.CIRCLE_SHADER);
        shader.bind();
        {
            shader.uniformFloat("radius", radius);
        }
        shader.unbind();

        Matrix4f model = new Matrix4f().translate(x, y, 0.0f).scaleXY(radius * 2, radius * 2);
        draw(ResourceManager.CIRCLE_SHADER, model);
    }

    public void drawTexturedCircle(ResourceHandle<Texture> textureHandle, float x, float y, float scale) {
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, ResourceManager.DEFAULT_TEXTURE);

        texture.bind();
        {
            drawCircle(x, y, scale);
        }
        texture.unbind();
    }

    public void drawCenteredText(ResourceHandle<Font> fontHandle, String text, float x, float y, float scale, Vector4f color) {
        Font font = resourceManager.getResourceOrDefault(fontHandle, ResourceManager.DEFAULT_FONT);

        float width = 0.0f;
        float height = -32.0f * scale;

        if (font != null) {
            width = getStringWidth(font, text, scale);
        }

        drawText(fontHandle, text, x - (width / 2), y - (height / 2), scale, color);
    }

    public void drawText(ResourceHandle<Font> fontHandle, String text, float x, float y, float scale, Vector4f color) {
        Shader shader = resourceManager.getResource(ResourceManager.FONT_SHADER);
        Font font = resourceManager.getResourceOrDefault(fontHandle, ResourceManager.DEFAULT_FONT);

        Tessellator textTessellator = new Tessellator();

        if (font != null) {
            shader.bind();
            {
                float pixelScale = stbtt_ScaleForPixelHeight(font.getInfo(), 64 * window.getContentScaleY());

                shader.uniformMat4("projection", window.getProjection());
                shader.uniformMat4("model", new Matrix4f().translate(x, y, 0.0f).scaleXY(scale / window.getContentScaleX(), scale / window.getContentScaleY()));
                font.bind();
                {
                    try (MemoryStack stack = MemoryStack.stackPush()) {
                        FloatBuffer xBuffer = stack.mallocFloat(1);
                        FloatBuffer yBuffer = stack.mallocFloat(1);
                        STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();

                        for (int i = 0; i < text.length(); ++i) {
                            int cp = text.codePointAt(i);
                            if (cp == '\n') {
                                yBuffer.put(0, yBuffer.get(0) + (font.getAscent() - font.getDescent() + font.getLineGap()) * pixelScale);
                                xBuffer.put(0, 0.0f);
                                continue;
                            }

                            stbtt_GetBakedQuad(font.getCData(), font.getWidth(), font.getHeight(), cp - 32, xBuffer, yBuffer, quad, true);

                            if (i < text.length() - 1) {
                                xBuffer.put(0, xBuffer.get(0) + stbtt_GetCodepointKernAdvance(font.getInfo(), cp, text.codePointAt(i + 1)) * pixelScale);
                            }

                            textTessellator.begin(GL_TRIANGLE_FAN);
                            textTessellator.color(color.x, color.y, color.z, color.w);
                            textTessellator.uv(quad.s0(), quad.t0()).vertex(quad.x0(), quad.y0());
                            textTessellator.uv(quad.s1(), quad.t0()).vertex(quad.x1(), quad.y0());
                            textTessellator.uv(quad.s1(), quad.t1()).vertex(quad.x1(), quad.y1());
                            textTessellator.uv(quad.s0(), quad.t1()).vertex(quad.x0(), quad.y1());
                            textTessellator.draw();
                        }
                    }
                }
                font.unbind();
            }
            shader.unbind();
        }
    }

    private float getStringWidth(Font font, String text, float scale) {
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

        return width * scale * stbtt_ScaleForPixelHeight(font.getInfo(), 64 * window.getContentScaleY());
    }

    private void draw(ResourceHandle<Shader> shaderResourceHandle, Matrix4f model) {
        Shader shader = resourceManager.getResource(shaderResourceHandle);
        shader.bind();
        {
            shader.uniformMat4("projection", window.getProjection());
            shader.uniformMat4("model", model);
            tessellator.draw();
        }
        shader.unbind();
    }

    public Window getWindow() {
        return window;
    }
}
