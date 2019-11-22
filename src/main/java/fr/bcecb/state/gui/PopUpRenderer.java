package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class PopUpRenderer extends ScreenStateRenderer {
    public PopUpRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(ScreenState screenState) {
        return this.getTexture((PopUpScreenState) screenState);
    }

    public ResourceHandle<Texture> getTexture(PopUpScreenState popUpScreenState) {
        return popUpScreenState.getBackgroundTexture();
    }

    @Override
    public void render(ScreenState screenState, float partialTick) {
        this.render((PopUpScreenState) screenState, partialTick);
    }

    public void render(PopUpScreenState popUpScreenState, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(popUpScreenState.width / 2f, popUpScreenState.height / 2f);
            renderManager.drawRoundedRect(null, 0, 0, popUpScreenState.getBackgroundWidth(), popUpScreenState.getBackgroundHeight(), 5f, true);
        }
        RenderHelper.popTransform();

        super.render(popUpScreenState, partialTick);
    }
}
