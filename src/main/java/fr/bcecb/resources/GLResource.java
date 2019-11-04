package fr.bcecb.resources;

import java.io.IOException;
import java.io.InputStream;

public abstract class GLResource implements IResource {
    private int id = 0;

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.id = create(inputStream);
    }

    public abstract int create(InputStream inputStream) throws IOException;

    public abstract void bind();
    public abstract void unbind();

    @Override
    public boolean validate() {
        return id > 0;
    }

    public int getGLId() {
        return id;
    }
}
