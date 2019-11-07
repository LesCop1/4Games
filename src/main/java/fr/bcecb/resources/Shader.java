package fr.bcecb.resources;

import com.google.gson.Gson;
import fr.bcecb.util.Resources;
import fr.bcecb.util.ShaderDescriptor;
import fr.bcecb.util.Shaders;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL45.*;

public class Shader extends GLResource {
    private ShaderDescriptor descriptor;

    public Shader() {
    }

    public ShaderDescriptor getDescriptor() {
        return descriptor;
    }

    private int getUniformLocation(String uniformName) {
        return glGetUniformLocation(getGLId(), uniformName);
    }

    public int getUniformInt(String name) {
        return glGetUniformi(getGLId(), getUniformLocation(name));
    }

    public Vector4f getUniformVec4(String name) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(4);
        glGetUniformfv(getGLId(), getUniformLocation(name), buffer);
        return new Vector4f(buffer);
    }

    public void uniformInt(String name, int value) {
        uniformInt(getUniformLocation(name), value);
    }
    private void uniformInt(int location, int value) {
        glUniform1i(location, value);
    }
    public void uniformDouble(String name, double value) {
        uniformDouble(getUniformLocation(name), value);
    }
    private void uniformDouble(int location, double value) {
        uniformFloat(location, (float)value);
    }
    public void uniformFloat(String name, float value) {
        uniformFloat(getUniformLocation(name), value);
    }
    private void uniformFloat(int location, float value) {
        glUniform1f(location, value);
    }
    public void uniformVec4(String name, Vector4f vector) {
        uniformVec4(getUniformLocation(name), vector);
    }
    private void uniformVec4(int location, Vector4f vector) {
        glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }
    public void uniformVec3(String name, Vector3f vector) {
        uniformVec3(getUniformLocation(name), vector);
    }
    private void uniformVec3(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }
    public void uniformVec2(String name, Vector2f vector) {
        uniformVec2(getUniformLocation(name), vector);
    }
    private void uniformVec2(int location, Vector2f vector) {
        glUniform2f(location, vector.x, vector.y);
    }
    public void uniformBoolean(String name, boolean value) {
        uniformBoolean(getUniformLocation(name), value);
    }
    private void uniformBoolean(int location, boolean value) {
        glUniform1i(location, value ? 1 : 0);
    }
    public void uniformMat4(String name, Matrix4f matrix) {
        uniformMat4(getUniformLocation(name), matrix);
    }
    private void uniformMat4(int location, Matrix4f matrix) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            glUniformMatrix4fv(location, false, fb);
        }
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
        this.descriptor = new Gson().fromJson(Resources.readResource(inputStream), ShaderDescriptor.class);
        return Shaders.compileProgram(descriptor);
    }

    @Override
    public void dispose() {
        glDeleteProgram(getGLId());
    }
}