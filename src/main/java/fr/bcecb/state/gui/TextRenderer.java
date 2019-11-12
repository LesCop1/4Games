package fr.bcecb.state.gui;

import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;
import fr.bcecb.util.TransformStack;

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
        RenderEngine engine = renderManager.getRenderEngine();
        TransformStack transform = engine.getTransform();

        transform.pushTransform();
        {
            transform.color(text.getColor());

            if (text.isCentered()) {
                renderManager.getRenderEngine().drawCenteredText(Constants.DEFAULT_FONT, text.getText(), text.getX(), text.getY(), text.getScale());
            } else {
                renderManager.getRenderEngine().drawText(Constants.DEFAULT_FONT, text.getText(), text.getX(), text.getY() + (32f * text.getScale()) * 1.5f, text.getScale());
            }
        }
        transform.popTransform();
    }
}
