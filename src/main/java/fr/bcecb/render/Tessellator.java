package fr.bcecb.render;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL41.*;

public final class Tessellator {
    private boolean building = false;
    private int mode;

    private float x;
    private float y;

    private float u;
    private float v;

    private float r;
    private float g;
    private float b;
    private float a;

    private int vertexCount;

    private final int vao;
    private final int vbo;

    private final FloatBuffer vertexBuffer;

    public Tessellator() {
        this.vertexBuffer = BufferUtils.createFloatBuffer(8 * 16);

        this.vao = glGenVertexArrays();
        glBindVertexArray(vao);
        {
            this.vbo = glGenBuffers();
            {
                glBindBuffer(GL_ARRAY_BUFFER, vbo);
                glBufferData(GL_ARRAY_BUFFER, new float[8 * 16], GL_DYNAMIC_DRAW);

                //position
                glVertexAttribPointer(0, 2, GL_FLOAT, false, 8 * 4, 0);
                glEnableVertexAttribArray(0);

                //uv
                glVertexAttribPointer(1, 2, GL_FLOAT, false, 8 * 4, 2 * 4);
                glEnableVertexAttribArray(1);

                //color
                glVertexAttribPointer(2, 4, GL_FLOAT, false, 8 * 4, 4 * 4);
                glEnableVertexAttribArray(2);
            }
        }
        glBindVertexArray(0);
    }

    public void begin(int mode) {
        if (this.building) {
            throw new IllegalStateException("Already building");
        }

        this.reset();
        this.building = true;
        this.mode = mode;
    }

    public void reset() {
        this.building = false;
        this.uv(0.0f, 0.0f);
        this.color(1.0f, 1.0f, 1.0f, 1.0f);
        this.offset(0.0f, 0.0f);
        this.mode = 0;
        this.vertexCount = 0;
        this.vertexBuffer.clear();
    }

    public Tessellator vertex(float x, float y) {
        if (!building) {
            throw new IllegalStateException("Not building");
        }

        vertexBuffer.put((8 * vertexCount), new float[]{x + this.x, y + this.y});
        vertexBuffer.put((8 * vertexCount) + 2, new float[]{u, v});
        vertexBuffer.put((8 * vertexCount) + 2 + 2, new float[]{r, g, b, a});
        this.vertexCount++;

        return this;
    }

    public Tessellator offset(float xOffset, float yOffset) {
        this.x = xOffset;
        this.y = yOffset;

        return this;
    }

    public Tessellator uv(float u, float v) {
        this.u = u;
        this.v = v;
        return this;
    }

    public Tessellator color(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public void finish() {
        if (!this.building) {
            throw new IllegalStateException("Not building");
        } else {
            this.vertexBuffer.limit(8 * vertexCount);
            this.vertexBuffer.position(0);

            glBindVertexArray(vao);
            {
                glBindBuffer(GL_ARRAY_BUFFER, vbo);
                glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            glBindVertexArray(0);

            this.building = false;
        }
    }

    public void draw() {
        if (this.building) {
            this.finish();
        }

        glBindVertexArray(vao);
        {
            glDrawArrays(mode, 0, vertexCount);
        }
        glBindVertexArray(0);
    }
}