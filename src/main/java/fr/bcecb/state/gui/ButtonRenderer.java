package fr.bcecb.state.gui;

import com.google.common.base.Strings;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class ButtonRenderer extends Renderer<Button> {

    public ButtonRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(Button button) {
        return button.isHovered() ? button.getHoverTexture() : button.getTexture();
    }

    @Override
    public void render(Button button, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(button.getX(), button.getY());
            transform.translate(button.getWidth() / 2.0f, button.getHeight() / 2.0f);

            if (button.isHovered() && !button.isDisabled()) {
                float scale = button.getHoverAnimation().getInterpolatedValue(partialTick);
                transform.scale(scale, scale);
            }

            renderManager.drawRect(this.getTexture(button), 0, 0, button.getWidth(), button.getHeight(), true);

            if (!Strings.isNullOrEmpty(button.getTitle())) {
                RenderHelper.pushTransform();
                {
                    transform.color(button.getTitleColor());

                    renderManager.getFontRenderer().drawStringBoxed(button.getTitle(), 0, 0, button.getWidth(), button.getHeight());
                }
                RenderHelper.popTransform();
            }
        }
        RenderHelper.popTransform();
    }
}
