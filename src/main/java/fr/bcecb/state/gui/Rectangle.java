package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.TransformStack;
import org.joml.Vector4f;

public class Rectangle extends GuiElement {
    private Vector4f color;

    public Rectangle(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, null);
    }

    public Rectangle(int id, float x, float y, float width, float height, Vector4f color) {
        super(id, x, y, width, height);
        this.color = MoreObjects.firstNonNull(color, new Vector4f(1.0f));
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

    public static class LineRenderer extends Renderer<Rectangle> {

        public LineRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Rectangle rect) {
            return null;
        }

        @Override
        public void render(Rectangle rect, float partialTick) {
            RenderEngine engine = renderManager.getRenderEngine();
            TransformStack transform = engine.getTransform();

            transform.pushTransform();
            {
                transform.color(rect.getColor());
                engine.drawRect(null, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            }
            transform.popTransform();
        }
    }
}
