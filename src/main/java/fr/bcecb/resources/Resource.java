package fr.bcecb.resources;

import java.io.IOException;
import java.io.InputStream;

public class Resource implements IResource {
    private byte[] bytes;

    public Resource() {

    }

    public byte[] getBytes() {
        return bytes;
    }

    public String asString() {
        return new String(getBytes());
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.bytes = inputStream.readAllBytes();
    }

    @Override
    public void dispose() {

    }
}
