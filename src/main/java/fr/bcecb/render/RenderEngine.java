package fr.bcecb.render;

import fr.bcecb.Game;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Shader;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.StateEngine;
import fr.bcecb.util.Log;
import org.joml.*;
import org.joml.Math;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL33.*;

public class RenderEngine {
    private Window window;

    private final ResourceManager resourceManager;

    private final ResourceHandle<Shader> shaderResourceHandle = new ResourceHandle<>("shaders/base.json") {
    };

    private final RenderManager renderManager;

    private final int vao, vbo, ebo;

    private final Matrix4f projection;

    public RenderEngine(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
        this.renderManager = new RenderManager(this, resourceManager);
        this.window = Window.newInstance("Game", 800, 600, false);

        GL.createCapabilities();
        Log.RENDER.config("Using OpenGL Version {0}", glGetString(GL_VERSION));

        this.vao = glGenVertexArrays();

        glBindVertexArray(vao);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, new float[]{0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f}, GL_STATIC_DRAW);

        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, new int[]{0, 1, 3, 1, 2, 3}, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindVertexArray(0);

        this.projection = new Matrix4f().ortho2D(0, 1, 1, 0);
    }

    public void cleanUp() {
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        glDeleteVertexArrays(vao);

        window.destroy();
        glfwTerminate();
    }

    public void update() {
        if (window.shouldClose()) {
            Game.instance().stop();
            return;
        }

        glfwSwapBuffers(window.id());
        glfwPollEvents();
    }

    public void render(StateEngine stateEngine, float partialTick) {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, window.width(), window.height());

        Shader shader = resourceManager.getResource(shaderResourceHandle);
        shader.bind();
        shader.uniformFloat("partialTick", partialTick);
        shader.uniformMat4("projection", projection);
        stateEngine.render(renderManager, partialTick);
        shader.unbind();

        drawRect(0, 0, 100, 100);
    }

    public void drawTexturedRect(ResourceHandle<Texture> textureHandle, float minX, float minY, float maxX, float maxY) {
        Texture texture = resourceManager.getTexture(textureHandle);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        texture.bind();
        drawRect(minX, minY, maxX, maxY);
        texture.unbind();
    }

    public void drawRect(float minX, float minY, float maxX, float maxY) {
        Shader shader = resourceManager.getResource(shaderResourceHandle);
        shader.uniformVec2("scale", new Vector2f(Math.abs(maxX - minX) / window.width(), Math.abs(maxY - minY) / window.height()));
        shader.uniformVec2("position", new Vector2f(minX / window.width(), minY / window.height()));

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void drawCircle(float x, float y, float radius) {
        Shader shader = resourceManager.getResource(new ResourceHandle<>("shaders/circle.json") {
        });
        shader.bind();
        shader.uniformMat4("projection", projection);
        shader.uniformVec2("scale", new Vector2f(radius / window.width(), radius / window.height()));
        shader.uniformVec2("position", new Vector2f(x / window.width(), y / window.height()));

        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);

        shader.unbind();
    }

    public void drawTexturedCircle(ResourceHandle<Texture> textureHandle, float x, float y, float scale) {
        Texture texture = resourceManager.getTexture(textureHandle);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        texture.bind();
        drawCircle(x, y, scale);
        texture.unbind();
    }

    public Window getWindow() {
        return window;
    }
}
