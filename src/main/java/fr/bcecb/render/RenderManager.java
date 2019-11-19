package fr.bcecb.render;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Shader;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateManager;
import fr.bcecb.util.*;

import static org.lwjgl.opengl.GL45.*;

public class RenderManager implements AutoCloseable {
    private final ResourceManager resourceManager;
    private final RendererRegistry rendererRegistry;

    private final FontRenderer fontRenderer;

    private final Mesh quadMesh;
    private final Mesh.ReusableBuilder lineMeshBuilder = new Mesh.ReusableBuilder(2);

    public RenderManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.rendererRegistry = new RendererRegistry(this, resourceManager);

        this.fontRenderer = new FontRenderer(this, resourceManager);

        Log.RENDER.config("Using OpenGL Version {0}", glGetString(GL_VERSION));

        glEnable(GL_MULTISAMPLE);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        Mesh.Builder baseMeshBuilder = new Mesh.Builder(4);
        baseMeshBuilder.uv(0.0f, 0.0f).vertex(0.0f, 0.0f);
        baseMeshBuilder.uv(1.0f, 0.0f).vertex(1.0f, 0.0f);
        baseMeshBuilder.uv(1.0f, 1.0f).vertex(1.0f, 1.0f);
        baseMeshBuilder.uv(0.0f, 1.0f).vertex(0.0f, 1.0f);

        this.quadMesh = baseMeshBuilder.build(GL_TRIANGLE_FAN);
    }

    public void render(StateManager stateEngine, float partialTick) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);

        stateEngine.render(rendererRegistry, partialTick);
    }

    public void drawBackground(ResourceHandle<Texture> textureHandle, int width, int height) {
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, Resources.DEFAULT_TEXTURE);
        int imageWidth = texture.getWidth();
        int imageHeight = texture.getHeight();
        float aspectRatio = MathHelper.upscaleRatio(imageWidth, imageHeight, width, height);

        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(width / 2.0f, height / 2.0f);
            transform.scale(aspectRatio, aspectRatio);

            drawRect(textureHandle, 0, 0, imageWidth, imageHeight, true);
        }
        RenderHelper.popTransform();
    }

    public void drawRoundedRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY, float radius) {
        drawRoundedRect(textureHandle, minX, minY, maxX, maxY, radius, false);
    }

    public void drawRoundedRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY, float radius, boolean centered) {
        Transform transform = RenderHelper.pushTransform();
        Shader shader = resourceManager.getResource(Resources.ROUNDED_SHADER);

        radius = Math.min(radius, Math.min((maxX - minX) / 2, (maxY - minY) / 2));

        shader.bind();
        {
            shader.uniformFloat("uiWidth", maxX - minX);
            shader.uniformFloat("uiHeight", maxY - minY);
            shader.uniformFloat("radius", radius);
        }
        shader.unbind();

        {
            if (centered) {
                float width = Math.abs(maxX - minX);
                float height = Math.abs(maxY - minY);
                transform.translate(-(width / 2), -(height / 2));
            }

            transform.translate(minX, minY);
            transform.scale(maxX - minX, maxY - minY);

            draw(Resources.ROUNDED_SHADER, textureHandle);
        }
        RenderHelper.popTransform();
    }

    public void drawRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY) {
        drawRect(textureHandle, minX, minY, maxX, maxY, false);
    }

    public void drawRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY, boolean centered) {
        Transform transform = RenderHelper.pushTransform();
        {
            if (centered) {
                float width = Math.abs(maxX - minX);
                float height = Math.abs(maxY - minY);
                transform.translate(-(width / 2), -(height / 2));
            }

            transform.translate(minX, minY);
            transform.scale(maxX - minX, maxY - minY);

            draw(Resources.DEFAULT_SHADER, textureHandle);
        }
        RenderHelper.popTransform();
    }

    public void drawCircle(ResourceHandle<Texture> textureHandle, float x, float y, float radius) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(x, y);
            transform.scale(radius * 2, radius * 2);
            draw(Resources.CIRCLE_SHADER, textureHandle);
        }
        RenderHelper.popTransform();
    }

    public void drawLine(float x1, float y1, float x2, float y2, float thickness) {
        glEnable(GL_LINE_SMOOTH);
        glLineWidth(thickness);
        lineMeshBuilder.reset();
        lineMeshBuilder.vertex(x1, y1);
        lineMeshBuilder.vertex(x2, y2);

        Texture texture = resourceManager.getResource(Resources.DEFAULT_TEXTURE);

        texture.bind();
        {
            draw(lineMeshBuilder.build(GL_LINES), Resources.DEFAULT_SHADER);
        }
        texture.unbind();
        glDisable(GL_LINE_SMOOTH);
    }

    private void draw(ResourceHandle<Shader> shaderResourceHandle, ResourceHandle<Texture> textureHandle) {
        Texture texture = resourceManager.getResourceOrDefault(textureHandle, Resources.DEFAULT_TEXTURE);

        texture.bind();
        {
            draw(quadMesh, shaderResourceHandle);
        }
        texture.unbind();
    }

    public void draw(Mesh mesh, ResourceHandle<Shader> shaderResourceHandle) {
        Shader shader = resourceManager.getResourceOrDefault(shaderResourceHandle, Resources.DEFAULT_SHADER);

        shader.bind();
        shader.uniformMat4("projection", RenderHelper.getProjection());
        shader.uniformMat4("model", RenderHelper.currentTransform().model);
        shader.uniformVec4("override_Color", RenderHelper.currentTransform().color);
        shader.unbind();
        mesh.draw(shader);
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public RendererRegistry getRendererRegistry() {
        return rendererRegistry;
    }

    @Override
    public void close() {
        this.quadMesh.close();
        this.lineMeshBuilder.close();

        this.fontRenderer.close();
    }
}
