package fr.bcecb.util;

import com.google.common.base.Strings;
import fr.bcecb.Game;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.StringResource;

import static org.lwjgl.opengl.GL45.*;

public class Shaders {
    public static int compileProgram(ShaderDescriptor descriptor) {
        int vertex = compileShader(GL_VERTEX_SHADER, descriptor.getVertexResource());
        int fragment = compileShader(GL_FRAGMENT_SHADER, descriptor.getFragmentResource());
        int geometry = compileShader(GL_GEOMETRY_SHADER, descriptor.getGeometryResource());

        if ((vertex == 0 && descriptor.isVertexEnabled()) || (fragment == 0 && descriptor.isFragmentEnabled()) || (geometry == 0 && descriptor.isGeometryEnabled())) {
            return 0;
        }

        int program = glCreateProgram();
        if (descriptor.isVertexEnabled()) glAttachShader(program, vertex);
        if (descriptor.isFragmentEnabled()) glAttachShader(program, fragment);
        if (descriptor.isGeometryEnabled()) glAttachShader(program, geometry);

        glLinkProgram(program);

        String infoLog = glGetProgramInfoLog(program, glGetProgrami(program, GL_INFO_LOG_LENGTH));
        if (!Strings.isNullOrEmpty(infoLog)) {
            Log.SYSTEM.warning("Shader program linking returned with warnings : {0}", infoLog);
            Log.SYSTEM.debug("Shader descriptor : {0}", descriptor);
        }

        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
            Log.SYSTEM.severe("Failed to link shader program !");
            return 0;
        }

        if (vertex != 0) glDetachShader(program, vertex);
        glDeleteShader(vertex);
        if (fragment != 0) glDetachShader(program, fragment);
        glDeleteShader(fragment);
        if (geometry != 0) glDetachShader(program, geometry);
        glDeleteShader(geometry);

        return program;
    }

    private static int compileShader(int type, ResourceHandle<StringResource> resourceHandle) {
        if (resourceHandle == null) {
            return 0;
        }

        StringResource resource = Game.instance().getResourceManager().loadResource(resourceHandle);

        if (resource == null) {
            return 0;
        }

        int id = glCreateShader(type);

        glShaderSource(id, resource.getString());
        glCompileShader(id);

        String infoLog = glGetShaderInfoLog(id, glGetShaderi(id, GL_INFO_LOG_LENGTH));
        if (!Strings.isNullOrEmpty(infoLog)) {
            Log.SYSTEM.warning("Shader compilation returned with warnings : {0}", infoLog);
        }

        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            Log.SYSTEM.severe("Failed to compile {0} shader !", getShaderName(type));
            return 0;
        }

        return id;
    }

    private static String getShaderName(int type) {
        return switch (type) {
            case GL_VERTEX_SHADER -> "Vertex";
            case GL_FRAGMENT_SHADER -> "Fragment";
            case GL_GEOMETRY_SHADER -> "Geometry";
            default -> "";
        };
    }
}
