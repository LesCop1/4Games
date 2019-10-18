package fr.bcecb.state.gui;

import fr.bcecb.input.MouseEvent;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.BoundingBox;

public class Button extends GuiElement {
    public Button(int id, int x, int y, float width, float height) {
        super(id, new BoundingBox(x, y, x + width, y + height));
    }

    @Override
    protected void onClick(MouseEvent.Click event) {
    }

    @Override
    protected void onHover(MouseEvent.Move event) {
    }

    @Override
    protected void onScroll(MouseEvent.Scroll event) {
    }

    public static class ButtonRenderer extends Renderer<Button> {

        public ButtonRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Button button) {
            return new ResourceHandle<>("textures/btn.png") {};
        }

        @Override
        public void render(Button button, float partialTick) {
            BoundingBox bb = button.getBoundingBox();
            renderManager.getRenderEngine().drawTexturedRect(getTexture(button), bb.getMinX(), bb.getMinY(), bb.getMaxX(), bb.getMaxY());
        }
    }
}