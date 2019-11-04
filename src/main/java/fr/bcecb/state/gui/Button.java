package fr.bcecb.state.gui;

import com.google.common.base.Strings;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import org.joml.Vector4f;

public class Button extends GuiElement {
    private String title;

    public Button(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, false);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, null);
    }

    public Button(int id, float x, float y, float width, float height, String title) {
        this(id, x, y, width, height, false, title);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
            RenderEngine renderEngine = renderManager.getRenderEngine();
            renderEngine.drawTexturedRect(getTexture(button), button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight());

            if (!Strings.isNullOrEmpty(button.getTitle())) {
                renderEngine.drawCenteredText(ResourceManager.DEFAULT_FONT, button.getTitle(), button.getX() + (button.getWidth() / 2.0f), button.getY() + (button.getHeight() / 2.0f), 1.0f, new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
            }
        }
    }
}