package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameScreen;
import fr.bcecb.util.RenderHelper;
import fr.bcecb.util.Transform;

public class EndGameStateRenderer extends ScreenStateRenderer {
    public EndGameStateRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(ScreenState screenState) {
        return this.getTexture((EndGameScreen) screenState);
    }

    public ResourceHandle<Texture> getTexture(EndGameScreen endGameScreen) {
        return endGameScreen.getBackgroundTexture();
    }

    @Override
    public void render(ScreenState screenState, float partialTick) {
        this.render((EndGameScreen) screenState, partialTick);
    }

    public void render(EndGameScreen endGameScreen, float partialTick) {
        Transform transform = RenderHelper.pushTransform();
        {
            transform.translate(endGameScreen.width / 2f, endGameScreen.height / 2f);
            renderManager.drawRoundedRect(null, 0, 0, endGameScreen.getBackgroundWidth(), endGameScreen.getBackgroundHeight(), 5f, true);
        }
        RenderHelper.popTransform();

        super.render(endGameScreen, partialTick);
    }
}
