package fr.bcecb.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringResource implements IResource {
    private String string;

    public StringResource() {

    }

    public String getString() {
        return string;
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.string = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean validate() {
        return this.string != null;
    }
}
