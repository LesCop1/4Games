package fr.bcecb.resources;

import fr.bcecb.util.Log;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.opengl.GL33.*;

public class Shader extends GLResource {
    public Shader() {
    }

    @Override
    public void bind() {
        glUseProgram(getGLId());
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public int create(InputStream inputStream) throws IOException {
        return create("", "", ""); //TODO
    }

    private int create(String vertexSource, String fragmentSource, String geometrySource) throws IOException {
        int vertex = compile(GL_VERTEX_SHADER, vertexSource);
        int fragment = compile(GL_FRAGMENT_SHADER, fragmentSource);
        int geometry = compile(GL_GEOMETRY_SHADER, geometrySource);

        if (vertex == 0 || fragment == 0 || (geometry == 0 && geometrySource != null)) {
            return -1;
        }

        int program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);

        if (geometry != 0) {
            glAttachShader(program, geometry);
        }

        glLinkProgram(program);

        String infoLog = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        if (!infoLog.isBlank()) {
            Log.warning(Log.RENDER, "Shader program linking returned with warnings : " + infoLog);
        }

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            Log.severe(Log.RENDER, "Failed to link shader program !");
            return -1;
        }

        glDetachShader(program, vertex);
        glDeleteShader(vertex);
        glDetachShader(program, fragment);
        glDeleteShader(fragment);
        glDetachShader(program, geometry);
        glDeleteShader(geometry);

        return program;
    }

    private int compile(int type, String source) {
        if (source == null || source.length() == 0) {
            return 0;
        }

        int id = glCreateShader(type);

        glShaderSource(id, source);
        glCompileShader(id);

        String infoLog = glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH));
        if (!infoLog.isBlank()) {
            Log.warning(Log.RENDER, "Shader compilation returned with warnings : " + infoLog);
        }

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            Log.severe(Log.RENDER, "Failed to compile " + getShaderName(type) + " shader !");
            return 0;
        }

        return id;
    }

    private static String getShaderName(int type) {
        switch (type) {
            case GL_VERTEX_SHADER:
                return "Vertex";
            case GL_FRAGMENT_SHADER:
                return "Fragment";
            case GL_GEOMETRY_SHADER:
                return "Geometry";
            default:
                return "";
        }
    }

    @Override
    public void dispose() {
        glDeleteProgram(getGLId());
    }
}