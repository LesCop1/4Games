package fr.bcecb.state.gui;

import com.google.common.base.Strings;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class ButtonRenderer extends Renderer<Button> {

    public ButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Button button) {
        return button.isHovered() ? button.getOnHoverTexture() : button.getTexture();
    }

    @Override
    public void render(Button button, Matrix4f transform, float partialTick) {
        RenderEngine renderEngine = renderManager.getRenderEngine();

        renderEngine.applyTransform(transform);
        renderEngine.drawTexturedRect(getTexture(button), button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight());
        renderEngine.resetTransform();

        if (!Strings.isNullOrEmpty(button.getTitle())) {
            renderEngine.drawCenteredText(ResourceManager.DEFAULT_FONT, button.getTitle(), button.getX() + (button.getWidth() / 2.0f), button.getY() + (button.getHeight() / 2.0f), 1.0f, new Vector4f(0.0f, 0.0f, 0.0f, 1.0f));
        }
    }
}
