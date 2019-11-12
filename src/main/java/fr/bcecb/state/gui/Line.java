package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Line extends GuiElement {
    private Vector4f color;

    public Line(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, null);
    }

    public Line(int id, float x, float y, float width, float height, Vector4f color) {
        super(id, x, y, width, height);
        this.color = MoreObjects.firstNonNull(color, new Vector4f(1));
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

    public Vector4f getColor() {
        return color;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public static class LineRenderer extends Renderer<Line> {

        public LineRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Line line) {
            return null;
        }

        @Override
        public void render(Line line, float partialTick) {
            renderManager.getRenderEngine().drawColoredRect(line.getX(), line.getY(), line.getWidth(), line.getHeight(), line.getColor());
        }
    }
}
