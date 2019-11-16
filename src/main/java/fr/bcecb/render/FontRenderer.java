package fr.bcecb.render;

import fr.bcecb.resources.Font;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.util.Render;
import fr.bcecb.util.Resources;
import fr.bcecb.util.Transform;
import org.lwjgl.stb.STBTTAlignedQuad;

import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.stb.STBTruetype.*;

public class FontRenderer implements AutoCloseable {
    private final RenderManager renderManager;
    private final ResourceManager resourceManager;
    private final Mesh.ReusableBuilder textMeshBuilder = new Mesh.ReusableBuilder(4);

    private float guiScale;

    private Font font;

    public FontRenderer(RenderManager renderManager, ResourceManager resourceManager) {
        this.renderManager = renderManager;
        this.resourceManager = resourceManager;

        this.font = resourceManager.loadResource(Resources.DEFAULT_FONT);
    }

    public void drawStringBoxed(String string, float x, float y, float width, float height) {
        this.drawString(string, x, y, 1.0f, true); /* TODO Scale text to fit box */
    }

    public void drawString(String string, float x, float y, boolean centered) {
        this.drawString(string, x, y, 1.0f, centered);
    }

    public void drawString(String string, float x, float y, float scale, boolean centered) {
        Transform transform = Render.pushTransform();
        {
            if (font != null) {
                float fontScale = this.getFontScale(scale);

                font.bind();
                {
                    transform.translate(x, y);
                    transform.scale(fontScale, fontScale);

                    if (centered) {
                        float xOffset = this.getStringWidth(string) * fontScale / 2.0f;
                        float yOffset = (-this.getFontHeight() / 2.0f) * fontScale / 2.0f;

                        transform.translate(-xOffset, -yOffset);
                    }

                    float[] xBuffer = new float[1];
                    float[] yBuffer = new float[1];
                    STBTTAlignedQuad quad = STBTTAlignedQuad.malloc();

                    for (int i = 0; i < string.length(); ++i) {
                        int cp = string.codePointAt(i);
                        if (cp == '\n') {
                            yBuffer[0] += (this.getFontHeight() - font.getLineGap()) * fontScale;
                            xBuffer[0] = 0.0f;
                            continue;
                        }

                        stbtt_GetBakedQuad(font.getCData(), font.getWidth(), font.getHeight(), cp - 32, xBuffer, yBuffer, quad, true);

                        if (i < string.length() - 1) {
                            xBuffer[0] += stbtt_GetCodepointKernAdvance(font.getInfo(), cp, string.codePointAt(i + 1)) * fontScale;
                        }

                        textMeshBuilder.reset();
                        textMeshBuilder.uv(quad.s0(), quad.t0()).vertex(quad.x0(), quad.y0());
                        textMeshBuilder.uv(quad.s1(), quad.t0()).vertex(quad.x1(), quad.y0());
                        textMeshBuilder.uv(quad.s1(), quad.t1()).vertex(quad.x1(), quad.y1());
                        textMeshBuilder.uv(quad.s0(), quad.t1()).vertex(quad.x0(), quad.y1());

                        renderManager.draw(textMeshBuilder.build(GL_TRIANGLE_FAN), Resources.FONT_SHADER);
                    }
                }
                font.unbind();
            }
        }
        Render.popTransform();
    }

    public int getStringWidth(String string) {
        int width = 0;

        int[] advanceWidth = new int[1];

        for (int i = 0; i < string.length(); ++i) {
            int cp = string.codePointAt(i);

            stbtt_GetCodepointHMetrics(font.getInfo(), cp, advanceWidth, new int[1]);
            width += advanceWidth[0];

            if (i < string.length() - 1) {
                width += stbtt_GetCodepointKernAdvance(font.getInfo(), cp, string.codePointAt(i + 1));
            }
        }

        return width;
    }

    public int getFontHeight() {
        return Math.abs(font.getDescent() - font.getAscent());
    }

    public float getFontScale(float scale) {
        return stbtt_ScaleForPixelHeight(font.getInfo(), font.getFontHeight() * scale);
    }

    public Font getFont() {
        return font;
    }

    public void setFont(ResourceHandle<Font> fontResourceHandle) {
        this.font.dispose();

        this.font = resourceManager.loadResource(fontResourceHandle);
    }

    public void setGuiScale(float guiScale) {
        this.guiScale = guiScale;
    }

    @Override
    public void close() {
        this.font.dispose();

        this.textMeshBuilder.close();
    }
}
