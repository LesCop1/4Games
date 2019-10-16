package fr.bcecb.util;

public class ShaderDescriptor {
    private final String name;

    public ShaderDescriptor() {
        this.name = null;
    }

    public ShaderDescriptor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
