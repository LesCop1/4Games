package fr.bcecb.resources;

import fr.bcecb.Game;

import java.io.IOException;
import java.io.InputStream;

public interface IResource {
    void load(InputStream inputStream) throws IOException;
    void dispose();
}
