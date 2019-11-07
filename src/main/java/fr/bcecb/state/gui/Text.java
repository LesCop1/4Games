package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Text extends GuiElement {
    private String text;
    private int size = 10;
    private Vector4f color = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);

    public Text(int id, float x, float y, String text) {
        this(id, x, y, text, null);
        this.text = text;
    }

    public Text(int id, float x, float y, String text, Vector4f color) {
        this(id, x, y, text, 0, color);
    }

    public Text(int id, float x, float y, String text, int size) {
        this(id, x, y, text, size, null);
    }

    public Text(int id, float x, float y, String text, int size, Vector4f color) {
        super(id, x, y, x, y);
        this.text = text;
        if (color != null) {
            this.color = color;
        }
        if (size != 0) {
            this.size = size;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    @Override
    public void onClick(MouseEvent.Click event) {

    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public static class TextRenderer extends Renderer<Text> {

        public TextRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Text text) {
            return null;
        }

        @Override
        public void render(Text text, float partialTick) {
            renderManager.getRenderEngine().drawText(ResourceManager.DEFAULT_FONT, text.getText(), text.getX(), text.getY(), text.getSize(), text.getColor());
        }
    }
}
