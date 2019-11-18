package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.state.EndGameState;
import fr.bcecb.util.Render;
import fr.bcecb.util.Transform;

public class EndGameStateRenderer extends ScreenStateRenderer {
    public EndGameStateRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(ScreenState screenState) {
        return this.getTexture((EndGameState) screenState);
    }

    public ResourceHandle<Texture> getTexture(EndGameState endGameState) {
        return endGameState.getBackgroundTexture();
    }

    @Override
    public void render(ScreenState screenState, float partialTick) {
        this.render((EndGameState) screenState, partialTick);
    }

    public void render(EndGameState endGameState, float partialTick) {
        Transform transform = Render.pushTransform();
        {
            transform.translate(endGameState.width / 2f, endGameState.height / 2f);
            renderManager.drawRoundedRect(null, 0, 0, endGameState.getBackgroundWidth(), endGameState.getBackgroundHeight(), 5f, true);
        }
        Render.popTransform();

        super.render(endGameState, partialTick);
    }
}
