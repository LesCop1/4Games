package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.util.Constants;
import org.joml.Vector4f;

public class Text extends GuiElement {
    private final String text;
    private final float scale;

    private final boolean centered;

    public Text(int id, float x, float y, boolean centered, String text) {
        this(id, x, y, centered, text, 1.0f);
    }

    public Text(int id, float x, float y, boolean centered, String text, float scale) {
        super(id, x, y, 0, 0);
        this.centered = centered;
        this.text = text;
        this.scale = scale;
    }

    public String getText() {
        return text;
    }

    public float getScale() {
        return scale;
    }

    public Vector4f getColor() {
        return Constants.COLOR_BLACK;
    }

    public boolean isCentered() {
        return centered;
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onClick(MouseEvent.Click event) {

    }

    @Override
    public void onDrag(MouseEvent.Click clickEvent, MouseEvent.Move moveEvent) {

    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }
}
