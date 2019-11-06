package fr.bcecb.resources;

public class StringResource extends BinaryResource {

    public StringResource() {

    }

    public String getString() {
        return new String(getBytes());
    }
}
