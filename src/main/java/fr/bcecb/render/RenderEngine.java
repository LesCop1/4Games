package fr.bcecb.render;

import fr.bcecb.resources.*;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static fr.bcecb.util.Constants.COLOR_WHITE;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.stb.STBTruetype.*;

public class RenderEngine {
    private final ResourceManager resourceManager;
    private final RenderManager renderManager;

    private final Window window;

    private final Matrix4f projection;

    private final Matrix4fStack model;

    private final Tessellator tessellator;
    private final Tessellator textTessellator;

    public RenderEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.renderManager = new RenderManager(this, resourceManager);
        this.window = Window.newInstance("4Games", 800, 600, false);
        this.projection = new Matrix4f();
        this.model = new Matrix4fStack(8);

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

        this.textTessellator = new Tessellator();
    }

    public void cleanUp() {
        window.destroy();
        glfwTerminate();
    }

    public void render(StateEngine stateEngine, float partialTick) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        projection.identity();
        projection.ortho2D(0, window.getWidth(), window.getHeight(), 0);

        model.identity();

        stateEngine.render(renderManager, partialTick);
    }

    public void drawBackground(ResourceHandle<Texture> textureHandle) {
        float width = Window.getCurrentWindow().getWidth();
        float height = Window.getCurrentWindow().getHeight();
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, ResourceManager.DEFAULT_TEXTURE);
        float imageHeight = texture.getHeight();
        float imageWidth = texture.getWidth();

        float aspectRatio = imageWidth/imageHeight;
        float widthRatio = width / imageWidth;
        float heightRatio = height / imageHeight;
        /*if (Math.abs(1 - hR) < Math.abs(1 - wR)) {
            float offset = ((hR * imageWidth) - width) / 2;
            drawTexturedRect(-offset, 0, width + offset, hR * imageHeight, textureHandle);
        } else {
            float offset = ((wR * imageHeight) - height) / 2;
            drawTexturedRect(0, -offset, wR * imageWidth, height + offset, textureHandle);
        }*/

        model.pushMatrix();
        {
            drawTexturedRect(0, 0, imageWidth, imageHeight, false, textureHandle);
        }
        model.popMatrix();
    }

    public void drawTexturedRect(float minX, float minY, float maxX, float maxY, ResourceHandle<Texture> textureHandle) {
        drawTexturedRect(minX, minY, maxX, maxY, false, textureHandle);
    }

    public void drawTexturedRect(float minX, float minY, float maxX, float maxY, boolean centered, ResourceHandle<Texture> textureHandle) {
        float width = Math.abs(maxX - minX);
        float height = Math.abs(maxY - minY);

        if (centered) {
            model.pushMatrix();
            model.translate(-(width / 2), -(height / 2), 0);
        }

        drawRect(minX, minY, maxX, maxY, textureHandle);

        if (centered) {
            model.popMatrix();
        }
    }

    public void drawColoredRect(float minX, float minY, float maxX, float maxY, Vector4f color) {
        Shader shader = resourceManager.getResource(ResourceManager.DEFAULT_SHADER);
        shader.bind();
        {
            shader.uniformVec4("overrideColor", color);
        }
        shader.unbind();
        drawRect(minX, minY, maxX, maxY, null);
    }

    public void drawRect(float minX, float minY, float maxX, float maxY, ResourceHandle<Texture> textureHandle) {
        model.pushMatrix();
        {
            model.translate(minX, minY, 0.0f);
            model.scaleXY(maxX - minX, maxY - minY);

            draw(ResourceManager.DEFAULT_SHADER, textureHandle);
        }
        model.popMatrix();
    }

    public void drawTexturedCircle(float x, float y, float scale, ResourceHandle<Texture> textureHandle) {
        drawCircle(x, y, scale, textureHandle);
    }

    public void drawCircle(float x, float y, float radius, ResourceHandle<Texture> textureHandle) {
        Shader shader = resourceManager.getResource(ResourceManager.CIRCLE_SHADER);
        shader.bind();
        {
            shader.uniformFloat("radius", radius);
        }
        shader.unbind();

        model.pushMatrix();
        {
            model.translate(x, y, 0.0f);
            model.scaleXY(radius * 2, radius * 2);
            draw(ResourceManager.CIRCLE_SHADER, textureHandle);
        }
        model.popMatrix();
    }

    public void drawCenteredText(ResourceHandle<Font> fontHandle, String text, float x, float y, float scale, Vector4f color) {
        Font font = resourceManager.getResourceOrDefault(fontHandle, ResourceManager.DEFAULT_FONT);

        if (font != null) {
            float height = ((font.getDescent() - font.getAscent()) / 2.0f) * stbtt_ScaleForPixelHeight(font.getInfo(), 32 * window.getContentScaleY()) * scale;
            float width = getStringWidth(font, text) * scale;

            model.pushMatrix();
            {
                model.translate(-(width / 2.0f) / window.getContentScaleX(), -(height / 2.0f) / window.getContentScaleY(), 0);
                drawText(fontHandle, text, x, y, scale, color);
            }
            model.popMatrix();
        }
    }

    public void drawText(ResourceHandle<Font> fontHandle, String text, float x, float y, float scale, Vector4f color) {
        Shader shader = resourceManager.getResource(ResourceManager.FONT_SHADER);
        Font font = resourceManager.getResourceOrDefault(fontHandle, ResourceManager.DEFAULT_FONT);

        model.pushMatrix();
        {
            if (font != null) {
                model.translate(x, y, 0);
                model.scaleXY(scale / window.getContentScaleX(), scale / window.getContentScaleY());

                shader.bind();
                {
                    float pixelScale = stbtt_ScaleForPixelHeight(font.getInfo(), 32 * scale * window.getContentScaleY());
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

                                shader.uniformMat4("projection", projection);
                                shader.uniformMat4("model", model);
                                textTessellator.draw();
                            }
                        }
                    }
                    font.unbind();
                }
                shader.unbind();
            }
        }
        model.popMatrix();
    }

    private float getStringWidth(Font font, String text) {
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

        return width * stbtt_ScaleForPixelHeight(font.getInfo(), 32 * window.getContentScaleY());
    }

    private void draw(ResourceHandle<Shader> shaderResourceHandle, ResourceHandle<Texture> textureHandle) {
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, ResourceManager.DEFAULT_TEXTURE);
        Shader shader = resourceManager.getResource(shaderResourceHandle);
        texture.bind();
        shader.bind();
        {
            shader.uniformMat4("projection", projection);
            shader.uniformMat4("model", model);
            tessellator.draw();
            shader.uniformVec4("overrideColor", COLOR_WHITE);
        }
        shader.unbind();
        texture.unbind();
    }

    public Window getWindow() {
        return window;
    }
}
