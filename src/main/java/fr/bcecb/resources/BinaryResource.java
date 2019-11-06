package fr.bcecb.resources;

import java.io.IOException;
import java.io.InputStream;

public class BinaryResource implements IResource {
    private byte[] bytes;

    public BinaryResource() {

    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.bytes = inputStream.readAllBytes();
    }

    @Override
    public boolean validate() {
        return bytes != null && bytes.length > 0;
    }

    @Override
    public void dispose() {

    }
}
