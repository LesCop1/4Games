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
    private ResourceHandle<Texture> textureResourceHandle;

    public Button(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, false);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, ResourceManager.DEFAULT_TEXTURE);
    }

    public Button(int id, float x, float y, float width, float height, String title) {
        this(id, x, y, width, height, false, title, ResourceManager.DEFAULT_TEXTURE);
    }

    public Button(int id, float x, float y, float width, float height, ResourceHandle<Texture> textureResourceHandle) {
        this(id, x, y, width, height, false, null, textureResourceHandle);
    }

    public Button(int id, float x, float y, float width, float height, String title, ResourceHandle<Texture> textureResourceHandle) {
        this(id, x, y, width, height, false, title, textureResourceHandle);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, ResourceHandle<Texture> textureResourceHandle) {
        this(id, x, y, width, height, centered, null, textureResourceHandle);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, ResourceHandle<Texture> textureResourceHandle) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
        this.textureResourceHandle = textureResourceHandle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResourceHandle<Texture> getTextureResourceHandle() {
        return textureResourceHandle;
    }

    public void setTextureResourceHandle(ResourceHandle<Texture> textureResourceHandle) {
        this.textureResourceHandle = textureResourceHandle;
    }

    public static class ButtonRenderer extends Renderer<Button> {

        public ButtonRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Button button) {
            return button.getTextureResourceHandle();
        }

        @Override
        public void render(Button button, float partialTick) {
            RenderEngine renderEngine = renderManager.getRenderEngine();
            renderEngine.drawTexturedRect(getTexture(button), button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight());

            if (!Strings.isNullOrEmpty(button.getTitle())) {
                renderEngine.drawCenteredText(ResourceManager.DEFAULT_FONT, button.getTitle(), button.getX() + (button.getWidth() / 2.0f), button.getY() + (button.getHeight() / 2.0f), 64, new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
            }
        }
    }
}