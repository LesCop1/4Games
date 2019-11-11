package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Texture;

public class TextRenderer extends Renderer<Text> {

    public TextRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Text text) {
        return null;
    }

    @Override
    public void render(Text text, float partialTick) {
        if (text.isCentered()) {
            renderManager.getRenderEngine().drawCenteredText(ResourceManager.DEFAULT_FONT, text.getText(), text.getX(), text.getY(), text.getScale(), text.getColor());
        } else {
            renderManager.getRenderEngine().drawText(ResourceManager.DEFAULT_FONT, text.getText(), text.getX(), text.getY() + (32f * text.getScale()) * 1.5f, text.getScale(), text.getColor());
        }
    }
}
