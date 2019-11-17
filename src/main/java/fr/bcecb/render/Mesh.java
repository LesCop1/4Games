package fr.bcecb.render;

import fr.bcecb.resources.Shader;
import org.lwjgl.BufferUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL41.*;

public final class Mesh implements AutoCloseable {
    private final int mode;
    private final int vertexCount;

    private final int vao;
    private final int vbo;

    private Mesh(int mode, int vertexCount) {
        this(mode, vertexCount, null);
    }

    private Mesh(int mode, int vertexCount, @Nullable FloatBuffer vertices) {
        this.mode = mode;
        this.vertexCount = vertexCount;

        this.vao = glGenVertexArrays();
        glBindVertexArray(this.vao);
        {
            this.vbo = glGenBuffers();
            {
                glBindBuffer(GL_ARRAY_BUFFER, this.vbo);
                if (vertices != null) {
                    glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
                } else {
                    glBufferData(GL_ARRAY_BUFFER, new float[vertexCount * 8], GL_DYNAMIC_DRAW);
                }

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

    public void draw(@Nonnull Shader shader) {
        shader.bind();
        {
            draw();
        }
        shader.unbind();
    }

    public void draw() {
        glBindVertexArray(this.vao);
        {
            glDrawArrays(this.mode, 0, this.vertexCount);
        }
        glBindVertexArray(0);
    }

    @Override
    public void close() {
        glDeleteBuffers(this.vbo);
        glDeleteVertexArrays(this.vao);
    }

    public static class Builder {
        private float x;
        private float y;

        private float u;
        private float v;

        private float r;
        private float g;
        private float b;
        private float a;

        protected int vertexCount;

        protected final FloatBuffer vertexBuffer;

        public Builder(int maxVertexCount) {
            this.vertexBuffer = BufferUtils.createFloatBuffer(8 * maxVertexCount);
            this.reset();
        }

        public Mesh.Builder vertex(float x, float y) {
            vertexBuffer.put((8 * vertexCount), new float[]{x + this.x, y + this.y});
            vertexBuffer.put((8 * vertexCount) + 2, new float[]{u, v});
            vertexBuffer.put((8 * vertexCount) + 2 + 2, new float[]{r, g, b, a});
            this.vertexCount++;

            return this;
        }

        public Mesh.Builder offset(float xOffset, float yOffset) {
            this.x = xOffset;
            this.y = yOffset;

            return this;
        }

        public Mesh.Builder uv(float u, float v) {
            this.u = u;
            this.v = v;

            return this;
        }

        public Mesh.Builder color(float r, float g, float b, float a) {
            this.r = r;
            this.g = g;
            this.b = b;
            this.a = a;

            return this;
        }

        public Mesh.Builder reset() {
            this.uv(0.0f, 0.0f);
            this.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.offset(0.0f, 0.0f);
            this.vertexCount = 0;
            this.vertexBuffer.clear();

            return this;
        }

        public Mesh build(int mode) {
            this.vertexBuffer.limit(8 * vertexCount);
            this.vertexBuffer.position(0);

            return new Mesh(mode, vertexCount, vertexBuffer);
        }
    }

    public static class ReusableBuilder extends Builder implements AutoCloseable {
        private Mesh mesh;

        public ReusableBuilder(int maxVertexCount) {
            super(maxVertexCount);
        }

        @Override
        public Mesh build(int mode) {
            if (mesh == null) {
                this.mesh = new Mesh(mode, vertexCount);
            }

            glBindVertexArray(mesh.vao);
            {
                glBindBuffer(GL_ARRAY_BUFFER, mesh.vbo);
                glBufferSubData(GL_ARRAY_BUFFER, 0, vertexBuffer);
                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
            glBindVertexArray(0);

            return mesh;
        }

        @Override
        public void close() {
            this.mesh.close();
        }
    }
}