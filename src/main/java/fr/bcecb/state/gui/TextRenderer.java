package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

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
        Transform transform = RenderHelper.pushTransform();
        {
            transform.color(text.getColor());
            renderManager.getFontRenderer().drawString(text.getText(), text.getX(), text.getY(), text.getScale(), text.isCentered());
        }
        RenderHelper.popTransform();
    }
}
