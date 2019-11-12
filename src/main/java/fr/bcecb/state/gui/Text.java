package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Text extends GuiElement {
    private String text;
    private float scale;
    private Vector4f color;
    private boolean centered;

    public Text(int id, float x, float y, String text, boolean centered) {
        this(id, x, y, text, null, centered);
        this.text = text;
    }

    public Text(int id, float x, float y, String text, Vector4f color, boolean centered) {
        this(id, x, y, text, 1.0f, color, centered);
    }

    public Text(int id, float x, float y, String text, float scale, boolean centered) {
        this(id, x, y, text, scale, null, centered);
    }

    public Text(int id, float x, float y, String text, float scale, Vector4f color, boolean centered) {
        super(id, x, y, x, y);
        this.text = text;
        this.scale = Math.min(1.0f, scale);
        this.color = MoreObjects.firstNonNull(color, new Vector4f(1.0f, 0.0f, 0.0f, 1.0f));
        this.centered = centered;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public boolean isCentered() {
        return centered;
    }

    public void setCentered(boolean centered) {
        this.centered = centered;
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
            if (text.getId() == 40) {
               // System.out.println("text.getText() = " + text.getText());;
            }
            if (text.isCentered()) {
                renderManager.getRenderEngine().drawCenteredText(ResourceManager.DEFAULT_FONT, text.getText(), text.getX(), text.getY(), text.getScale(), text.getColor());
            } else {
                renderManager.getRenderEngine().drawText(ResourceManager.DEFAULT_FONT, text.getText(), text.getX(), text.getY() + (32f * text.getScale()) * 1.5f, text.getScale(), text.getColor());
            }
        }
    }
}
